package io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/2
 */
public class CharSourceTest {

    /**
     * 读取
     */
    @Test
    public void testCharSourceWrap() throws IOException {
        CharSource charSource = Files
                .asCharSource(new File("src/test/resources/io/resource.txt"), Charsets.UTF_8);
        //CharSource charSource= CharSource.wrap("hello world");

        // 读取成一个字符串
        String resultAsRead=charSource.read();
        List<String> readLines=Files.readLines(new File("src/test/resources/io/resource.txt"),Charsets.UTF_8);
        String resultReadLines = Joiner.on("\r\n").join(readLines);
        assertThat(resultAsRead,equalTo(resultReadLines));

        // 读取每行
        ImmutableList<String> list = charSource.readLines();
        assertThat(list,equalTo(readLines));

        // 数据源大小
        assertThat(charSource.length(), equalTo((long) resultReadLines.length()));
        // 不打开数据流就能确定数据源大小时(without actually opening the data stream)
        assertThat(charSource.lengthIfKnown().orNull(), equalTo(null));
    }

    /**
     * 拼接
     * .lines()返回的是stream
     * .readLines()返回集合
     */
    @Test
    public void testConcat() throws IOException {
        CharSource charSource=CharSource.concat(
                CharSource.wrap("hello\n"),
                CharSource.wrap("world")
        );
        // 输出每行
        charSource.lines().forEach(System.out::println);
        // 总长度（回车占1位）
        System.out.println(charSource.length());
        // 行数
        System.out.println(charSource.readLines().size());
        // 行数
        System.out.println(charSource.lines().count());
    }

}
