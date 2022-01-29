package web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GetUserById;
import service.LoginVerify;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/28 19:49
 */

@WebServlet(name = "Login", urlPatterns = "/Login")
public class Login extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger("Login.class");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取数据
        Integer id = Integer.valueOf((request.getParameter("id")).trim());
        String password = request.getParameter("password").trim();
        LOGGER.info("输入的id和密码：" + id + "," + password);
        //notice 接收数组用getParameterValues
        List<String> remember = List.of(request.getParameterValues("remember"));
        LOGGER.info("勾选了：" + remember);

        // 处理
        LoginVerify.LoginStatus loginStatus = new LoginVerify().login(id, password);
        Cookie cookie;
        if (remember.contains("account")) {
            //使用Cookie保存id,如果保存中文需要URL编码
            String id_str = URLEncoder.encode(id.toString(), StandardCharsets.UTF_8);
            cookie = new Cookie("id", id_str);
            //设置Cookie存活时间  7天
            cookie.setMaxAge(60 * 60 * 24 * 7);
                /*
                    notice setMaxAge(int seconds)存活时间
                        - 正数：存活的秒数
                        - 复数：默认值，浏览器关闭时，cookie销毁
                        - 0：删除cookie
                 */
        } else {
            //notice 删除（思路就是替换原来的cookie,并设置它的生存时间为0）
            cookie = new Cookie("id", null);
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath());
            /*
                notice 共享Cookie setPath()   setDomain()
                    - 本机tomcat/webapp下有两个应用appA和appB，在appA中设置的Cookie，appB下获取不到，path默认是产生cookie的应用的路径
                        - 如果在appA中设置cookie时，加上cookie.setPath("/")或cookie.setPath("/appB/")就可以在appB下获取到cookie
                            - cookie.setPath("/appB/")：只能在appB中获得，产生cookie的应用也不行
                        - setPath的参数是相对于应用服务器存放应用的文件夹的根目录，比如tomcat下的webapp
                        - 设置cookie.setPath("/appB/jsp")或cookie.setPath("/appB/jsp/")：只能在appB/jsp里可以获得cookie
                        - 有多个setPath时，以最后一个为准
                    - 跨域共享  A机所在域：aaa.com有应用A   B机所在域：bbb.com有应用B
                        - 在A中设置cookie时，增加coolie.setDomain(".bbb.com")，在B中可以取到cookie，默认在A也可以访问
                        - 参数必须以 . 开始
                        - 输入url访问才可以（http://bbb.com/B可以获取到A的cookie，在B机器访问http://localhost/B不可以获得cookie）
             */
        }
        response.addCookie(cookie);

        //获取session对象
        HttpSession httpSession = request.getSession();
        if (remember.contains("password")) {
            //存储数据到session域
            httpSession.setAttribute("password", password);
        } else {
            //根据key删除键值对
            httpSession.removeAttribute("password");
        }
        /*
            notice session
                - 钝化：服务器正常关闭时，Tomcat自动将session数据写入硬盘
                - 活化：服务器再次启动，从文件中加载数据到session
                - session的销毁：默认情况下，无操作，30分钟自动销毁
                    - 在tomcat的<session-config>中设置
                    - 调用session对象的invalidate()方法
            notice cookie和session
                - 都是完成 一次会话多次请求 之间的数据共享
                - 区别
                    - cookie将数据存到客户端，session存到服务端
                    - cookie不安全，session安全
                    - cookie最大3KB，session无大小限制
                    - cookie可以长期存储，session默认30分钟
                    - cookie不占服务器资源，session占用
                    - session的通过cookie实现（标识服务器上的session对象）
         */

        // 返回
        if (loginStatus == LoginVerify.LoginStatus.Success) {
            httpSession.setAttribute("user", GetUserById.getUserById(id));
            response.sendRedirect("/show.jsp");
        } else {
            httpSession.setAttribute("login_msg", loginStatus.name());
            response.sendRedirect("/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
