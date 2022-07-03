package io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/3
 */
public class CloserTest {

    private final String path = "src/test/resources/io/resource.txt";

    /**
     * java8
     * try-with-resources
     * 推荐
     */
    @Test
    public void testTryWithSource() throws Exception {
        try (InputStream input = new FileInputStream(path)) {
            // 单字节读取，读取下一个字节
            int charToByteToInt = input.read();
            assertThat((char) charToByteToInt, is('l'));
            // InputStream的read()方法读取数据时，read是阻塞Blocking的
            charToByteToInt = input.read(); // 必须等待read()方法返回才能执行下一代码
            assertThat((char) charToByteToInt, is('e'));
        }
    }

    /**
     * java8
     * close in finally
     * <p>
     * 当try{}和finally{}中同时发生错误时，最后捕捉到的异常只有finally里的，try代码块里的异常被覆盖
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCloseInFinally() throws Exception {
        InputStream input = new FileInputStream(path);
        try {
            byte[] buffer = new byte[(int) new File(path).length()];
            int length = input.read(buffer);

            List<String> strings = Files.readLines(new File(path), Charsets.UTF_8);
            String expected = Joiner.on("\n").join(strings);

            assertThat(new String(buffer), is(expected));
            throw new RuntimeException("要发现的错误");
        } finally {
            input.close();
            throw new IllegalArgumentException("次生错误");
        }
    }

    @Test(expected = RuntimeException.class)
    public void testMultiException() {
        Throwable t = null;
        try {
            // ....
            throw new RuntimeException("error 1");
        } catch (Exception e) {
            // ....
            t = e;
            throw e;
        } finally {
            try {
                // ....
                throw new IllegalArgumentException("error 2");
            } catch (Exception e) {
                // 不覆盖上个异常
                t.addSuppressed(e);
            }
        }
    }

    /**
     * 用于简化jdk7以前的代码
     */
    @Test(expected = RuntimeException.class)
    public void testCloser() throws IOException {
        ByteSource byteSource = Files.asByteSource(new File(path));
        Closer closer = Closer.create();
        try {
            InputStream inputStream = closer.register(byteSource.openStream());
            throw new RuntimeException("要发现的错误");
        } catch (IOException e) {
            throw closer.rethrow(e);
        } finally {
            closer.close();
        }
    }
}
