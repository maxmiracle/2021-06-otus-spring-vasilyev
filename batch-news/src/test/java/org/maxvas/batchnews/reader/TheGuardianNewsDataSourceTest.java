package org.maxvas.batchnews.reader;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.maxvas.batchnews.domain.ArticleDto;
import org.maxvas.batchnews.domain.ArticleLink;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class TheGuardianNewsDataSourceTest {
    @Test
    public void getUrlDateFormatTest() {
        LocalDate testDate = LocalDate.of(2020, 11, 3);
        String expectedUrlPart = "2020/nov/03";
        String actualUrlPart = TheGuardianNewsDataSource.getUrlDate(testDate);
        assertEquals(expectedUrlPart, actualUrlPart);
    }

    @Test
    public void getLinksFromSiteTest() {
        List<String> links = TheGuardianNewsDataSource.getLinksByDate(LocalDate.of(2020, 11, 3));
    }

    @Test
    public void getDateFromLinkTest() {
        String testLink = "https://www.theguardian.com/world/2020/nov/02/russias-sausage-king-killed-in-moscow-after-crossbow-attack-vladimir-marugov";
        LocalDate actualDate = TheGuardianNewsDataSource.getDateFromLink(testLink);
        assertEquals(LocalDate.of(2020, 11, 2), actualDate);
    }

    @Test
    public void getLinksForPeriodTest() {
        List<ArticleLink> articleItems = TheGuardianNewsDataSource.getLinksByRange(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 10));
        articleItems.stream().forEach(item -> log.debug(item.toString()));
    }


    @Test
    public void getArticleData() {
        String link = "https://www.theguardian.com/world/2019/dec/31/north-korean-leader-to-end-missile-test-ban-claims-state-media";
        ArticleLink articleLink = new ArticleLink(LocalDate.of(2019, 12, 31)
                , link);
        ArticleDto article = TheGuardianNewsDataSource.getArticleData(articleLink);
        log.debug(article.toString());
    }

    @Test
    public void getAll2020Articles() {
        List<ArticleLink> articleItems = TheGuardianNewsDataSource.getLinksByRange(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 12, 31));
        log.info("Articles number: {}", articleItems.size());
        articleItems.forEach(articleLink -> {
            var data = TheGuardianNewsDataSource.getArticleData(articleLink);
            log.info("Data link {} loaded. Size {}", data.getLink(), data.getSuccess() ? data.getText().length() : data.getError());
        });
    }

}
