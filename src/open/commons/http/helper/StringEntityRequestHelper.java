/*
 * Copyright 2011 Park Jun-Hong_(fafanmama_at_naver_com)
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
 * Date  : 2014. 11. 7. 오전 10:01:24
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
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;

import open.commons.http.HttpUtils;
import open.commons.http.HttpUtils.AbstractDoRequestHelper;

/**
 * 
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 * @deprecated Use {@link DefaultHttpRequestHelper} instead of.
 */
public class StringEntityRequestHelper extends AbstractDoRequestHelper {

    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> parameters = new HashMap<>();
    private String entity;

    public StringEntityRequestHelper addHeader(String key, Object value) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }

        this.headers.put(key, value);

        return this;
    }

    public StringEntityRequestHelper addHeaders(Map<String, Object> headers) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        } else {
            this.headers.putAll(headers);
        }

        return this;
    }

    public StringEntityRequestHelper addParameter(String key, Object value) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }

        this.parameters.put(key, value);

        return this;
    }

    public StringEntityRequestHelper addParameters(Map<String, Object> parameters) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        } else {
            this.parameters.putAll(parameters);
        }

        return this;
    }

    @Override
    public void afterHttpRequest(HttpRequestBase request) {
        super.afterHttpRequest(request);

        if (headers != null) {
            for (Entry<String, Object> header : headers.entrySet()) {
                request.setHeader(header.getKey(), header.getValue().toString());
            }
        }

        if (entity != null) {
            try {
                ByteArrayEntity bytesEntity = new ByteArrayEntity(entity.getBytes("UTF-8"));
                ((HttpEntityEnclosingRequestBase) request).setEntity(bytesEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void beforeHttpRequest(URIBuilder builder) {
        super.beforeHttpRequest(builder);

        List<NameValuePair> params = HttpUtils.mapToParameters(parameters);
        builder.addParameters(params);
    }

    public StringEntityRequestHelper setEntity(String entity) {
        this.entity = entity;

        return this;
    }

    public StringEntityRequestHelper setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;

        return this;
    }
}
