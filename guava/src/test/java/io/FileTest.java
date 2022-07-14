package io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Filter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Resources类提供的 处理classpath下资源文件的方法：
 *  getResource(String) 获得一个资源文件的URL
 *  toString(URL,Charset)   将一个URL资源转换成字符串
 *  copy(URL,OutputStream)  将一个URL资源写入字节输出流
 *  readLines(URL,Charset,LineProcessor)    读取一个URL资源，以换行符分割，接受一个LineProcessor参数对其处理
 *  asCharSource(URL,Charset)
 *  asByteSource(URL)
 *
 * @Author: 赵鑫
 * @Date: 2022/7/2
 */
public class FileTest {

    private final String SOURCE_FILE = "src/test/resources/io/resource.txt";
    private final String TARGET_FILE = "src/test/resources/io/target.txt";

    /**
     * guava复制文件
     */
    @Test
    public void testCopyFile_WithGuava() throws IOException {
        Files.copy(new File(SOURCE_FILE), new File(TARGET_FILE));
        assertThat(new File(TARGET_FILE).exists(), equalTo(true));
        // 利用hash值对比文件
        HashCode sourceHashCode = Files.asByteSource(new File(SOURCE_FILE)).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(new File(TARGET_FILE)).hash(Hashing.sha256());
        assertThat(sourceHashCode, equalTo(targetHashCode));
    }

    /**
     * jdk复制文件
     */
    @Test
    public void testCopyFileByJDK() throws IOException {
        java.nio.file.Files.copy(
                Paths.get(SOURCE_FILE),
                Paths.get(TARGET_FILE),
                StandardCopyOption.REPLACE_EXISTING
        );

        assertThat(new File(TARGET_FILE).exists(), equalTo(true));
    }

    /**
     * 移动文件
     */
    @Test
    public void testMoveFile() throws IOException {
        try {
            Files.move(new File(SOURCE_FILE), new File(TARGET_FILE));
            assertThat(new File(TARGET_FILE).exists(), equalTo(true));
            assertThat(new File(SOURCE_FILE).exists(), equalTo(false));
        } finally {
            Files.move(new File(TARGET_FILE), new File(SOURCE_FILE));
        }
    }

    /**
     * 读取文件到String数组
     */
    @Test
    public void testToString() throws IOException {
        final String expectedString = "learning guava io-file";

        List<String> strings = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String result = Joiner.on("\n").join(strings);

        assertThat(result, equalTo(expectedString));
    }

    /**
     * 读取文件并逐行处理
     */
    @Test
    public void testLineProcessString() throws IOException {

        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<List<Integer>>() {

            private final List<Integer> lengthList = new ArrayList<>();

            @Override
            public boolean processLine(String s) throws IOException {
                // 遇到空行，停止读取处理文件
                if (s.length() == 0) {
                    return false;
                }
                lengthList.add(s.length());
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return lengthList;
            }
        };

        List<Integer> result = Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8).readLines(lineProcessor);

        System.out.println(result);
    }

    /**
     * 生成文件的hash值
     */
    @Test
    public void testFileSha() throws IOException {

        File file = new File(SOURCE_FILE);
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode);
    }

    /**
     * 程序结束时自动删除文件：File.deleteOnExit()
     * 写入文件 Files.asCharSink(file,Charsets.UTF_8).write(content)
     * 读取文件 Files.asSource(file,Charsets.UTF_8).read()
     */
    @Test
    public void testFileWriteRead() throws IOException {
        File targetFile = new File(TARGET_FILE);
        // 程序退出时自动删除文件
        targetFile.deleteOnExit();

        String content = "content";

        Files.asCharSink(targetFile, Charsets.UTF_8).write(content);
        String actually = Files.asCharSource(targetFile, Charsets.UTF_8).read();

        assertThat(content, equalTo(actually));
    }

    /**
     * 追加写入
     */
    @Test
    public void testFileAppend() throws IOException {
        File targetFile = new File(TARGET_FILE);
        targetFile.deleteOnExit();
        String content = "content";

        CharSink charSink = Files.asCharSink(targetFile, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write(content);
        charSink.write(content);

        String actually = Files.asCharSource(targetFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(Strings.repeat("content", 2)));
    }

    /**
     * 创建文件
     */
    @Test
    public void testTouchFile() throws IOException {
        File targetFile = new File(TARGET_FILE);
        targetFile.deleteOnExit();

        Files.touch(targetFile);
        assertThat(targetFile.exists(), equalTo(true));
    }

    /**
     * 文件递归
     */
    @Test
    public void testRecursive() {
        List<File> list = new ArrayList<>();
        recursiveList_OnlyFile(new File("src/test"), list);
        list.forEach(System.out::println);
    }
    private void recursiveList_OnlyFile(File root, List<File> fileList) {
        if (root.isHidden()) {
            return;
        }
        if (!root.isFile()) {
            File[] files = root.listFiles();
            for (File f : files) {
                recursiveList_OnlyFile(f, fileList);
            }
        } else {
            fileList.add(root);
        }
    }

    /**
     * 文件递归-guava
     */
    @Test
    public void testTreeFiles_OnlyFile() {
        File root = new File("src/test");
        // fixme 如何筛选文件，过滤文件夹？
        Iterable<File> files = Files.fileTraverser().depthFirstPreOrder(root);
        files.forEach(System.out::println);
    }


    @After
    public void tearDown() {
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }
}
