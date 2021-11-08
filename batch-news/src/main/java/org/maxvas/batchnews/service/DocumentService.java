package org.maxvas.batchnews.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Get resource from internet with retry.
 */
@Service
@Slf4j
@AllArgsConstructor
public class DocumentService {

    private final DocumentServiceInternal documentServiceInternal;


    /**
     * Wrap exception
     *
     * @param url Url
     * @return Document
     * @throws RuntimeException
     */
    public Document getUrl(String url) throws RuntimeException {
        try {
            return documentServiceInternal.getUrlRetryInternal(url);
        } catch (IOException exception) {
            throw new RuntimeException(String.format("Error getUrl %s", url), exception);
        }
    }
}
