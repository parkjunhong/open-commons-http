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
 * Date  : 2018. 10. 15. 오전 4:30:24
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

/**
 * 
 * @since 2018. 10. 15.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class AbstractDoRequestHelper {
    
    private Logger log = Logger.getLogger(getClass());

    /**
     * 
     * @since 2018. 10. 15.
     */
    public AbstractDoRequestHelper() {
    }

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
