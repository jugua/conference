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


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class DefaultSecureHttpClient extends DefaultHttpClient {

    private HttpContext localContext;
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultSecureHttpClient.class);

    public DefaultSecureHttpClient() throws IOException, NoSuchAlgorithmException {
        this(buildHttpParams());
    }

    public DefaultSecureHttpClient(final HttpParams params) throws IOException, NoSuchAlgorithmException {
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

    private static ClientConnectionManager buildConnectionManager(final HttpParams params) throws IOException, NoSuchAlgorithmException {
        ClientConnectionManager ccm = null;

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sslSocketFactory = new DefaultSSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sslSocketFactory, 443));

            ccm = new ThreadSafeClientConnManager(params, registry);

            LOG.info("Secure client was initialized successfully");

        } catch (CertificateException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException
                | IOException | KeyManagementException e) {
            throw new RuntimeException("Cannot initialize client!", e);
        }

        return ccm;
    }

    public HttpContext getLocalContext() {
        return localContext;
    }

    private static class DefaultSSLSocketFactory extends SSLSocketFactory {

        private SSLContext sslContext = SSLContext.getInstance("TLS");

        public DefaultSSLSocketFactory(final KeyStore trustStore)
                throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(trustStore);

            TrustManager trustManager = new X509TrustManager() {
                public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                        throws CertificateException {
                }

                public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                        throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            sslContext.init(null, new TrustManager[] { trustManager }, null);
        }

        @Override
        public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose)
                throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

}
