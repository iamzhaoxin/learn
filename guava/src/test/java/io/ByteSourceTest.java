package io;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/2
 */
public class ByteSourceTest {

    private final String path = "src/test/resources/io/宇航员.png";

    @Test
    public void testAsByteSource() throws IOException {
        File file = new File(path);
        // 字节流
        ByteSource byteSource = Files.asByteSource(file);
        // 切片
        ByteSource sliceByteSource = byteSource.slice(0, 10);
        // 读取字节流
        byte[] readBytes = byteSource.read();
        assertThat(readBytes, is(Files.toByteArray(file)));
    }
}
