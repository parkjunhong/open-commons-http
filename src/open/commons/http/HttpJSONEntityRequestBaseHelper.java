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
 * Date  : 2018. 10. 15. 오전 4:30:51
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;

/**
 * 
 * @since 2018. 10. 15.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class HttpJSONEntityRequestBaseHelper extends AbstractDoRequestHelper {

    private String json;

    public HttpJSONEntityRequestBaseHelper(String json) {
        this.json = json;
    }

    /**
     * 
     * @see open.commons.http.AbstractDoRequestHelper#afterHttpRequest(org.apache.http.client.methods.HttpRequestBase)
     */
    public void afterHttpRequest(HttpRequestBase request) {
        super.afterHttpRequest(request);

        if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
            if (json != null) {
                try {
                    ByteArrayEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
                    ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
                } catch (UnsupportedEncodingException e) {
                    logger.warn("Encoding is wrong.", e);
                }
            }
        }
    }
}