package org.maxvas.batchnews.reader;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.maxvas.batchnews.domain.ArticleDto;
import org.maxvas.batchnews.domain.ArticleLink;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
@UtilityClass
public class TheGuardianNewsDataSource {
    static final String theGuardianSitUrlBase = "https://theguardian.com/world/russia";
    static final String allPostfix = "all";
    static final DateTimeFormatter urlPartFormatter = DateTimeFormatter.ofPattern("yyyy/LLL/dd").withLocale(Locale.ENGLISH);
    static final int HTTP_STATUS_CODE_TOO_MANY_REQUEST = 429;
    static final int WAIT_TIMEOUT_ON_TOO_MANY_REQUEST = 15000; // 15 sec
    static final int RETRY_TIMES = 3;

    /**
     * 3 november 2020 should be formatted like "2020/nov/03"
     *
     * @param date date to request
     * @return formatted part of URL
     */
    public static String getUrlDate(LocalDate date) {
        return urlPartFormatter.format(date).toLowerCase(Locale.ENGLISH);
    }

    /**
     * Get link like https://theguardian.com/world/russia/2020/nov/03/all
     *
     * @param date date to request
     * @return url string
     */
    public static String getUrlAllByDate(LocalDate date) {
        return String.join("/", theGuardianSitUrlBase, getUrlDate(date), allPostfix);
    }

    /**
     * Get all links for the dedicated date
     *
     * @param date
     * @return
     */
    @SneakyThrows
    public static List<String> getLinksByDate(LocalDate date) {
        String url = getUrlAllByDate(date);
        Document doc = getUrl(url);
        Elements newLinks = doc.select("a.u-faux-block-link__overlay");
        return newLinks.stream().map(link -> link.absUrl("href")).collect(Collectors.toList());
    }

    @SneakyThrows
    public static Document getUrl(String url) {
        ArrayList<Exception> exceptions = new ArrayList();
        Document doc = null;
        int i = 0;
        boolean success = false;

        while (i < RETRY_TIMES) {
            try {
                doc = Jsoup.connect(url).get();
                success = true;
                break;
            } catch (SocketTimeoutException ex) {
                exceptions.add(ex);
                log.warn(ExceptionUtils.getStackTrace(ex));
            } catch (HttpStatusException statusException) {
                log.warn(ExceptionUtils.getStackTrace(statusException));
                exceptions.add(statusException);
                if (statusException.getStatusCode() == HTTP_STATUS_CODE_TOO_MANY_REQUEST) {
                    Thread.sleep(WAIT_TIMEOUT_ON_TOO_MANY_REQUEST);
                }
            } catch (IOException ioException) {
                log.warn(ExceptionUtils.getStackTrace(ioException));
                exceptions.add(ioException);
            }
            i++;
        }

        if (success) {
            return doc;
        } else {
            throw new RuntimeException("Can't download link " + url, exceptions.get(0));
        }
    }


    /**
     * Get date from link url
     *
     * @param link url, содержащая дату в пути.
     * @return
     */
    public static LocalDate getDateFromLink(String link) {
        String regexp = "^.*/([^/]*)/([^/]*)/([^/]*)/.*$";
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(link);
        if (!m.matches()) {
            throw new RuntimeException("Cant extract date from link");
        }
        String year = m.group(1);
        String mon = m.group(2);
        String dayOfMonth = m.group(3);
        LocalDate date = LocalDate.parse(String.join("/", year, mon.substring(0, 1).toUpperCase() + mon.substring(1), dayOfMonth), urlPartFormatter);
        return date;
    }


    /**
     * Get all links for a range of dates.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return List of article links to download.
     */
    public static List<ArticleLink> getLinksByRange(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .flatMap(date -> TheGuardianNewsDataSource.getLinksByDate(date).stream())
                .distinct()
                .map(link -> new ArticleLink(getDateFromLink(link), link))
                .sorted(Comparator.comparing((ArticleLink a) -> a.getDate()).thenComparing(a -> a.getLink()))
                .collect(Collectors.toList());
    }

    /**
     * Download and map an article by article link.
     *
     * @param articleLink resource to download
     * @return formatted dto Article
     */
    @SneakyThrows
    public static ArticleDto getArticleData(ArticleLink articleLink) {
        String title = null;
        String text = null;
        try {
            String url = articleLink.getLink();
            Document doc = getUrl(url);
            title = doc.select("h1").first().html();
            Elements elements = doc.select("div.article-body-commercial-selector");
            Element element = elements.first();
            if (element == null) {
                return new ArticleDto(articleLink.getDate(), articleLink.getLink(), title, text, false, "Error to find text block. Wrong format.");
            }
            text = elements.first().html();
            text = Jsoup.clean(text, Safelist.basic());
            return new ArticleDto(articleLink.getDate(), articleLink.getLink(), title, text, true, null);
        } catch (Exception e) {
            return new ArticleDto(articleLink.getDate(), articleLink.getLink(), title, text, false, e.toString());
        }
    }


}
