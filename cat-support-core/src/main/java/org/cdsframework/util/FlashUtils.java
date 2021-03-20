/**
 * CAT Core support plugin project.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
 * details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
 * Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
 * limitation, and warranty) complete irrevocable access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cdsframework.message.MessageMGR;

/**
 *
 * @author HLN Consulting, LLC
 */
public class FlashUtils {

    private static final LogUtils logger = LogUtils.getLogger(FlashUtils.class);
    private static final String FLASH_COOKIE_KEY = "CAT_FLASH";
    private static final String FLASH_COOKIE_MESSAGEKEY_MAP_KEY = "messageKey";

    /**
     * Create a flash scope outside of the phase listener via a cookie in order to display the filter error message
     *
     * @param request
     * @param response
     * @param messageKey
     */
    public static void addFlashErrorMessageKey(HttpServletRequest request, HttpServletResponse response, String messageKey) {
        final String METHODNAME = "addFlashErrorMessageKey ";
        if (request != null && response != null) {
            Map<String, Object> flashScope = new HashMap<String, Object>();
            flashScope.put(FLASH_COOKIE_MESSAGEKEY_MAP_KEY, messageKey);
            String flashScopeId = UUID.randomUUID().toString();
            request.getSession().setAttribute(flashScopeId, flashScope);
            Cookie cookie = new Cookie(FLASH_COOKIE_KEY, flashScopeId);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        } else {
            logger.error(METHODNAME, "either the request or response is null for error mesage key: ", messageKey, " - ", request, " - ", response);
        }
    }

    public static void processFlashScope(HttpServletRequest request, HttpServletResponse response) {
        final String METHODNAME = "processFlashScope ";
        if (request != null && response != null) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (FLASH_COOKIE_KEY.equals(cookie.getName())) {
                        Map<String, Object> flashScope = (Map<String, Object>) request.getSession().getAttribute(cookie.getValue());

                        if (flashScope != null) {
                            request.getSession().removeAttribute(cookie.getValue());

                            for (Map.Entry<String, Object> entry : flashScope.entrySet()) {
                                if (FLASH_COOKIE_MESSAGEKEY_MAP_KEY.equals(entry.getKey())) {
                                    try {
                                        MessageMGR messageMGR = BeanUtils.getBean(MessageMGR.class);
                                        if (messageMGR != null) {
                                            messageMGR.displayErrorMessageKey(false, (String) entry.getValue(), null);
                                        }
                                    } catch (Exception e) {
                                        logger.error(e);
                                    }
                                    break;
                                }
                            }
                        }

                        cookie.setValue(null);
                        cookie.setMaxAge(0);
                        cookie.setPath(request.getContextPath());
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        } else {
            logger.error(METHODNAME, "either the request or response is null: ", request, " - ", response);
        }
    }
}