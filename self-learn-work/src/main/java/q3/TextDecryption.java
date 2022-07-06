package q3;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @Author: 赵鑫
 * @Date: 2022/7/5
 */
public class TextDecryption {

    static ArrayList<String> natureList = new ArrayList<>();
    static HashMap<Integer, String> indexMap = Maps.newHashMap();
    static ArrayList<String> charList = new ArrayList<>();
    static Integer propLength = 0;
    static String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "sdxl.txt";

    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        readProp();
        System.out.println("readProp: " + stopwatch.stop());

        LineProcessor<Integer> processor = new LineProcessor<Integer>() {
            CharSink sink = Files.asCharSink(new File(path), Charsets.UTF_8, FileWriteMode.APPEND);

            @Override
            public boolean processLine(String line) throws IOException {
                int start = 0;
                while (true) {
                    start = line.indexOf("$");
                    if (start == -1) break;
                    int end = line.indexOf(")", start);
                    int split = line.indexOf("(", start);
                    String function = line.substring(start + 1, split);
                    Integer index = Integer.valueOf(line.substring(split + 1, end));

                    String replaced = line.substring(start, end + 1);
                    String newString = decryption(function, index);
                    line = line.replace(replaced, newString);
                }
                sink.write(line + "\n");
                return true;
            }

            @Override
            public Integer getResult() {
                return null;
            }
        };
        stopwatch.start();
        File target = new File(path);
        if (target.exists()) {
            target.delete();
        }
        Files.asCharSource(new File("attachments/Question 3/sdxl_template.txt"), Charsets.UTF_8).readLines(processor);
        System.out.println("decryption and write: " + stopwatch.stop());

    }

    private static void readProp() throws IOException {
        LineProcessor<Integer> lineProcessor = new LineProcessor<Integer>() {
            @Override
            public boolean processLine(String line) throws IOException {
                String[] split = line.trim().split("\t");
                natureList.add(split[1]);
                indexMap.put(Integer.valueOf(split[0]), split[1]);
                charList.add(split[1]);
                propLength++;
                return true;
            }
            @Override
            public Integer getResult() {
                return null;
            }
        };
        Files.asCharSource(new File("attachments/Question 3/sdxl_prop.txt"), Charsets.UTF_8).readLines(lineProcessor);

        charList.sort(String::compareTo);
    }

    private static String decryption(String function, Integer index) {
        if (function.equals("natureOrder")) {
            return natureList.get(index);
        } else if (function.equals("indexOrder")) {
            return indexMap.get(index);
        } else if (function.equals("charOrder")) {
            return charList.get(index);
        } else {
            return charList.get(propLength - index - 1);
        }
    }

}
