package q1;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 读文件预处理：记录POST和GET的数量和请求总量，URL删除参数,存到TreeMap<URL,number>和HashMultimap<String,String>中  -- 1 3 4
 * 根据Number排序TreeMap<URL,number>，截取前10个 -- 2
 * <p>
 * 可以不排序，直接遍历取最大的十个
 * HashMultiMap -> 前缀树
 *
 * @Author: 赵鑫
 * @Date: 2022/7/5
 */
public class AnalyseLog {

    static String inPath = "attachments/Question 1/access.log";
    static String outPath = "attachments/Question 1/result.log";
    static Integer postNumber = 0;
    static Integer getNumber = 0;
    static Integer requestNumber = 0;
    static List<Map.Entry<String, Integer>> maxRequest = null;
    static HashMultimap<String, String> cluster = HashMultimap.create();

    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        // URL-requestNumber
        TreeMap<String, Integer> UNMap = read();
        System.out.println("read file: " + stopwatch.stop());

        stopwatch.start();
        List<Map.Entry<String, Integer>> entries = Lists.newArrayList(UNMap.entrySet());
        entries.sort((o1, o2) -> o2.getValue() - o1.getValue());
        maxRequest = entries.subList(0, 10);
        System.out.println("get most requests: " + stopwatch.stop());

        stopwatch.start();
        print();
        System.out.println("print: " + stopwatch.stop());
    }

    private static TreeMap<String, Integer> read() throws IOException {
        LineProcessor<TreeMap<String, Integer>> mapLineProcessor = new LineProcessor<TreeMap<String, Integer>>() {
            TreeMap<String, Integer> UNMap = Maps.newTreeMap();

            @Override
            public boolean processLine(String line) throws IOException {
                // 计数
                String get = Strings.commonPrefix(line, "GET");
                if (Strings.isNullOrEmpty(get)) {
                    postNumber++;
                } else {
                    getNumber++;
                }
                requestNumber++;
                // 删除？后的参数
                String URL = line.trim().split(" ")[1];
                URL = URL.split("\\?")[0];
                // 记录URL和对应访问次数
                UNMap.put(URL, UNMap.getOrDefault(URL, 0) + 1);
                // 记录不同AAA对应的URI
                String AAA = URL.split("/")[1];
                cluster.put(AAA, URL);
                return true;
            }

            @Override
            public TreeMap<String, Integer> getResult() {
                return UNMap;
            }
        };
        return Files.asCharSource(new File(inPath), Charsets.UTF_8).readLines(mapLineProcessor);
    }

    private static void print() throws IOException {
        File outFile = new File(outPath);
        if (outFile.exists()) {
            outFile.delete();
        }
        CharSink sink = Files.asCharSink(outFile, Charsets.UTF_8,
                FileWriteMode.APPEND);
        sink.write("请求总量: " + requestNumber + "\n");
        sink.write("最频繁的10个接口:\n");
        maxRequest.forEach(e -> {
            try {
                sink.write(e.getValue() + " " + e.getKey() + "\n");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        sink.write("POST请求量 " + postNumber + "\n");
        sink.write("GET请求量  " + getNumber + "\n");
        sink.write("各类URI:\n");
        for (String AAA : cluster.keySet()) {
            Set<String> URLs = cluster.get(AAA);
            sink.write(AAA + "\n");
            URLs.forEach(u -> {
                try {
                    sink.write("    " + u + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


}
