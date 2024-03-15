package org.openschool.consumer_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler
    implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
        throws IOException {

        return httpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
        throws IOException {
        if (httpResponse.getStatusCode().is4xxClientError()) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
               log.warn("Ресурс не найден {}", httpResponse.getStatusCode());
            }
        }
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Ресурс не найден {} {}", method, url);
            }
        }
    }
}
