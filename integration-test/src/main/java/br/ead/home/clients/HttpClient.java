package br.ead.home.clients;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static br.ead.home.clients.HttpHeaders.CORRELATION_ID;
import static br.ead.home.clients.HttpHeaders.IDEMPOTENCY_KEY;
import static br.ead.home.clients.HttpHeaders.ORIGINATOR_SOURCE;
import static br.ead.home.clients.HttpHeaders.ORIGINATOR_USER;
import static java.util.UUID.randomUUID;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

@Log4j2
public class HttpClient {

    private static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    private final CloseableHttpClient httpclient;

    private final String baseUrl;
    private final Integer port;
    private final String basicAuthHeader;
    private final List<Header> additionalHeaders;

    public HttpClient(String baseUrl, Integer port, String basicAuthHeader, List<Header> additionalHeaders) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.basicAuthHeader = basicAuthHeader;
        this.additionalHeaders = additionalHeaders;
        this.httpclient = HttpClients.createDefault();
    }

    public HttpClient(String baseUrl, Integer port, String basicAuthHeader) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.basicAuthHeader = basicAuthHeader;
        this.additionalHeaders = List.of();
        this.httpclient = HttpClients.createDefault();
    }

    public Response call(HttpRequest request) {
        setHeaders(request);

        var target = new HttpHost(baseUrl, port, "http");
        try (CloseableHttpResponse rawResponse = httpclient.execute(target, request)) {
            int statusCode = rawResponse.getStatusLine().getStatusCode();
            Response response = new Response(statusCode, getResponseBody(rawResponse).orElse(null), getHeaders(rawResponse));
            return response;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<String> getResponseBody(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return Optional.empty();
        } else {
            try {
                return Optional.of(EntityUtils.toString(entity));
            } catch (IOException | ParseException e) {
                throw new IllegalStateException("Could not parse response entity to String: \"" + entity + "\"", e);
            }
        }
    }

    private Map<String, String> getHeaders(CloseableHttpResponse response) {
        Map<String, String> headers = new HashMap<>();
        for (Header header : response.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        return headers;
    }

    private void setHeaders(HttpRequest httpRequest) {

        if (!httpRequest.containsHeader(CONTENT_TYPE)) {
            httpRequest.setHeader(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);
        }

        Optional.ofNullable(httpRequest)
                .map(req -> req.getFirstHeader(CORRELATION_ID))
                .map(Header::getValue)
                .filter(StringUtils::isNotBlank)
                .ifPresentOrElse(correlationId -> httpRequest.setHeader(CORRELATION_ID, correlationId),
                        () -> httpRequest.setHeader(CORRELATION_ID, randomUUID().toString()));

        Optional.ofNullable(httpRequest)
                .map(req -> req.getFirstHeader(IDEMPOTENCY_KEY))
                .map(Header::getValue)
                .filter(StringUtils::isNotBlank)
                .ifPresentOrElse(idempotencyKey -> httpRequest.setHeader(IDEMPOTENCY_KEY, idempotencyKey),
                        () -> httpRequest.setHeader(IDEMPOTENCY_KEY, randomUUID().toString()));

        httpRequest.setHeader(ORIGINATOR_SOURCE, "integration-test");
        httpRequest.setHeader(ORIGINATOR_USER, "jhon.doe");

        if (StringUtils.isNotEmpty(basicAuthHeader)) {
            httpRequest.setHeader(AUTHORIZATION, basicAuthHeader);
        }

        additionalHeaders.forEach(httpRequest::setHeader);
    }

}
