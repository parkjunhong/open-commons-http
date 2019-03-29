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
 * Date  : 2017. 3. 14. 오전 10:15:38
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http.response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @since 2017. 3. 14.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class HttpResponseUtils {

    /**
     * 
     * @since 2017. 3. 14.
     */
    private HttpResponseUtils() {
    }

    /**
     * URL을 decoding 한다.
     * 
     * @param url
     * 
     * @param enc
     *            인코딩
     * @return
     * @throws UnsupportedEncodingException
     * 
     * @see {@link URLDecoder}
     */
    public static String decodeUrl(String url, String enc) throws UnsupportedEncodingException {
        return url != null ? URLDecoder.decode(url, enc) : null;
    }

    /**
     * URL을 UTF-8로 decoding 한다.
     * 
     * @param url
     *            request 파라미터
     * @return
     * @throws UnsupportedEncodingException
     * 
     * @see {@link URLDecoder}
     */
    public static String decodeUrlAsUTF8(String url) throws UnsupportedEncodingException {
        return decodeUrl(url, "UTF-8");
    }

    /**
     * URL을 encoding 한다.
     * 
     * @param url
     * 
     * @param enc
     *            인코딩
     * @return
     * @throws UnsupportedEncodingException
     * 
     * @see {@link URLDecoder}
     */
    public static String encodeUrl(String url, String enc) throws UnsupportedEncodingException {
        return url != null ? URLEncoder.encode(url, enc) : null;
    }

    /**
     * URL을 UTF-8로 encoding 한다.
     * 
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     * 
     * @see {@link URLDecoder}
     */
    public static String encodeUrlAsUTF8(String url) throws UnsupportedEncodingException {
        return encodeUrl(url, "UTF-8");
    }

    /**
     * no-cache 설정을 위한 헤더 정보를 반환한다.
     * 
     * @return
     *
     * @since 2017. 3. 14.
     */
    public static Map<String, String> noCacheHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.put("Pragma", "no-cache");
        headers.put("Expire", "-1");

        return headers;
    }

}
