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
 * Date  : 2015. 1. 6. 오후 5:00:09
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http;

import java.io.Closeable;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * {@link CloseableHttpClient}와 {@link HttpResponse} 를 관리하는 클래스.
 * 
 * @since 2015. 1. 6.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class ResponseClient implements Closeable {

    private CloseableHttpClient client;

    private HttpResponse response;

    public ResponseClient(CloseableHttpClient client) {
        this.client = client;
    }

    /**
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
    }

    /**
     *
     * @return the client
     *
     * @since 2015. 1. 6.
     */
    public CloseableHttpClient getClient() {
        return client;
    }

    /**
     *
     * @return the response
     *
     * @since 2015. 1. 6.
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * @param response
     *            the response to set
     *
     * @since 2015. 1. 6.
     */
    public void setResponse(HttpResponse response) {
        this.response = response;
    }

}
