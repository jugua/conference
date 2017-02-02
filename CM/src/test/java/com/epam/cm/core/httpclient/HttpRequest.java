package com.epam.cm.core.httpclient;

import com.epam.cm.core.logger.LoggerFactory;
import com.gargoylesoftware.htmlunit.HttpMethod;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;

/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class HttpRequest {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultSecureHttpClient.class);
    private static final String DEFAULT_ENCODING = "UTF-8";
    private boolean withAuth;
    private String login;
    private String password;
    private HttpRequestBase rawRequest;
    private boolean logResponseBody = true;

    private HttpRequest(final HttpMethod method, final String uri) {
        if (method.equals(HttpMethod.GET)) {
            rawRequest = new HttpGet(uri);
        }
    }

    public static HttpRequest get(final String uri) {
        return new HttpRequest(HttpMethod.GET, uri);
    }


    public HttpRequest addBasicAuth(final String login, final String password) {
        withAuth = true;
        this.login = login;
        this.password = password;
        String encodedAuthorization = "Basic " + Base64.encodeBase64String((login + ":" + password).getBytes());
        addHeader("Authorization", encodedAuthorization);
        return this;
    }

    public HttpRequest addTokenHeader(final String value) {
        return addHeader("X-XSRF-TOKEN", value);
    }

    public HttpRequest addContentType(final String enumer) {
        return addHeader("Content-Type", enumer);
    }

    public HttpRequest addHeader(final String key, final String value) {
        rawRequest.addHeader(key, value);
        return this;
    }

    public HttpRequest addBody(final String key, final String value) {

        return this;
    }

    public HttpResponse sendAndGetResponse(int expectedStatusCode) {
        HttpResponse response = null;
        try {
            response = DefaultSecureHttpClientSingleton.getInstance().execute(rawRequest);
            if (expectedStatusCode != response.getStatusLine().getStatusCode()) {
                throw new RuntimeException("Incorrect status code. Actual:"
                        + response.getStatusLine().getStatusCode() + " Expected:" + expectedStatusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception in sendAndGet", e);
        }
        return response;
    }
}
