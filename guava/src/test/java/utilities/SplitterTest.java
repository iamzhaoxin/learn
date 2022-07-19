package utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 字符串分割
 *
 * @Author: 赵鑫
 * @Date: 2022/7/1
 */
public class SplitterTest {

    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("Hello|World");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("Hello"));
        assertThat(result.get(1), equalTo("World"));
    }

    /**
     * 忽略空值的拆分
     * .omitEmptyStrings()
     */
    @Test
    public void testSplit_On_Split_OmitEmpty() {
        List<String> result = Splitter.on("|").splitToList("Hello|World|||");
        assertThat(result.size(), equalTo(5));
        result = Splitter.on("|").omitEmptyStrings().splitToList("Hello|World|||");
        assertThat(result.size(), equalTo(2));
    }

    /**
     * 去除拆分结果的首位空格
     * .trimResults()
     */
    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult() {
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList(" Hello  |World | ||  ");
        assertThat(result.get(0), equalTo(" Hello  "));
        result = Splitter.on("|").omitEmptyStrings().trimResults().splitToList(" Hello  |World | ||  ");
        assertThat(result.get(0), equalTo("Hello"));
    }

    /**
     * 按照固定长度拆分
     * .fixedLength();
     */
    @Test
    public void testSplit_FixLength() {
        List<String> result = Splitter.fixedLength(4).splitToList("aaddffgghhjkk");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(4));
        assertThat(result.get(0), equalTo("aadd"));
        assertThat(result.get(1), equalTo("ffgg"));
        assertThat(result.get(2), equalTo("hhjk"));
        assertThat(result.get(3), equalTo("k"));
    }

    /**
     * 限制最大拆分数量
     * .limit()
     */
    @Test
    public void testSplitOnSplit_Limit() {
        List<String> result = Splitter.on("|").limit(3).splitToList("Hello|World|aaa|bbb|ccc");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0), equalTo("Hello"));
        assertThat(result.get(1), equalTo("World"));
        assertThat(result.get(2), equalTo("aaa|bbb|ccc"));
    }

    /**
     * 匹配正则表达式字符串进行分割
     * .onPattern()
     */
    @Test
    public void testSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").omitEmptyStrings().trimResults()
                .splitToList(" Hello  |World | ||  ");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("Hello"));
        assertThat(result.get(1), equalTo("World"));
    }

    /**
     * 分割成Map
     * .withKeyValueSeparator().split()
     */
    @Test
    public void testSplitOnSplitToMap() {
        Map<String, String> result = Splitter.on(Pattern.compile("\\|")).omitEmptyStrings().trimResults()
                .withKeyValueSeparator("=").split(" Hello=Java  |World=world | ||  ");
        assertThat(result, notNullValue());
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("Hello"), equalTo("Java"));
        assertThat(result.get("World"), equalTo("world"));
    }

    /**
     * MapSplitter 对键值对格式有着严格的校验
     * 如果希望使用 MapSplitter 来拆分 KV 结构的字符串，需要保证键-值分隔符和键值对之间的分隔符不会称为键或值的一部分
     * 所以一般推荐使用 JSON 而不是 MapJoiner + MapSplitter。
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalMapSplitterTest(){
        Map<String, String> split = Splitter.on("#").withKeyValueSeparator(":").split("1:2#3:4:5");
        split.forEach(System.out::printf);
    }
}
