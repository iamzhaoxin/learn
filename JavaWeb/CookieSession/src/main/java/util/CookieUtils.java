package util;

import javax.servlet.http.Cookie;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/29 8:33
 */
public class CookieUtils {
    public static String getValueByName(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
