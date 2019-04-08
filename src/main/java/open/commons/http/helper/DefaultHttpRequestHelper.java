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
 *
 * This file is generated under this project, "open-commons-http".
 *
 * Date  : 2015. 3. 17. 오전 9:34:59
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http.helper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;

import open.commons.http.AbstractDoRequestHelper;
import open.commons.http.HttpUtils;

/**
 * 
 * 
 * @since 2015. 3. 17.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class DefaultHttpRequestHelper extends AbstractDoRequestHelper {

    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> parameters = new HashMap<>();
    private byte[] entity;

    /**
     * Defines the socket timeout (<code>SO_TIMEOUT</code>) in milliseconds, which is the timeout for waiting for data
     * or, put differently, a maximum period inactivity between two consecutive data packets).
     * <p/>
     * A timeout value of zero is interpreted as an infinite timeout. A negative value is interpreted as undefined
     * (system default).
     * <p/>
     * 
     * Default: <code>-1</code>
     * 
     * @see
     */
    private int socketTimeout = -1;

    /**
     * Defines the socket timeout (<code>SO_TIMEOUT</code>) in milliseconds, which is the timeout for waiting for data
     * or, put differently, a maximum period inactivity between two consecutive data packets).
     * <p/>
     * A timeout value of zero is interpreted as an infinite timeout. A negative value is interpreted as undefined
     * (system default).
     * <p/>
     * Default: <code>5000</code>
     */
    private int connectTimeout = 5000;

    /**
     * 기본값으로 설정.<br>
     * {@link #socketTimeout}: -1, {@link #connectTimeout}: 5000ms
     * 
     * @since 2015. 3. 13.
     */
    public DefaultHttpRequestHelper() {
    }

    /**
     * 기본값으로 설정.<br>
     * {@link #socketTimeout}: -1, {@link #connectTimeout}: 5000ms
     * 
     * @since 2015. 3. 13.
     * 
     * @see {@link HttpRequestHelper(int, int)}
     */
    public DefaultHttpRequestHelper(byte[] entity) {
        this.entity = entity;
    }

    /**
     * 
     * @param entity
     * @param socketTimeout
     *            millisecond
     * @param connectionTimeout
     *            millisecond
     * @since 2015. 3. 16.
     */
    public DefaultHttpRequestHelper(byte[] entity, int socketTimeout, int connectionTimeout) {
        this.entity = entity;

        this.socketTimeout = socketTimeout;
        this.connectTimeout = connectionTimeout;
    }

    /**
     * 
     * @param socketTimeout
     *            millisecond. see {@link #socketTimeout}
     * @param connectionTimeout
     *            millisecond. see {@link #connectTimeout}
     * @since 2015. 3. 13.
     */
    public DefaultHttpRequestHelper(int socketTimeout, int connectionTimeout) {
        this.socketTimeout = socketTimeout;
        this.connectTimeout = connectionTimeout;
    }

    /**
     * 기본값으로 설정.<br>
     * {@link #socketTimeout}: -1, {@link #connectTimeout}: 5000ms
     * 
     * @since 2015. 3. 13.
     * 
     * @see {@link DefaultHttpRequestHelper(int, int)}
     */
    public DefaultHttpRequestHelper(String entity) {
        setEntity0(entity);
    }

    /**
     * 
     * @param socketTimeout
     *            millisecond
     * @param connectionTimeout
     *            millisecond
     * @since 2015. 3. 13.
     */
    public DefaultHttpRequestHelper(String entity, int socketTimeout, int connectionTimeout) {
        setEntity0(entity);

        this.socketTimeout = socketTimeout;
        this.connectTimeout = connectionTimeout;
    }

    /**
     * 동일한 이름을 갖는 헤더가 있는 경우 갱신된다.
     * 
     * @param key
     * @param value
     * @return
     *
     * @since 2015. 3. 16.
     */
    public DefaultHttpRequestHelper addHeader(String key, Object value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }

        this.headers.put(key, value);

        return this;
    }

    public DefaultHttpRequestHelper addHeaders(Map<String, Object> headers) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        } else {
            this.headers.putAll(headers);
        }

        return this;
    }

    public DefaultHttpRequestHelper addParameter(String key, Object value) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }

        this.parameters.put(key, value);

        return this;
    }

    public DefaultHttpRequestHelper addParameters(Map<String, Object> parameters) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        } else {
            this.parameters.putAll(parameters);
        }

        return this;
    }

    @Override
    public void afterHttpRequest(HttpRequestBase request) {
        setHeader(request);

        RequestConfig.Builder reqBuilder = setRequestConfig(request);
        request.setConfig(reqBuilder.build());

        if (headers != null) {
            for (Entry<String, Object> header : headers.entrySet()) {
                request.setHeader(header.getKey(), header.getValue().toString());
            }
        }

        setEntity(request);
    }

    @Override
    public void beforeHttpRequest(URIBuilder builder) {
        super.beforeHttpRequest(builder);

        List<NameValuePair> params = HttpUtils.mapToParameters(parameters);
        builder.addParameters(params);
    }

    public DefaultHttpRequestHelper setEntity(byte[] entity) {
        this.entity = entity;

        return this;
    }

    protected void setEntity(HttpRequestBase request) {
        if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
            if (entity != null) {
                ByteArrayEntity byteEntity = new ByteArrayEntity(entity);
                ((HttpEntityEnclosingRequestBase) request).setEntity(byteEntity);
            }
        }
    }

    public DefaultHttpRequestHelper setEntity(String json) {
        setEntity0(json);

        return this;
    }

    private void setEntity0(String string) {
        if (string != null) {
            try {
                this.entity = string.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
        }
    }

    /**
     * 
     * @param request
     *
     * @since 2015. 3. 17.
     */
    protected void setHeader(HttpRequestBase request) {
        request.setHeader("http.useragent", "HttpClient");
    }

    public DefaultHttpRequestHelper setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;

        return this;
    }

    /**
     * Return a {@link RequestConfig.Builder} instance that contains <b><code>http.socket.timeout</code></b>(default
     * value: -1) and <b><code>http.connection.timeout</code></b>(default value: 5000ms).
     * 
     * @param request
     * @return
     *
     * @since 2015. 3. 17.
     */
    protected RequestConfig.Builder setRequestConfig(HttpRequestBase request) {
        RequestConfig.Builder reqBuilder = RequestConfig.custom();
        reqBuilder.setSocketTimeout(socketTimeout) //
                .setConnectTimeout(connectTimeout);

        request.setConfig(reqBuilder.build());

        return reqBuilder;
    }
}
