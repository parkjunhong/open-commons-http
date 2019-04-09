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
 * Date  : 2019. 4. 9. 오후 3:13:15
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package https;

import org.apache.http.StatusLine;

import open.commons.http.AbstractDoRequestHelper;
import open.commons.http.HttpMethod;
import open.commons.http.HttpUtils;
import open.commons.http.ResponseClient;
import open.commons.http.helper.DefaultHttpRequestHelper;

/**
 * 
 * @since 2019. 4. 9.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class TurnOffCertValidFailMain {

    /**
     * 
     * @since 2019. 4. 9.
     */
    public TurnOffCertValidFailMain() {
    }

    public static void main(String[] args) {

        HttpMethod method = HttpMethod.GET;
        String host = "vms.ymtech.co.kr";
        int port = 443;
        String url = "/";

        AbstractDoRequestHelper helper = new DefaultHttpRequestHelper();

        request(method, host, port, url, helper, false);
        System.out.println("=======================================================");
        request(method, host, port, url, helper, true);
    }

    static void request(HttpMethod method, String host, int port, String url, AbstractDoRequestHelper helper, boolean allowPrivateCA) {

        ResponseClient resclient = HttpUtils.doRequestViaHttps(method, host, port, url, helper, allowPrivateCA);

        StatusLine statusline = resclient.getResponse().getStatusLine();
        if (HttpUtils.sayOk(statusline, 200)) {
            String entity = HttpUtils.readResponseEntity(resclient.getResponse());
            System.out.println(entity);
        } else {
            System.out.println(statusline);
        }
    }
}
