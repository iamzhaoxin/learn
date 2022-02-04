package config;

import org.springframework.context.annotation.*;

/**
 * @Author: 赵鑫
 * @Date: 2022/2/4 17:23
 */

@Configuration  //标志该类是Spring的核心配置类
@ComponentScan({"dao", "service"})   //配置组件扫描
@Import(DataSourceConfiguration.class)  //导入其他配置类
public class SpringConfiguration {

}
