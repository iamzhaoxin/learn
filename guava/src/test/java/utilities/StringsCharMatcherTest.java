package utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 字符串、字符 处理
 *
 * @Author: 赵鑫
 * @Date: 2022/7/1
 */
public class StringsCharMatcherTest {

    /**
     * 字符串操作
     */
    @Test
    public void testStringsMethod() {
        // 空串转为null
        assertThat(Strings.emptyToNull(""), nullValue());
        assertThat(Strings.emptyToNull("hello"), equalTo("hello"));
        // null转空串
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.nullToEmpty("hello"), equalTo("hello"));
        // 公共前缀、后缀
        assertThat(Strings.commonPrefix("hello","hit"),equalTo("h"));
        assertThat(Strings.commonSuffix("hello","hit"),equalTo(""));
        // 重复字符串
        assertThat(Strings.repeat("hello",3),equalTo("hellohellohello"));
        // 判断空串或null
        assertThat(Strings.isNullOrEmpty(""),equalTo(true));
        assertThat(Strings.isNullOrEmpty(null),equalTo(true));
        // 前后补足字符串长度
        assertThat(Strings.padStart("hello",10,'H'),equalTo("HHHHHhello"));
        assertThat(Strings.padEnd("hello",10,'H'),equalTo("helloHHHHH"));
    }

    /**
     * 指定字符集
     */
    @Test
    public void testCharsets(){
        Charset charset=Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8,equalTo(charset));
    }

    /**
     * 字符、字符串操作
     */
    @Test
    public void testCharMatcher(){
        // 判断是否是数字
        assertThat(CharMatcher.javaDigit().matches('5'),equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'),equalTo(false));
        // java自带方法
        assertThat(Character.isDigit('3'),equalTo(true));

        // 匹配字符在字符串中出现次数
        assertThat(CharMatcher.is('a').countIn("it's a pen."),equalTo(1));
        // 替换连续的空格
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("    hello world",'*'),equalTo("*hello*world"));
        // 匹配并移除
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello wor123ld"),equalTo(
                "helloworld"));
    }

    /**
     * .trimResults()传入CharMatcher参数时，删除所有匹配的字符
     */
    @Test
    public void charMatcher_Spliter(){
        String toSplit="a1aa312,b1";
        List<String> splitToList = Splitter.on(',').trimResults(CharMatcher.anyOf("a1")).splitToList(toSplit);
        splitToList.forEach(System.out::println);
    }
}
