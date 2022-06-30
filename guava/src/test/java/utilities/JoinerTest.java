package utilities;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * @Author:  赵鑫
 * @Date: 2022/6/26 21:54
 */
public class JoinerTest {

    private final List<String> stringList = Arrays.asList(
            "Google", "Guava", "Java", "Scala", "Kafka"
    );

    private final List<String> stringListWithNullValue = Arrays.asList(
            "Google", "Guava", "Java", "Scala", null
    );

    private final Map<String, String> stringMap = ImmutableMap.of("Hello", "Guava", "Java", "Scala");

    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on(",").join(stringList);
        assertThat(result, equalTo("Google,Guava,Java,Scala,Kafka"));
    }

    /**
     * 带空值的拼接：得到NullPointerException报错
     */
    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        String result = Joiner.on(",").join(stringListWithNullValue);
        assertThat(result, equalTo("Google,Guava,Java,Scala"));
    }

    /**
     * 使用.skipNulls()忽略null
     */
    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on(",").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("Google,Guava,Java,Scala"));
    }

    /**
     * 使用.useForNull("DEFAULT")替换null
     */
    @Test
    public void testJoin_On_Join_WithNullValue_UseDefaultValue() {
        String result = Joiner.on(",").useForNull("DEFAULT").join(stringListWithNullValue);
        assertThat(result, equalTo("Google,Guava,Java,Scala,DEFAULT"));
    }

    /**
     * 追加到appendable变量
     * All Known Implementing Classes:
     * BufferedWriter, CharArrayWriter, CharBuffer, FileWriter, FilterWriter, LogStream, OutputStreamWriter,
     * PipedWriter, PrintStream, PrintWriter, StringBuffer, StringBuilder, StringWriter, Writer
     */
    @Test
    public void testJoin_On_AppendTo_StringBuilder() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on(",").useForNull("DEFAULT")
                .appendTo(builder, stringListWithNullValue);
/*        StringBuilder resultBuilder = Joiner.on(",").useForNull("DEFAULT")
                .appendTo(new StringBuilder(), stringListWithNullValue);*/
        assertThat(resultBuilder, sameInstance(builder));
        assertThat(resultBuilder.toString(), equalTo("Google,Guava,Java,Scala,DEFAULT"));
        assertThat(builder.toString(), equalTo("Google,Guava,Java,Scala,DEFAULT"));
    }

    @Test
    public void testJoin_On_AppendTo_Writer() {
        String targetFileName = "guava-joiner.txt";
        try (FileWriter writer = new FileWriter(targetFileName)) {
            Joiner.on(",").useForNull("DEFAULT").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fetal error");
        }
    }

    /**
     * 使用stream().filter()
     * 忽略null值
     * isEmpty()用于判断字符串是否为空
     */
    @Test
    public void testJoiningByStream_SkipNullValues() {
        String collect = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty())
                .collect(joining(","));
        assertThat(collect, equalTo("Google,Guava,Java,Scala"));
    }

    /**
     * 使用stream().map()
     * 替换null值
     */
    @Test
    public void testJoiningByStream_WithDefaultValue() {
/*        String collect = stringListWithNullValue.stream().map(item -> item == null || item.isEmpty() ? "DEFAULT" : item)
                .collect(joining(","));*/
        String collect = stringListWithNullValue.stream().map(this::defaultValue).collect(joining(","));
        assertThat(collect, equalTo("Google,Guava,Java,Scala,DEFAULT"));
    }

    private String defaultValue(final String item) {
        return item == null || item.isEmpty() ? "DEFAULT" : item;
    }

    /**
     * Joiner.on().withKeyValueSeparator().join()
     * 拼接键值对
     */
    @Test
    public void testJoinOn_WithMap() {
        assertThat(Joiner.on(",").withKeyValueSeparator("=").join(stringMap), equalTo("Hello=Guava,Java=Scala"));
    }

}
