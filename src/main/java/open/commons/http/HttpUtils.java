/*
 * Copyright 2011 Park Jun-Hong (parkjunhong77/gmail/com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is generated under this project, "open-commons-http".
 *
 * Date  : 2014. 6. 20. 오후 4:23:24
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import open.commons.http.helper.DefaultHttpRequestHelper;

/**
 * 
 * @since 2014. 6. 20.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class HttpUtils {

    private static Logger log = LogManager.getLogger(HttpUtils.class);

    /**
     * {@link AutoCloseable}를 모두 닫는다.
     * 
     * @param closeables
     *            {@link AutoCloseable} 객체들.
     */
    private static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists, is closed.
     * 
     * @param entity
     * @throws IOException
     *             if an error occurs reading the input stream
     * 
     * @since 4.1
     */
    public static void consume(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            InputStream instream = entity.getContent();
            if (instream != null) {
                instream.close();
            }
        }
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists, is closed. The process is
     * done, <i>quietly</i> , without throwing any IOException.
     * 
     * @param entity
     * 
     * 
     * @since 4.2
     */
    public static void consumeQuietly(final HttpEntity entity) {
        try {
            consume(entity);
        } catch (IOException ioex) {
        }
    }

    private static CloseableHttpClient createClient() {

        // Lookup
        RegistryBuilder<ConnectionSocketFactory> regBuilder = RegistryBuilder.create();
        regBuilder.register("http", new PlainConnectionSocketFactory());

        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new ManagedHttpClientConnectionFactory();

        // use org.apache.http.conn.SchemePortResolver for default port resolution and org.apache.http.config.Registry
        // for socket factory lookups.
        SchemePortResolver schemePortResolver = new DefaultSchemePortResolver();
        DnsResolver dnsResolver = new SystemDefaultDnsResolver();

        HttpClientConnectionManager manager = new BasicHttpClientConnectionManager(regBuilder.build(), connectionFactory, schemePortResolver, dnsResolver);

        // connection config
        ConnectionConfig.Builder conBuilder = ConnectionConfig.custom();
        conBuilder.setCharset(Charset.forName("UTF-8"));

        ConnectionConfig conConfig = conBuilder.build();

        // request config
        RequestConfig.Builder reqBuilder = RequestConfig.custom();
        reqBuilder.setSocketTimeout(2 * 1000) //
                .setConnectTimeout(2 * 1000);

        RequestConfig reqConfig = reqBuilder.build();

        // client
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultConnectionConfig(conConfig) //
                .setDefaultRequestConfig(reqConfig) //
                .setConnectionManager(manager);

        CloseableHttpClient client = httpClientBuilder.build();

        return client;
    }

    /**
     * 
     * HTTPS 연결객체를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2016. 11. 21		박준홍			최초 작성
     * 2019. 4. 9.		박준홍			최초 작성
     * </pre>
     *
     * @param allowPrivateCA
     * @return
     * @throws KeyManagementException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     *
     * @since 2019. 4. 9.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private static CloseableHttpClient createHttpsClient(boolean allowPrivateCA) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {

        // Lookup
        RegistryBuilder<ConnectionSocketFactory> regBuilder = createRegistryBuilder(allowPrivateCA);

        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new ManagedHttpClientConnectionFactory();

        // use org.apache.http.conn.SchemePortResolver for default port resolution and org.apache.http.config.Registry
        // for socket factory lookups.
        SchemePortResolver schemePortResolver = new DefaultSchemePortResolver();
        DnsResolver dnsResolver = new SystemDefaultDnsResolver();

        HttpClientConnectionManager manager = new BasicHttpClientConnectionManager(regBuilder.build(), connectionFactory, schemePortResolver, dnsResolver);

        // connection config
        ConnectionConfig.Builder conBuilder = ConnectionConfig.custom();
        conBuilder.setCharset(Charset.forName("UTF-8"));

        ConnectionConfig conConfig = conBuilder.build();

        // request config
        RequestConfig.Builder reqBuilder = RequestConfig.custom();
        reqBuilder.setSocketTimeout(2 * 1000) //
                .setConnectTimeout(2 * 1000);

        RequestConfig reqConfig = reqBuilder.build();

        // client
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultConnectionConfig(conConfig) //
                .setDefaultRequestConfig(reqConfig) //
                .setConnectionManager(manager);

        CloseableHttpClient client = httpClientBuilder.build();

        return client;
    }

    /**
     * 
     * <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2019. 4. 9.		박준홍			최초 작성
     * </pre>
     *
     * @param allowPrivateCA
     *            사설인증서 자동 허용 여부
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws KeyStoreException
     *
     * @since 2019. 4. 9.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private static RegistryBuilder<ConnectionSocketFactory> createRegistryBuilder(boolean allowPrivateCA)
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        // Lookup
        RegistryBuilder<ConnectionSocketFactory> regBuilder = RegistryBuilder.create();
        if (allowPrivateCA) {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
            regBuilder.register("https", new SSLConnectionSocketFactory(sslContext, new AllowAllHostnameVerofier()));
            regBuilder.register("http", new PlainConnectionSocketFactory());
        } else {
            SSLContext sslContext = SSLContext.getDefault();
            regBuilder.register("https", new SSLConnectionSocketFactory(sslContext));
            regBuilder.register("http", new PlainConnectionSocketFactory());
        }

        return regBuilder;
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     * 
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     *
     */
    public static ResponseClient doDelete(String host, int port, String url, final Map<String, ?> parameters) {
        return doRequest(HttpMethod.DELETE, host, port, url, new open.commons.http.HttpRequestBaseHelper(parameters));
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     *
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doGet(String host, int port, String url, final Map<String, Object> parameters) {
        return doRequest(HttpMethod.GET, host, port, url, new open.commons.http.HttpRequestBaseHelper(parameters));
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     *
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doHead(String host, int port, String url, final Map<String, Object> parameters) {
        return doRequest(HttpMethod.HEAD, host, port, url, new open.commons.http.HttpRequestBaseHelper(parameters));
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     *
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doOptions(String host, int port, String url, final Map<String, Object> parameters) {
        return doRequest(HttpMethod.OPTIONS, host, port, url, new open.commons.http.HttpRequestBaseHelper(parameters));
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     *
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doPatch(String host, int port, String url, final String json) {
        return doRequest(HttpMethod.PATCH, host, port, url, new open.commons.http.HttpJSONEntityRequestBaseHelper(json));
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     *
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doPost(String host, int port, String url, final String json) {
        return doRequest(HttpMethod.POST, host, port, url, new open.commons.http.HttpJSONEntityRequestBaseHelper(json));
    }

    /**
     * 
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     *
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doPut(String host, int port, String url, final String json) {
        return doRequest(HttpMethod.PUT, host, port, url, new open.commons.http.HttpJSONEntityRequestBaseHelper(json));
    }

    public static ResponseClient doRequest(CloseableHttpClient client, HttpMethod method, String host, int port, String url,
            open.commons.http.AbstractDoRequestHelper requestHelper) {

        HttpResponse response = null;

        HttpRequestBase httpRequest = null;
        URIBuilder uriBuilder = null;

        try {
            uriBuilder = new URIBuilder(url);
            requestHelper.beforeHttpRequest(uriBuilder);

            switch (method) {
                case GET:
                    httpRequest = new HttpGet(uriBuilder.build());
                    break;
                case POST:
                    httpRequest = new HttpPost(uriBuilder.build());
                    break;
                case DELETE:
                    httpRequest = new HttpDelete(uriBuilder.build());
                    break;
                case PUT:
                    httpRequest = new HttpPut(uriBuilder.build());
                    break;
                case TRACE:
                    httpRequest = new HttpTrace(uriBuilder.build());
                    break;
                case HEAD:
                    httpRequest = new HttpHead(uriBuilder.build());
                    break;
                case OPTIONS:
                    httpRequest = new HttpOptions(uriBuilder.build());
                    break;
                case PATCH:
                    httpRequest = new HttpPatch(uriBuilder.build());
                    break;
                default:
                    break;
            }

            requestHelper.afterHttpRequest(httpRequest);

            // host
            HttpHost httpHost = new HttpHost(host, port);

            response = client.execute(httpHost, httpRequest);

        } catch (ConnectTimeoutException e) {
            StatusLine sl = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_REQUEST_TIMEOUT, "서버의 요청 대기가 시간을 초과하였다." + e.getMessage());
            response = new BasicHttpResponse(sl);
        } catch (SocketTimeoutException e) {
            StatusLine sl = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_REQUEST_TIMEOUT, "소켓 연결 대기시간을 초과하였습니다." + e.getMessage());
            response = new BasicHttpResponse(sl);
        } catch (Exception e) {
            StatusLine sl = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_INTERNAL_SERVER_ERROR, "내부 오류 발생 - " + e.getMessage());
            response = new BasicHttpResponse(sl);
        }

        ResponseClient rc = new ResponseClient(client);
        rc.setResponse(response);

        return rc;
    }

    /**
     * 
     * @param method
     * @param host
     * @param port
     * @param url
     * @param requestHelper
     * @return
     *
     */
    public static ResponseClient doRequest(HttpMethod method, String host, int port, String url, open.commons.http.AbstractDoRequestHelper requestHelper) {
        return doRequest0(method, host, port, url, requestHelper, false, false);
    }

    /**
     * HTTP/HTTPS 요청을 처리한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2016. 11. 21		박준홍			최초 작성
     * 2019. 4. 9.		박준홍			사설인증서 허용 기능 추가
     * </pre>
     *
     * @param method
     * @param host
     * @param port
     * @param url
     * @param requestHelper
     *            요청 도우미 객체
     * @param isHttps
     *            HTTPS 접속 여부
     * @param allowPrivateCA
     *            사설인증서 허용 여부
     * @return
     *
     * @since 2019. 4. 9.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    private static ResponseClient doRequest0(HttpMethod method, String host, int port, String url, open.commons.http.AbstractDoRequestHelper requestHelper, boolean isHttps,
            boolean allowPrivateCA) {

        HttpResponse response = null;

        HttpRequestBase httpRequest = null;
        URIBuilder uriBuilder = null;
        CloseableHttpClient client = null;

        try {
            uriBuilder = new URIBuilder(url);
            requestHelper.beforeHttpRequest(uriBuilder);

            switch (method) {
                case GET:
                    httpRequest = new HttpGet(uriBuilder.build());
                    break;
                case POST:
                    httpRequest = new HttpPost(uriBuilder.build());
                    break;
                case DELETE:
                    httpRequest = new HttpDelete(uriBuilder.build());
                    break;
                case PUT:
                    httpRequest = new HttpPut(uriBuilder.build());
                    break;
                case TRACE:
                    httpRequest = new HttpTrace(uriBuilder.build());
                    break;
                case HEAD:
                    httpRequest = new HttpHead(uriBuilder.build());
                    break;
                case OPTIONS:
                    httpRequest = new HttpOptions(uriBuilder.build());
                    break;
                case PATCH:
                    httpRequest = new HttpPatch(uriBuilder.build());
                    break;
                default:
                    break;
            }

            requestHelper.afterHttpRequest(httpRequest);

            // host
            HttpHost httpHost = null;

            if (isHttps) {
                httpHost = new HttpHost(host, port, "https");
                client = createHttpsClient(allowPrivateCA);
            } else {
                httpHost = new HttpHost(host, port);
                client = createClient();
            }

            response = client.execute(httpHost, httpRequest);

        } catch (ConnectTimeoutException e) {
            StatusLine sl = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_REQUEST_TIMEOUT, "서버의 요청 대기가 시간을 초과하였다." + e.getMessage());
            response = new BasicHttpResponse(sl);
        } catch (SocketTimeoutException e) {
            StatusLine sl = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_REQUEST_TIMEOUT, "소켓 연결 대기시간을 초과하였습니다." + e.getMessage());
            response = new BasicHttpResponse(sl);
        } catch (Exception e) {
            StatusLine sl = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_INTERNAL_SERVER_ERROR, "내부 오류 발생 - " + e.getMessage());
            response = new BasicHttpResponse(sl);
        }

        ResponseClient rc = new ResponseClient(client);
        rc.setResponse(response);

        return rc;
    }

    /**
     * HTTPS 요청을 처리한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2016. 11. 21.		박준홍			최초 작성
     * </pre>
     * 
     * @param method
     * @param host
     * @param port
     * @param url
     * @param requestHelper
     * @return
     *
     * @since 2016. 11. 21.
     */
    public static ResponseClient doRequestViaHttps(HttpMethod method, String host, int port, String url, open.commons.http.AbstractDoRequestHelper requestHelper) {
        return doRequest0(method, host, port, url, requestHelper, true, false);
    }

    /**
     * HTTPS 요청을 처리한다.<br>
     * 
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2019. 4. 9.		박준홍			최초 작성
     * </pre>
     *
     * @param method
     * @param host
     * @param port
     * @param url
     * @param requestHelper
     * @param allowPrivateCA
     * @return
     *
     * @since 2019. 4. 9.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static ResponseClient doRequestViaHttps(HttpMethod method, String host, int port, String url, open.commons.http.AbstractDoRequestHelper requestHelper,
            boolean allowPrivateCA) {
        return doRequest0(method, host, port, url, requestHelper, true, allowPrivateCA);
    }

    /**
     * @param host
     * @param port
     * @param url
     * @param parameters
     * @return
     * 
     * @deprecated Use {@link #doRequest(HttpMethod, String, int, String, AbstractDoRequestHelper)} instead of. Will be
     *             removed at next release.
     */
    public static ResponseClient doTrace(String host, int port, String url, final Map<String, Object> parameters) {
        return doRequest(HttpMethod.TRACE, host, port, url, new open.commons.http.HttpRequestBaseHelper(parameters));
    }

    public static List<NameValuePair> mapToParameters(Map<String, ?> paramsMap) {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        if (paramsMap == null) {
            return parameters;
        }

        for (Entry<String, ?> entry : paramsMap.entrySet()) {
            try {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            } catch (Exception ignored) {
                log.warn(ignored);
            }
        }

        return parameters;
    }

    /**
     * {@link HttpResponse} 객체를 {@link ResponseClient}로부터 획득한 경우 반드시 {@link ResponseClient#close()} 한다.
     * 
     * @param response
     * @return
     *
     * @since 2015. 1. 6.
     */
    public static String readResponseEntity(HttpResponse response) {
        String responseString = null;

        if (response != null) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
                consumeQuietly(response.getEntity());
            } else {
                HttpEntity entity = response.getEntity();

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));

                    StringBuffer sb = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    responseString = sb.toString();
                } catch (IOException e) {
                    responseString = null;
                } catch (Exception e) {
                    responseString = null;
                }
            }
        }

        return responseString;
    }

    /**
     * {@link ResponseClient}에 포함된 {@link HttpResponse}로부터 데이터를 얻은 후 {@link CloseableHttpClient#close()}를 실행한다.
     * 
     * @param responseClient
     * @return
     *
     * @since 2015. 1. 6.
     */
    public static String readResponseEntity(ResponseClient responseClient) {
        String responseString = null;
        HttpResponse response = responseClient.getResponse();

        if (response != null) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
                consumeQuietly(response.getEntity());
            } else {
                HttpEntity entity = response.getEntity();

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));

                    StringBuffer sb = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    responseString = sb.toString();
                } catch (IOException e) {
                    responseString = null;
                } catch (Exception e) {
                    responseString = null;
                }
            }

            try {
                responseClient.close();
            } catch (IOException ignored) {
            }
        }

        return responseString;
    }

    /**
     * {@link HttpResponse} 객체를 {@link ResponseClient}로부터 획득한 경우 반드시 {@link ResponseClient#close()} 한다.
     * 
     * @param response
     * @return
     *
     * @since 2015. 1. 6.
     */
    public static byte[] readResponseEntityAsByteArray(HttpResponse response) {

        if (response == null) {
            return null;
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
            consumeQuietly(response.getEntity());
            return null;
        } else {
            try {

                HttpEntity entity = response.getEntity();
                InputStream inStream = entity.getContent();

                int contentLen = -1;

                Header header = response.getFirstHeader("content-length");
                if (header != null) {
                    contentLen = Integer.parseInt(header.getValue());
                }

                if (contentLen < 1) {
                    return null;
                }

                return readStream(inStream, contentLen);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * {@link ResponseClient}에 포함된 {@link HttpResponse}로부터 데이터를 얻은 후 {@link CloseableHttpClient#close()}를 실행한다.
     * 
     * @param responseClient
     * @return
     *
     * @since 2015. 1. 6.
     */
    public static byte[] readResponseEntityAsByteArray(ResponseClient responseClient) {
        byte[] entityBytes = null;

        HttpResponse response = responseClient.getResponse();

        if (response != null) {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_IMPLEMENTED) {
                consumeQuietly(response.getEntity());
            } else {
                try {

                    HttpEntity entity = response.getEntity();
                    InputStream inStream = entity.getContent();

                    int contentLen = -1;

                    Header header = response.getFirstHeader("content-length");
                    if (header != null) {
                        contentLen = Integer.parseInt(header.getValue());
                    }

                    if (contentLen > 0) {
                        entityBytes = readStream(inStream, contentLen);
                    }

                } catch (Exception e) {
                }
            }
        }

        try {
            responseClient.close();
        } catch (IOException ignored) {
        }

        return entityBytes;
    }

    /**
     * 입력받은 InputStream에서 주어진 길이만큼 데이터를 읽어서 반환한다.
     * 
     * @param inStream
     * @param length
     *            읽어올 데이터 길이
     * @return InputStream으로부터 읽어온 데이터. 예외가 발생하는 경우 <code>null</code> 반환.
     */
    private static byte[] readStream(InputStream inStream, final int length) {

        ByteBuffer buf = ByteBuffer.allocateDirect(length);

        ReadableByteChannel reader = Channels.newChannel(inStream);

        int total = 0;
        int count = 0;

        try {
            while (total < length && (count = reader.read(buf)) > 0) {
                total += count;
            }

            byte[] read = new byte[length];

            buf.clear();
            buf.get(read);

            return read;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(inStream, reader);
        }

        return null;
    }

    public static boolean sayOk(StatusLine statusLine, int... httpStatuses) {

        if (httpStatuses == null) {
            return false;
        }

        int value = statusLine.getStatusCode();
        for (int i : httpStatuses) {
            if (i == value) {
                return true;
            }
        }

        return false;
    };

    /**
     * 
     * 
     * @since 2015. 1. 6.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     * 
     * @deprecated Use {@link open.commons.http.AbstractDoRequestHelper} instead of. Will be removed at next release.
     */
    public static abstract class AbstractDoRequestHelper {

        public void afterHttpRequest(HttpRequestBase request) {

            request.setHeader("http.useragent", "HttpClient");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json" + ";charset=UTF-8");

            RequestConfig.Builder reqBuilder = RequestConfig.custom();
            reqBuilder.setSocketTimeout(2 * 1000) //
                    .setConnectTimeout(2 * 1000);

            RequestConfig reqConfig = reqBuilder.build();

            request.setConfig(reqConfig);
        }

        public void beforeHttpRequest(URIBuilder builder) {
        }
    }

    private static class AllowAllHostnameVerofier implements HostnameVerifier {
        /**
         * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)
         */
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }

    }

    /**
     * 
     * 
     * @since 2015. 1. 6.
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     * 
     * @deprecated Use {@link open.commons.http.HttpJSONEntityRequestBaseHelper} instead of. Will be removed at next
     *             release.
     */
    public static class HttpJSONEntityRequestBaseHelper extends AbstractDoRequestHelper {

        private String json;

        public HttpJSONEntityRequestBaseHelper(String json) {
            this.json = json;
        }

        /**
         * @see kr.ymtech.sdn.http.HttpUtils.AbstractDoRequestHelper#afterHttpRequest(org.apache.http.client.methods.HttpRequestBase)
         */
        @Override
        public void afterHttpRequest(HttpRequestBase request) {
            super.afterHttpRequest(request);

            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                if (json != null) {
                    try {
                        ByteArrayEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
                        ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
                    } catch (UnsupportedEncodingException e) {
                        log.warn("Encoding is wrong.", e);
                    }
                }
            }
        }
    }

    /**
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     * @deprecated Use {@link DefaultHttpRequestHelper} instead of. Will be removed at next release.
     */
    public static class HttpRequestBaseHelper extends AbstractDoRequestHelper {

        private Map<String, ?> parameters;

        /**
         * 
         * @since 2014. 6. 24.
         */
        public HttpRequestBaseHelper() {
        }

        /**
         * 
         * @since 2014. 6. 24.
         */
        public HttpRequestBaseHelper(Map<String, ?> parameters) {
            this.parameters = parameters;
        }

        /**
         * @see kr.ymtech.sdn.http.HttpUtils.AbstractDoRequestHelper#beforeHttpRequest(org.apache.http.client.utils.URIBuilder)
         */
        @Override
        public void beforeHttpRequest(URIBuilder builder) {
            super.beforeHttpRequest(builder);

            List<NameValuePair> params = mapToParameters(parameters);
            builder.addParameters(params);
        }
    }
}