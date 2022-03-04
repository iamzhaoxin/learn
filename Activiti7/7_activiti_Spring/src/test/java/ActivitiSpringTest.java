/**
 * @Author: 赵鑫
 * @Date: 2022/3/4 22:45
 */

import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:activiti-spring.xml")
public class ActivitiSpringTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void test(){
        System.out.println(repositoryService);
    }
}
