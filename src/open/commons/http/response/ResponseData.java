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
 * Date  : 2017. 3. 14. 오전 10:11:19
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.http.response;

/**
 * 
 * @since 2017. 3. 14.
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class ResponseData {

    public static final int SUCCESS = 0x00;

    public static final int ERROR = 0x01;

    private int status = SUCCESS;

    private String message;

    private Object data;

    /**
     * 
     * @since 2017. 3. 14.
     */
    public ResponseData() {
    }

    /**
     *
     * @return the value
     *
     * @since 2017. 3. 14.
     */
    public Object getData() {
        return data;
    }

    /**
     *
     * @return the message
     *
     * @since 2017. 3. 14.
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return the code
     *
     * @since 2017. 3. 14.
     */
    public int getStatus() {
        return status;
    }

    /**
     * 회신할 데이터를 설정한다.
     * 
     * @param data
     *            the value to set
     *
     * @since 2017. 3. 14.
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @param message
     *            the message to set
     *
     * @since 2017. 3. 14.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 응답 처리 상태를 설정한다.
     * 
     * @param status
     *            <ul>
     *            <li>boolean: 성공
     *            <li>false: 실패
     *            </ul>
     *
     * @since 2017. 3. 14.
     */
    public void setStatus(boolean status) {
        this.status = status ? SUCCESS : ERROR;
    }

    /**
     * @param status
     *            the code to set
     *
     * @since 2017. 3. 14.
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
