package com.epam.cm.core.httpclient;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import com.epam.cm.core.logger.LoggerFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;


import java.security.KeyStore;

/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class DefaultSecureHttpClient extends DefaultHttpClient {

    private HttpContext localContext;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultSecureHttpClient.class);

    public DefaultSecureHttpClient() {
        this(buildHttpParams());
    }

    public DefaultSecureHttpClient(final HttpParams params) {
        super(buildConnectionManager(params), params);
        localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, getCookieStore());
    }

    private static HttpParams buildHttpParams() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        return params;
    }

    private static ClientConnectionManager buildConnectionManager(final HttpParams params) {
        ClientConnectionManager ccm = null;

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sslSocketFactory, 443));

            ccm = new ThreadSafeClientConnManager(params, registry);

            LOG.info("Secure client was initialized successfully");

        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize client!", e);
        }

        return ccm;
    }
}
