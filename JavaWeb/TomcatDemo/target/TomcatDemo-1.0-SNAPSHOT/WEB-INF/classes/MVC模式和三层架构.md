# MVC模式

- M：Model，业务模型
- V：View，视图
- C：Controller，控制器

浏览器请求控制器（Servlet）  
控制器从模型（JavaBean）中得到数据  
控制器将数据发送给视图（JSP）  
视图相应浏览器

# 三层架构

浏览器  
⬆⬇  
表现层web：（Servlet+JSP） 接收请求、封装数据、调用业务逻辑、响应数据  
⬆⬇  
业务逻辑层service：封装业务逻辑  
⬆⬇  
数据访问层dao/mapper：数据库的CRUD操作  
⬆⬇  
数据库  
