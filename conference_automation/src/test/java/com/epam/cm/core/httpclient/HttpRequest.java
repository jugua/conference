package com.epam.cm.core.httpclient;

import com.epam.cm.core.logger.LoggerFactory;
import com.gargoylesoftware.htmlunit.HttpMethod;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
        else if (method.equals(HttpMethod.POST)){
            rawRequest = new HttpPost(uri);
        }
    }

    public static HttpRequest get(final String uri) {
        return new HttpRequest(HttpMethod.GET, uri);
    }

    public static HttpRequest post(final String uri){
        return new HttpRequest(HttpMethod.POST,uri);
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

    public HttpRequest addContentType(final String value) {
        return addHeader("Content-Type", value);
    }

    public HttpRequest addHeader(final String key, final String value) {
        rawRequest.addHeader(key, value);
        return this;
    }

    public HttpResponse sendAndGetResponse(int expectedStatusCode) throws NoSuchAlgorithmException {
        HttpResponse response = null;
        try {
                response = getHttpClient()
                        .execute(rawRequest, getHttpClient()
                                .getLocalContext());
            if (expectedStatusCode != response.getStatusLine().getStatusCode()) {
                throw new RuntimeException("Incorrect status code. Actual:"
                        + response.getStatusLine().getStatusCode() + " Expected:" + expectedStatusCode);
            }
        }catch (IOException e) {
            throw new RuntimeException("Exception in sendAndGet", e);
        }
        EntityUtils.consumeQuietly(response.getEntity());
        return response;
    }

    private DefaultSecureHttpClient getHttpClient() throws IOException, NoSuchAlgorithmException {
        return DefaultSecureHttpClientSingleton.getInstance();
    }
}
