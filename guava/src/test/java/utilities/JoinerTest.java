package utilities;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author: 赵鑫
 * @Date: 2022/6/26 21:54
 */
public class JoinerTest {

    private final List<String> stringList= Arrays.asList(
            "Google","Guava","Java","Scala","Kafka"
    );

    private final List<String> stringListWithNullValue= Arrays.asList(
            "Google","Guava","Java","Scala",null
    );

    @Test
    public void testJoinOnJoin(){
        String result = Joiner.on(",").join(stringList);
        assertThat(result,equalTo("Google,Guava,Java,Scala,Kafka"));
    }
}
