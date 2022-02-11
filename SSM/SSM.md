# Spring

> 对应项目：SSM\Spring\spring_ioc

## Spring快速入门

### 开发步骤

> profile.BeanTest#Bean


1. 导入坐标
2. 创建Bean接口和实现类
3. 创建applicationContext.xml
4. 在配置文件中配置（比如：将Bean的id和class对应
5. 创建ApplicationContext对象，通过对象getBean("id")

## Spring配置文件

### Bean

#### 标签范围scope

> profile.BeanTest#Bean

- singleton：默认值，单例
  - Spring核心文件被加载时，创建实例。
  - 应用卸载，销毁容器时，对象被销毁
- prototype：多例
  - 调用getBean时实例化Bean
  - 对象长时间不用时，被Java的垃圾回收器回收
- init-method：指定类中的初始化方法名称
- destory-method：指定销毁方法名称（需要用close方法关闭容器，才能调用）

### Bean实例化三种方式

> factory

- 无参**构造**方法实例化
- 工厂**静态**方法实例化
  - 创建工厂类&静态方法，Xml中Bean标签全限名+`factory-method`
- 工厂**实例**方法实例化

### 依赖注入

> service/impl/UserServiceImpl.java
>
> applicationContext.xml

#### 构造方法

```java
<bean id="userService" class="service.impl.UserServiceImpl">
    <!--构造参数-->
    <constructor-arg name="user1" ref="userDao"/>
</bean>
```

#### set方法

> service.impl.UserServiceImpl#setUserDao

- 在applicationContext.xml中对被注入的Bean添加属性

  - 其中`name`为`setUser1`的`set`后面的`User1`并小写为`user1`
  - `value`用于**对普通属性赋值**,`ref`用于**引用对象**

  ```java
      <bean id="userService" class="service.impl.UserServiceImpl">
          <property name="user1" ref="userDao"/>
      </bean>
  ```

##### P命名空间

本质也是set注入

1. 引入P命名空间

   `xmlns:p="http://www.springframework.org/schema/p"`

2. 修改注入方式

   `<bean id="userService" class="service.impl.UserServiceImpl" p:user1-ref="userDao"/>`

#### 数据类型

- 引用数据类型(ref)

> dao/impl/UserDaoImpl.java

- 普通数据类型

  - 添加Bean对象的属性,并添加`setName()`方法

    ```java
    private String username;
    private int age;
    ```

  - 配置Xml
    
    ```java
    <bean id="userDaoWithParams" class="dao.impl.UserDaoImpl">
        <property name="username" value="zx"/>
        <property name="age" value="22"/>
    </bean>
    ```

- 集合数据类型

  - 添加对应属性,添加set方法
  
    ```java
    private List<String> strList;
    private Map<String, Integer> userMap;
    private Properties properties;
    ```
  
  - ```java
    <bean id="userDaoWithSet" class="dao.impl.UserDaoImpl">
        <property name="strList">
            <list>
                <!--如果List里的是对象,用ref-->
                <value>aaa</value>
                <value>哈哈哈</value>
            </list>
        </property>
        <property name="userMap">
            <map>
                <entry key="zx" value="22"/>
            </map>
        </property>
        <property name="properties">
            <props>
                <prop key="p1">aaa</prop>
                <prop key="p2">bbb</prop>
            </props>
        </property>
    </bean>
    ```

### 引入其他配置文件

用于:分模块开发

`<import resource="applicationContext-user.xml"/>`

## Spring相关API

### ApplicationContext的实现类

```java
ApplicationContext app2=new ClassPathXmlApplicationContext("resouces下的相对路径");	//推荐
ApplicationContext app2=new FileSystemXmlApplicationContext("绝对路径");
ApplicationContext app3=new AnnotationConfigApplicationContext("注解配置");
```

### getBean方法

- 通过id:`getBean("userDao");`
  - 允许容器中出现多个相同类型的对象
- 通过字节码对象:`getBean(UserDao.class);`
  - 容器中只能有一个同类型的对象

# Spring注解

> 项目:SSM/Spring/spring_ioc_anno

## Spring配置数据源

### 手动配置连接池

> dataSource/DataSourceTest.java

- c3p0数据源 略
- druid数据源 略

### Spring配置数据源

> applicationContext.xml

```java
//因为是通过ComboPooledDataSource dataSource = new ComboPooledDataSource();获取数据源对象,所以bean调用的class是ComboPooledDataSource
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
    <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
    <property name="jdbcUrl" value="jdbc:mysql://82.157.138.64:3306"/>
    <property name="user" value="root"/>
    <property name="password" value="123456"/>
</bean>
```

#### 抽取jdbc配置文件

applicationContext.xml加载jdbc.properties配置文件获得连接信息

1. 引入context命名空间和约束路径

   ```xml
   xmlns:context="http://www.springframework.org/schema/context"
   http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd"
   ```

2. 加载外部properties文件`<context:property-placeholder location="jdbc.properties"/>`

3. 使用EL表达式

   ```java
   <bean id="dataSource2" class="com.mchange.v2.c3p0.ComboPooledDataSource">
       <property name="jdbcUrl" value="${jdbc.url}"/>
       <property name="user" value="${jdbc.username}"/>
       <property name="password" value="${jdbc.password}"/>
   </bean>
   ```

## Spring注解开发

> 用注解代替xml,简化配置

### Spring原始注解

主要用于替代bean标签

```java
//<bean id="userDao" class="dao.impl.UserDaoImpl"/>
@Component("userDao")
public class UserDaoImpl implements UserDao {
    private String username;
```

```java
//<property name="user1" ref="userDao"/>
//    @Autowired			//自动按照数据类型从Spring容器中匹配注入
//    @Qualifier("userDao")	//根据id值注入
    private UserDao user1;
-------------或--------------------------
//    @Resource(name = "userDao") //相当于@Autowired+@Qualifier
    private UserDao user1;
-------------或--------------------------
private UserDao user1;
public UserServiceImpl(@Qualifier("userDao") UserDao user1) {
    this.user1 = user1;
}
```

- 使用注解开发时,要在applicationContext.xml中**配置组件扫描**

  - 用于指定哪个包及其子包下的bean需要被扫描,以便识别使用注解配置的 类/字段/方法

    ```xml
        <context:component-scan base-package="dao"/>
        <context:component-scan base-package="service"/>
    ```

### 新注解

- 原始注解无法完全替代Xml配置文件:
  - 非自定义的Bean配置(ComboPooledDataSource)
  - 加载properties文件的配置`<context:property-placeholder>`
  - 组件扫描的配置`<context:component-scan>`
  - 引入其他文件`<import>`

> src/main/java/config/SpringConfiguration.java
>
> src/main/java/config/DataSourceConfiguration.java

通过把配置文件写成类,替代xml文件

## Spring集成Junit

- SpringJunit负责创建Spring容器,需要指定配置文件

- 将需要测试的Bean,在测试类中注入

  > src/test/java/junit/JunitTest.java

  - 导入坐标(junit和spring-test)
  - 用@RunWith替代原测试运行期
  - 用@ContextConfiguration指定配置文件/类
  - 用@Autowired注入测试对象
  - 创建测试方法

# SpringMVC

## Spring集成Web环境

- 每次从容器中获得Bean时,都要通过`new ClasspathXmlApplicationContext("applicationContext.xml")`应用上下文对象
  - 缺点:配置文件加载多次,应用上下文对象创建多次
- 用`ServletContextListener`监听Web应用启动
  - 在启动时加载Spring配置文件,并将上下文对象`ApplicationContext`存储到最大的域`servletContext`中
  - 即可在任意位置从域中获得`ApplicationContext`对象

1. 创建监听器

   > src/main/java/listener/ContextLoaderListener.java

2. web.xml中配置监听器

   `<listener><listener-class>listener.ContextLoaderListener</listener-class></listener>`

3. 使用

1. 导入spring-web坐标

2. web.xml中配置监听器

   `<listener><listener-class>org.springframework.web.context.ContextLoaderListener</listener-class></listener>`

3. 为Spring的ContextLoaderListener在web.xml中配置contextConfigLocation的位置(不配置则默认加载application.xml)

4. 调用(`ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());`)

## SpringMVC

model:数据封装/业务逻辑处理

view:数据展示

controller:分发/指派

SpringMVC 通过一套注解,将简单的Java类变成处理请求的控制器,同时支持RESTful编程风格的请求

- 步骤

  1. 导入SpringMVC包(pom.xml:44)

  2. 配置SpringMVC核心控制器DispathcerServlet(共有行为)(WEB-INF/web.xml:12)

     ```java
     <!--配置SpringMVC前端控制器-->
         <servlet>
             <servlet-name>DispatcherServlet</servlet-name>
             <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
             <init-param>
                 <param-name>contextConfigLocation</param-name>
                 <param-value>classpath:spring-mvc.xml</param-value>
             </init-param>
             <load-on-startup>1</load-on-startup>
         </servlet>
         <servlet-mapping>
             <servlet-name>DispatcherServlet</servlet-name>
             <url-pattern>/controller/*</url-pattern>
             <!--        <url-pattern>/</url-pattern>-->
         </servlet-mapping>
     
     
         <!--
             notice 如果拦截器的url-pattern是"/",那么访问xxx.html也会被拦截,需要设置.html文件为的拦截器为default
                 - 如果url-pattern设为"/xxx/*",那么html不会被拦截,同时浏览器访问路径由"根目录/路径"变为"根目录/xxx/路径",
                     - 例如"http://localhost/spring_mvc_war/user/modelAndView"
                     	变为"http://localhost/spring_mvc_war/controller/user/modelAndView"
                 <servlet-mapping>
                     <servlet-name>default</servlet-name>
                     <url-pattern>*.html</url-pattern>
                 </servlet-mapping>
         -->
     ```
  
  3. 创建Controller类和视图页面(POJO类(独有行为))
  
  4. 使用注解配置Controller类中业务方法的映射地址(@Controller)
  
  5. 使DispathcerServlet加载spring-mvc.xml (配置组件扫描)(WEB-INF/web.xml:16)
  
     ```xml
             <init-param>
                 <param-name>contextConfigLocation</param-name>
                 <param-value>classpath:spring-mvc.xml</param-value>
             </init-param>
     ```
  
  6. 客户端请求测试

## SpringMVC的注解和配置

- 注解:
  - @RequestMapping 写在类名上,和方法名的映射构成多级路径
    - value 默认,指定请求的URL,和`path`属性作用相同
    - method:指定请求方式
    - params:指定限制请求参数的条件
      - `params = {"accountName","money!100"}`:请求参数必须有accountName, money不等于100
- 配置
  - 视图解析器配置
    - 略


## 数据响应

> src/main/java/controller/UserController.java

### 页面跳转

#### 返回字符串

将返回的字符串 和 视图解析器的前后缀拼接后跳转

- **跳转HTML的请求会被前端控制器拦截**,在web.xml文件中增加配置来释放.html请求(css/js/jpg可能也需要?)

  ```xml
      <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.html</url-pattern>
       </servlet-mapping>
  ```

#### 返回ModelAndView对象

略

### 回写数据

#### 返回字符串

- 通过@ResponseBody注解

  ```java
      @RequestMapping("/string")
      @ResponseBody
      public String save3() {
  ```

#### 返回对象或集合

1. @ResponseBody注解

2. 导包

   ```xml
           <dependency>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-core</artifactId>
               <version>2.13.1</version>
           </dependency>
           <dependency>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-annotations</artifactId>
               <version>2.13.1</version>
           </dependency>
           <dependency>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-databind</artifactId>
               <version>2.13.1</version>
           </dependency>
   ```

3. ```
   <!--配置处理器映射器,自动转换为JSON  https://www.bilibili.com/video/BV1WZ4y1P7Bp?p=58 	3分17秒开始看-->
   <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
       <property name="messageConverters">
           <list>
               <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
           </list>
       </property>
   </bean>
   ```

4. 直接return对象

>第三步 可用mvc的注解驱动 代替
>
>`<mvc:annotation-driven/>`

## 获得请求参数

### 参数类型

> src/main/java/dao/impl/UserDaoImpl.java

- 基本类型

  - Controller业务中的**参数名** 与 请求参数中的key**一致**,参数值会**自动映射匹配**

    ```java
        @RequestMapping("/paramsBasicType")
        @ResponseBody
        /*
            notice 接收参数最好用"引用类型",如果参数不存在,接收为null
                如果不用Integer用int,缺少参数时会报错:
                    Optional int parameter 'age' is present but cannot be translated into a null value due to being declared as a primitive type.
         */
        public String receive1(String username, Integer age) {
            if (username != null || age != null) {
                System.out.println(username + " " + age);
                return username + " " + age;
            }
            return "I do not receive anything.";
        }
    ```

  - 如果要接收**multipart/form-data**类型的数据

    - 要在maven中导入`<groupId>commons-fileupload</groupId><artifactId>commons-fileupload</artifactId>`

    - 在spring配置文件中配置

      ```xml
          <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
              <property name="defaultEncoding" value="UTF-8"/>
          </bean>
      ```

- POJO类型

  - Controller业务方法中的POJO参数的属性名与请求参数的key一致

    ```java
        @RequestMapping("/pojo")
        @ResponseBody
        public void receivePOJO(UserDaoImpl userDao) {
            System.out.println(userDao);
        }
    ```

  - http://localhost/spring_mvc_war/controller/user/pojo?username=zhaoxin

- 数组

  - Controller业务方法中数组名称 与 请求参数中的key一致,自动映射匹配

- 集合

  - 方法一: 将集合封装到POJO对象中

  - 方法二: 用**ajax**提交时,可以**指定ContentType为JSON格式**,在**方法参数用@RequestBody**可以**直接接收集合**数据

    ```java
        // 前端发送JSON:[{"username": "Google"},{"username": "Baidu"},{"username": "SoSo"}]
        @RequestMapping("/set")
        @ResponseBody
        public void receiveSet(@RequestBody List<UserDaoImpl> userDaoList) {
            System.out.println(userDaoList);
        }
    ```

### 静态资源访问开启

```java
    <mvc:annotation-driven/>    
	<!--
        notice 这两种方法,都需要提前添加<mvc:annotation-driven/>,否则会导致Controller类下的RequestMapping下的方法失效
    -->
    <!--方法一: 开放资源的访问-->
    <!--    <mvc:resources mapping="/js/**" location="/js/"/>-->
    <!--方法二: 找不到就交给原始路由器-->
    <mvc:default-servlet-handler/>
```

### 中文乱码

- request乱码

    - (**application/x-www-form-urlencoded**是URI编码形式的**键值对**,如果UTF-8解码,会乱码. **multipart/form-data**是字节流的**多部分形式**,所以不会乱码)

    > src/main/webapp/WEB-INF/web.xml
    
    ```xml
        <!--配置全局过滤器-->
        <filter>
            <filter-name>CharacterEncodingFilter</filter-name>
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>CharacterEncodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    ```
    
    - 但**CharacterEncodingFilter不能解决所有乱码!!!**(也可以说是SpringMVC的一个bug)
      - SpringMVC有一系列HttpMessageConverter去处理用@ResponseBody注解的返回值
        - 如返回VM(View Model)则使用MappingJacksonHttpMessageConverter
        - 若返回String，则使用StringHttpMessageConverter，**这个Converter使用的是字符集是iso-8859-1,而且是final的**
          - 既然是String有问题，那自然就直接从适配器AnnotationMethodHandlerAdapter 的字符串解析器 StringHttpMessageConverter 入手


- response乱码

    > src/main/resources/spring-mvc.xml

    ```xml
        <mvc:annotation-driven>
            <mvc:message-converters>
                <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                    <constructor-arg value="UTF-8"/>
                </bean>
            </mvc:message-converters>
        </mvc:annotation-driven>
    ```


### 参数绑定

- 当请求的参数名称和Controller业务方法的参数名称不一致时,用@RequestParam对应绑定

```java
    @RequestMapping(value = "/paramsBinding")
    @ResponseBody
    /*notice required默认是true,提交时没有此参数则报错 */
    public String receive2(@RequestParam(value = "name", required = false, defaultValue = "默认值") String username, Integer age) {
        if (username != null || age != null) {
            System.out.println(username + " " + age);
            return username + " " + age;
        }
        return "没有收到任何参数";
    }
```

### 获得Restful风格的参数

- Restful风格: `url+请求方式` 表示一次请求的目的
  - 请求方式:
    - GET 用于获取资源 `/user/1 GET` 得到id=1的user
    - POST 新建资源`/user/1` 新增id=1的user
    - PUT 更新资源`/user/1 PUT` 更新id=1的user
    - DELETE 删除资源`/user/1 DELETE` 删除id=1的user

### 自定义类型转换器

自定义类型转换器步骤

1. 定义转换器类实现Converter接口

   ```java
   public class DateConverter implements Converter<String, Date> {
   
       @Override
       public Date convert(String source) {
           //将日期字符串转换为日期对象,并返回
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           Date date = null;
           try {
               date = format.parse(source);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           return date;
       }
   }
   ```

2. 在配置文件中声明转换器

   ```xml
       <!-- 声明转换器-->
       <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
           <property name="converters">
               <list>
                   <bean class="converter.DateConverter"/>
               </list>
           </property>
       </bean>
   ```

3. 在<annotation-driven>中引用转换器

   `<mvc:annotation-driven conversion-service="conversionService">`

### 获得Servlet相关API

支持使用原始的ServletAPI作为Controller方法的参数注入,常用对象:

- HttpServletRequest
- HttpServletResponse
- HttpSession

```java
public String servletAPI(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession) {
    return httpServletRequest.getRequestURI();
}
```

### 获得请求头

- 使用@RequestHeader, 注解属性:

  - value:请求头的名称

  - required:是否必须携带此请求头

- 使用@CookieValue

```java
public String headerTest
    (
    @RequestHeader(value = "User-Agent", required = false) String userAgent,
    @CookieValue(value = "JSESSIONID") String cookie
	) 
{
    return userAgent + " " + cookie;
}
```

### 文件上传

上传文件方式

1. 表单项`type=file`
2. 表单提交方式是`post`
3. 表单的enctype属性是多部分表单形式(multipart/form-data)

#### 单文件上传

1. 导入FileUpload坐标和io坐标

   ```xml
   <dependency>
       <groupId>commons-fileupload</groupId>
       <artifactId>commons-fileupload</artifactId>
       <version>1.4</version>
   </dependency>
   <dependency>
       <groupId>commons-io</groupId>
       <artifactId>commons-io</artifactId>
       <version>2.11.0</version>
   </dependency>
   ```

2. spring-mvc.xml中配置文件上传解析器

   ```xml
   <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
       <!--上传文件的编码类型-->
       <property name="defaultEncoding" value="UTF-8"/>
       <!--上传文件总大小-->
       <property name="maxUploadSize" value="5242800"/>
       <!--上传文件单个大小-->
       <property name="maxUploadSizePerFile" value="5000"/>
   </bean>
   ```

3. 编写文件上传代码

   ```java
   /*上传文件*/
   @RequestMapping("uploadFile")
   @ResponseBody
   public String uploadFile(MultipartFile skin) throws IOException {
       //获得文件名
       String originalFilename = skin.getOriginalFilename();
       skin.transferTo(new File("D:\\OneDrive\\桌面\\" + originalFilename));
       return originalFilename + " has been saved";
   }
   ```

#### 多文件上传

```java
@RequestMapping("uploadFile")
@ResponseBody
public String uploadFile(MultipartFile[] skins) throws IOException {
    for(MultipartFile skin:skins){
        //获得文件名
        String originalFilename = skin.getOriginalFilename();
        skin.transferTo(new File("D:\\OneDrive\\桌面\\" + originalFilename));
    }
    return "saved";
}
```

## Spring JdbcTemplate

> 模块:spring_jdbc

### 基本使用

开发步骤

1. 导入`spring-jdbc`和`spring-tx`坐标
2. 创建数据库表和实体
3. 创建JdbcTemplate对象
4. 执行数据库操作

### 常用操作

> src/test/java/jdbc/CRUDTest.java

- JdbcTemplate.update : 增删改

- 查询

  - 查询多个对象

    ```java
    public void testQuarryAll() {
        /* new BeanPropertyRowMapper<User>(User.class): 初始化一个实现类BeanPropertyRowMapper对象,<泛型>(泛型的字节码对象)*/
        List<User> userList = jdbcTemplate.query("select * from test.test_table", new BeanPropertyRowMapper<User>(User.class));
        System.out.println(userList);
    }
    ```

  - 查询单个对象

    ```java
    User user = jdbcTemplate.queryForObject(
                    "select * from test.test_table where id=?", new BeanPropertyRowMapper<User>(User.class), 100);
    ```

  - 查询数量

    ```java
    Integer count = jdbcTemplate.queryForObject(
                    "select count(*) from test.test_table where id=?", Integer.class, 9);
    ```

## SpringMVC拦截器

步骤:

1. 创建拦截器类实现HandlerInterceptor接口
2. 配置拦截器
3. 测试效果

> src/main/java/interceptor/MyInterceptor.java
>
> spring-mvc.xml:60

多个拦截器的执行顺序 取决于在spring-mvc.xml中的先后顺序

## 异常处理机制

系统的`Dao` `Service` `Controller`层都**通过throws Exception向上抛出**,最后由SpringMVC前端控制器交给**异常处理器**进行异常处理.

- 处理方式一: 使用SpringMVC提供的简单异常处理器`SimpleMappingExceptionResolver`

  - 在spring-mvc.xml中进行异常和视图的映射设置

    ```xml
    <!-- 异常和视图的映射-->
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <!--默认的异常视图-->
        <property name="defaultErrorView" value="error.html"/>
        <!-- 映射-->
        <property name="exceptionMappings">
            <map>
                <entry key="java.lang.ClassNotFoundException" value="error2.html"/>
            </map>
        </property>
    </bean>
    ```

- 处理方式二: 实现Spring的异常处理接口`HandlerExceptionResolver`

  1. 创建实现类(src/main/java/resolver/MyExceptionResolver.java)

     ```java
     public class MyExceptionResolver implements HandlerExceptionResolver {
         @Override
         /*
             notice
              - 参数Exception: 异常对象
              - 返回值ModelAndView:跳转到错误视图信息
          */
         public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
             ModelAndView modelAndView = new ModelAndView();
             
     		//instanceof is a binary operator used to test if an object is of a given type.
             if (ex instanceof MyException) {
                 modelAndView.addObject("info", "自定义异常");
             } else if (ex instanceof ClassNotFoundException) {
                 modelAndView.addObject("info", "ClassNotFound异常");
             }
             modelAndView.setViewName("error.html");
     
             return null;
         }
     }
     ```

  2. 配置`<bean class="resolver.MyExceptionResolver"/>`

# Spring_AOP

AOP: Aspect Oriented Programming 面向切面编程, 通过预编译和运行期动态代理 实现程序功能统一维护 的技术

AOP是OOP(面向对象编程)的延续, 利用AOP对业务逻辑的各个部分进行隔离, 降低耦合度, 减少重复代码

## 动态代理

- 底层实现

​		AOP底层通过Spring提供的**动态代理技术**实现,

​		运行期间,Spring通过动态代理技术 动态地生成代理对象

​		代理对象的方法执行时,进行增强功能的介入,再去调用目标对象的方法

- 常用的动态代理技术
  - JDK代理:基于**接口**
  - cglib代理: 基于父类

### JDK动态代理

```java
public class ProxyTest {
    public static void main(String[] args) {
        //目标对象
        final UserServiceImpl userService = new UserServiceImpl();

        /*
            notice
                public static Object newProxyInstance(ClassLoader loader,  Class< ?>[] interfaces, InvocationHandler h)
                    参数一：类加载器，负责加载代理类到内存中使用。
                    参数二：获取被代理对象实现的全部接口。代理要为全部接口的全部方法进行代理
                    参数三：代理的核心处理逻辑
                    返回值: 动态生成的代理对象
         */
        UserService _userService = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     *
                     * @param proxy 代理对象本身。一般不管
                     * @param method 正在被代理的方法
                     * @param args 被代理方法，应该传入的参数
                     * @return 把业务功能方法执行的结果返回给调用者
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //执行前置的增强功能
                        long startTimer = System.currentTimeMillis();
                        // 执行原本的业务功能
                        Object result = method.invoke(userService, args);
                        //执行后置的增强功能
                        long endTimer = System.currentTimeMillis();
                        System.out.println(method.getName() + "方法耗时：" + (endTimer - startTimer) / 1000.0 + "s");

                        return result;
                    }
                }
        );

        //调用 代理对象 的方法
        _userService.save();
    }
}
```

### cglib代理

1. 导包(已经被Spring集成,所以只需要有spring-context就行)

2. ```java
   public class ProxyTest {
       public static void main(String[] args) {
           //目标对象
           final UserController userController = new UserController();
   
           // 基于cglib动态生成代理对象:
           // 1. 创建增强器
           Enhancer enhancer = new Enhancer();
           // 2. 设置父类(目标)
           enhancer.setSuperclass(UserController.class);
           // 3. 设置回调
           enhancer.setCallback(new MethodInterceptor() {
               @Override
               public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                   //执行前置的增强功能
                   long startTimer = System.currentTimeMillis();
                   // 执行原本的业务功能
                   Object result = method.invoke(userController, objects);
                   //执行后置的增强功能
                   long endTimer = System.currentTimeMillis();
                   System.out.println(method.getName() + "方法耗时：" + (endTimer - startTimer) / 1000.0 + "s");
   
                   return result;
               }
           });
           // 4. 创建代理对象
           UserController userControllerNew = (UserController) enhancer.create();
   
           userControllerNew.save();
       }
   }
   ```

## Spring AOP的相关概念

SPring的AOP底层是对动态代理代码的封装

- 相关术语:
  - Target(目标对象): 代理的目标对象
  - Proxy(代理): 一个类被AOP织入增强后,产生的结果代理类
  - Joinpoint(连接点): 连接点指那些被拦截到的点. 在Spring中,"点"指的是"方法", 因为Spring只支持方法类型的连接点
  - PointCut(切入点/切点): 对哪些Joinpoint进行拦截的定义.  (Joinpoint:公民, PonitCut:人大代表)
  - Advice(通知/增强): 拦截到Joinpoint后要做的事情(增强的功能)
  - Aspect(切面): 切点+通知
  - Weaving(织入): 把Advice织入到Target来创建新的Proxy的过程. Spring采用动态代理织入, AspectJ采用编译期织入和类装载期织入
- 明确事项
  - 要编写的内容
    - 核心业务代码 (目标类的目标方法,即内部有切点)
    - 编写切面类, 切面类中有Advice
    - 在配置文件中,配置Weaving关系,将Advice和Joinpoint结合
  - AOP技术实现的内容
    - Spring框架监控PointCut(切入点方法)的执行
    - 监控到PointCut执行, 使用代理机制动态创建Target的Proxy
    - 根据Advice的类别, 在Proxy的相应位置将Advice对应的功能织入
  - AOP底层使用的代理方式
    - Spring框架根据Target是否实现了接口来决定使用JDK还是cglib

## 基于XML的AOP开发

1. 导包`spring-context` `aspectjweaver`

2. 编写切面类

   ```java
   public class MyAspect {
   
       public void before() {
           System.out.println("前置增强");
       }
   }
   ```

3. 配置织入

   ```xml
       <!-- 目标对象-->
       <bean id="target" class="service.impl.UserServiceImpl"/>
       <!-- 切面对象-->
       <bean id="myAspect" class="aop.MyAspect"/>
       <!-- 配置织入: PointCut和Advice匹配-->
       <aop:config>
           <!-- 声明切面-->
           <aop:aspect ref="myAspect">
               <!--
                   notice 切面配置 Aspect:Advice+PointCut
                       切点表达式语法: execution([修饰符] 返回值类型 包名.类名.方法名(参数))
                           - 修饰符可以不写
                           - 返回值类型/包名/类名/方法名 都可以用*表示任意
                           - 包名和类名之间一个.表示当前包下的类,两个..表示当前包及其子包下的类
                           - 参数列表用..表示任意个数/任意类型的参数
                       Advice配置:
                           - <aop:before>  前置通知
                           - <aop:after-returning>  后置通知
                           - <aop:around>  环绕通知
                           - <aop:throwing>  异常抛出通知
                           - <aop:after>  最终通知,无论Advice是否执行,是否抛出异常,都会执行
               -->
               <aop:before method="before" pointcut="execution(public void service.impl.UserServiceImpl.save())"/>
   
               <!-- 抽取切点表达式-->
               <aop:pointcut id="myPointCut" expression="execution(* service..*.*(..))"/>
               <aop:before method="before" pointcut-ref="myPointCut"/>
               <aop:before method="before" pointcut-ref="myPointCut"/>
           </aop:aspect>
       </aop:config>
   ```

4. 执行

   ```java
   @RunWith(SpringJUnit4ClassRunner.class)
   @ContextConfiguration("classpath:application.xml")
   public class AopTest {
   
       @Autowired
       private UserService userService;
   
       @Test
       public void test() {
           userService.save();
       }
   }
   ```

## 基于注解的AOP开发

- 切面类中使用注解配置织入关系(标注Aspect类和Advice方法)

  ```java
  @Component("myAspectAnno")
  @Aspect //标注为切面类
  public class MyAspect {
  
      //注意 value的值是PointCut
      @Before(value = "execution(* controller..*.*(..))")
      public void before() {
          System.out.println("注解方式的前置增强");
      }
          
      //切点表达式的抽取
      @Pointcut("execution(* controller..*.*(..))")
      public void pointCut1(){}
      @Before("pointCut1()")
      public void before2() {
          System.out.println("注解方式的前置增强");
      }
      @Before("MyAspect.pointCut1()")
      public void before3() {
          System.out.println("注解方式的前置增强");
      }
  }
  ```

- 配置文件中开启组件扫描和AOP自动代理

  ```xml
      <mvc:component-scan base-package="aop.anno"/>
      <mvc:component-scan base-package="controller"/>
  
      <!-- notice 基于注解的AOP: 要打开aop自动代理-->
      <aop:aspectj-autoproxy/>
  ```

- 执行

# Spring的事务控制

## 编程式事务控制

三大对象:

- PlatFormTransactionManager 事务的接口(用它的实现类对象)
  - `PlatFormTransactionManager`是**接口**类型,**不同的Dao层有不同的实现类**(Dao层是jdbc或mybatis时,或Dao层是hibernate时,实现类不同)
- TransactionDefinition 事务的定义对象
  - 事务隔离级别(不懂)
  - 事务传播行为(没记住)
- TransactionStatus 事务的具体运行状态对象

## 基于XML的声明式事务控制

> 声明式事务管理,属于系统层面的服务,而不是业务逻辑的一部分. 如果要改变事务管理策划,只需要在定义文件中重新配置,无需改变代码
>
> Spring声明式事务控制底层就是AOP

明确事项:

- PointCut: 转账行为
- Advice:事务管理
- Aspect

>src/main/resources/application.xml

```xml
    <!-- 配置PlatFromTransactionManager-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager"
          p:dataSource-ref="dataSource"/>

    <!-- 配置事务的Advice, 如果PlatFromTransactionManager的id是transactionManager,transaction-manager可以省略不写(因为默认了)-->
    <tx:advice id="transactionControl" transaction-manager="transactionManager">
        <!-- 配置事务的属性信息TransactionDefinition -->
        <tx:attributes>
            <!-- 切点方法的事务参数配置-->
            <tx:method name="transfer" isolation="DEFAULT" propagation="REQUIRED" timeout="-1"
                       read-only="false"/>
            <tx:method name="findAll" isolation="REPEATABLE_READ" read-only="true"/>
            <tx:method name="update*"/> <!--update开头的方法-->
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>

    <!-- 配置事务的AOP织入-->
    <aop:config>
        <!-- 事务增强专用标签:aop:advice代替通用的aop:aspect-->
        <aop:advisor advice-ref="transactionControl" pointcut="execution(* service..*.*(..))"/>
    </aop:config>
```

## 基于注解的声明式事务控制

- 配置文件:

  ```xml
  <!-- 配置PlatFromTransactionManager-->
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager"
        p:dataSource-ref="dataSource"/>
  
  <!-- notice 事务的注解驱动-->
  <tx:annotation-driven transaction-manager="transactionManager"/>
  ```

- 切点

  ```java
  @Service("accountService")
  @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)    //配置当前类所有方法的事务控制参数
  public class AccountServiceImpl implements AccountService {
  
      private final AccountDao accountDao;
  
      public AccountServiceImpl(AccountDao accountDao) {
          this.accountDao = accountDao;
      }
  
      @Override
      @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)    //配置当前方法的事务控制参数,优先级更高
      public void transfer(int payer, int payee, double money) {
          accountDao.out(payer, money);
          int error = 1 / 0;
          accountDao.in(payee, money);
      }
  }
  ```

# MyBatis

- 如果 是**基本类型，或者是java自身的引用类型**，在MyBatis运行时，会自动的进行匹配，可以省略**parameterType**属性
- 如果是你**自己声明的一个类型**，**因为可能在引入的jar包中有同名的类**，所以你需要制定，这个时候的类型是什么,就要写parameterType

```xml
<!--useGeneratedKeys返回主键 keyProperty声明主键 parameterType参数类型-->
<insert id="add" useGeneratedKeys="true" keyProperty="id" parameterType="User">
    insert into test.test_table(id, money, password)
    VALUES (#{id}, #{money}, #{password});
</insert>
```



- 配置文件中, 用加载jdbc.properties的方式加载数据库信息( [Mybatis外部引用Properties文件代码不显示高亮问题](https://blog.csdn.net/Harrie_Even/article/details/107900613))
- 可以用typeAlias标签, 为parameterType和ResultType类 设置别名

- **mybatis必须配置driver**,否则会报错key is null

## 配置文件

### TypeHandlers标签

通过重写或创建类型处理器,来处理非标准或不支持的类型

- 具体做法: 实现TypeHandler接口, 或继承BaseTypeHandler类, 然后选择性得将其映射到JDBC类型

- 步骤:

  1. 定义转换类

  2. 覆盖四个未实现的方法,

     - setNonNullParameter: Java程序设置数据到数据库的回调方法
     - getNullableResult: 查询时MySQL的字符串类型转换为Java的Type类型的方法

     ```java
     public class MoneyTypeHandler extends BaseTypeHandler<Double> {
     
         /**
          * 将Java类型转为数据库需要的类型
          * @param i: 自定义Type的位置
          * @param o: 传入的需要被转换的Java类型
          * @param jdbcType
          */
         @Override
         public void setNonNullParameter(PreparedStatement preparedStatement, int i, Double o, JdbcType jdbcType) throws SQLException {
             //将Double o转换为自定义Type, 本实例将其转换为美元,汇率6.36,java对象是Double,自定义Type是Float
             float dollar= (float) (o/6.36);
             preparedStatement.setFloat(i,dollar);
         }
     
         /**
          * 数据库中的类型转换为Java对象
          * @param resultSet 查询结果集
          * @param s 要转换的字段的名称
          * @return 返回Java对象
          */
         @Override
         public Double getNullableResult(ResultSet resultSet, String s) throws SQLException {
             float dollar = resultSet.getFloat(s);
             return (double) dollar*6.36;
         }
     
         /**
          * 数据库中的类型转换为Java对象
          * @param resultSet 结果集
          * @param i 要转换字段在结果集中的位置i
          * @return Java对象
          */
         @Override
         public Double getNullableResult(ResultSet resultSet, int i) throws SQLException {
             float dollar = resultSet.getFloat(i);
             return (double) dollar*6.36;
         }
     
         /**
          * 数据库中的类型转换为Java对象
          * @return Java对象
          */
         @Override
         public Double getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
             float dollar = callableStatement.getFloat(i);
             return (double) dollar*6.36;
         }
     }
     ```

  3. 在MyBatis核心配置文件中注册

     ```xml
         <!--注册类型处理器-->
         <typeHandlers>
             <typeHandler handler="handler.MoneyTypeHandler"/>
         </typeHandlers>
     ```

  4. 测试转换

### plugins标签

- 分页助手: sql语句不要加分号,不然会SQL语句错误

- 开发步骤:

  1. 导入PageHelper坐标

     ```
     <!--分页助手-->
     <dependency>
         <groupId>com.GitHub.pagehelper</groupId>
         <artifactId>pagehelper</artifactId>
         <version>5.3.0</version>
     </dependency>
     <dependency>
         <groupId>com.GitHub.jsqlparser</groupId>
         <artifactId>jsqlparser</artifactId>
         <version>4.2</version>
     </dependency>
     ```

  2. 在MyBatis核心配置文件中配置PageHelper插件

     ```xml
         <plugins>
             <!--分页助手插件-->
             <plugin interceptor="com.github.pagehelper.PageInterceptor"/>
         </plugins>
     ```

  3. 测试分页数据获取

## MyBatis多表查询

### 一对一查询

- resultMap

```xml
    <resultMap id="order" type="domain.Order">
        <id column="oid" property="oid"/>
<!--
        可以用association代替
        <result column="id" property="user.id"/>
        <result column="money" property="user.money"/>
        <result column="password" property="user.password"/>
-->
        <result column="things" property="things"/>
        <!--association要放在result后面
            property: 当前实体(order)中的属性名称(private User user)
            javaType: 当前实体(order)中的属性的类型(User)
        -->
        <association property="user" javaType="domain.User">
            <result column="id" property="id"/>
            <result column="money" property="money"/>
            <result column="password" property="password"/>
        </association>
    </resultMap>

    <select id="findAll" resultMap="order">
        select * from test.test_table user,test.`order` `order` where user.id=`order`.uid
    </select>
```

### 一对多/多对多

- resultMap+collection

```xml
<typeAliases>
    <typeAlias type="domain.User" alias="userWithOrders"/>
       <!-- package: 包下所有类都自动起别名,User类自定起别名为User和user-->
        <package name="domain"/>
</typeAliases>
```

```xml
    <resultMap id="user" type="userWithOrders">
        <id column="id" property="id"/>
        <result column="money" property="money"/>
        <result column="password" property="password"/>
        <collection property="orderList" ofType="domain.Order">
            <id column="oid" property="oid"/>
            <result column="things" property="things"/>
        </collection>
    </resultMap>
```

## 注解开发

```java
public interface orderMapper {

    @Select("select * from test.test_table user,test.`order` `order` where user.id=`order`.uid")
    @Results({
            @Result(id = true, column = "oid", property = "oid"),
            @Result(column = "things", property = "things"),
            @Result(column = "uid", property = "user.id"),
            @Result(column = "money", property = "user.money"),
            @Result(column = "password", property = "user.password")
    })
    List<Order> AnnoFindAll();

    @Select("select * from test.`order`")
    @Results({
            @Result(id = true, column = "oid", property = "oid"),
            @Result(column = "things", property = "things"),
            @Result(
                    property = "user",    //要封装的Order中的属性名称
                    column = "uid",  //根据order表中哪个字段查询test_table表中的数据
                    javaType = domain.User.class,   //要封装的实体类型
                    //select属性,代表查询哪个接口的方法获得数据
                    one = @One(select = "mapper.test_tableMapper.findById")
            )
    })
    List<Order> AnnoFindAll2();

    @Select("select * from test.`order` where uid=#{uid}")
    @Results({
            @Result(id = true, column = "oid", property = "oid"),
            @Result(column = "things", property = "things"),
            @Result(
                    property = "user",    //要封装的Order中的属性名称
                    column = "uid",  //根据order表中哪个字段查询test_table表中的数据
                    javaType = domain.User.class,   //要封装的实体类型
                    //select属性,代表查询哪个接口的方法获得数据
                    one = @One(select = "mapper.test_tableMapper.findById")
            )
    })
    List<Order> findByUid(int uid);
}
```

```java
public interface test_tableMapper {

    @Select("select * from test.test_table;")
    @Results({
            /* id=true:主键*/
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "password",property = "password"),
            @Result(
                    property = "orderList",
                    column = "id",
                    /*notice 返回的是List类型*/
                    javaType = List.class,
                    many=@Many(select = "mapper.orderMapper.findByUid")
            )
    })
    List<User> AnnoFindAll();

    @Select("select * from test.test_table where id=#{id}")
    User findById(int id);
}
```

```java
public class MybatisTest {

    private static SqlSession sqlSession;
    private static test_tableMapper userMapper;
    private static orderMapper orderMapper;

    @BeforeAll
    static void before() {
        try {
            System.out.println("开始初始化数据库连接池");
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            userMapper = sqlSession.getMapper(test_tableMapper.class);
            orderMapper = sqlSession.getMapper(orderMapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws Exception {
        List<Order> orders = orderMapper.AnnoFindAll2();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void test2() {
        List<User> users = userMapper.AnnoFindAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test3() {
        List<Order> orders = orderMapper.findByUid(1);
        System.out.println(orders);
    }

    @AfterAll
    static void after() {
        sqlSession.close();
        System.out.println("closed sqlSession!");
    }

}
```
