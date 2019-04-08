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
 * Date  : 2018. 10. 15. 오전 4:41:02
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import open.commons.http.helper.DefaultHttpRequestHelper;

/**
 * 
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 * @deprecated Use {@link DefaultHttpRequestHelper} instead of. Will be removed at next release.
 */
public class HttpRequestBaseHelper extends AbstractDoRequestHelper {

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

        List<NameValuePair> params = HttpUtils.mapToParameters(parameters);
        builder.addParameters(params);
    }
}