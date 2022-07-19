package io;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/2
 */
public class ByteSinkTest {

    private final String path = "src/test/resources/io/byteSink.bat";

    /**
     * 写入二进制文件
     * fixme
     * ByteSink和outPutStream的区别？
     * (it is an immutable supplier of OutputStream instances.没看懂)
     */
    @Test
    public void testByteSink() throws IOException {
        File file = new File(path);
        file.deleteOnExit();
        // ByteSink byteSink = Files.asByteSink(file, FileWriteMode.APPEND);
        ByteSink byteSink = Files.asByteSink(file);
        byte[] bytes = {0x01, 0x02};
        byteSink.write(bytes);
        byteSink.write(bytes);

        byte[] expected = Files.toByteArray(file);
        assertThat(expected, is(bytes));
    }

    @Test
    public void testFromSourceToSink() throws IOException {
        String source = "src/test/resources/io/宇航员.png";
        String target = "src/test/resources/io/target.png";
        File sourceFile = new File(source);
        File targetFile = new File(target);
        targetFile.deleteOnExit();

        ByteSource byteSource = Files.asByteSource(sourceFile);
        // 拷贝文件
        byteSource.copyTo(Files.asByteSink(targetFile));

        String sourceHash = Files.asByteSource(sourceFile).hash(Hashing.sha256()).toString();
        String targetHash = Files.asByteSource(targetFile).hash(Hashing.sha256()).toString();
        assertThat(sourceHash, is(targetHash));
    }

}
