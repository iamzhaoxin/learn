package q4;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.io.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/5
 */
public class SimulateLinuxShell {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String command = bufferedReader.readLine();
            if (command.equals("exit")) {
                break;
            }
            if (Strings.isNullOrEmpty(command)) {
                continue;
            }
            String path = handle(CharMatcher.breakingWhitespace().trimAndCollapseFrom(command, ' '), null);
            CharSource source = Files.asCharSource(new File(path), Charsets.UTF_8);
            source.readLines().forEach(System.out::println);
            System.out.println("======");
        }
    }

    private static String handle(String command, String preResult) throws IOException {
        if (Strings.isNullOrEmpty(command)) {
            return preResult;
        }
        int commandEnd = command.indexOf('|');
        commandEnd = commandEnd == -1 ? command.length() - 2 : commandEnd;

        if (command.startsWith("cat")) {
            if (Strings.isNullOrEmpty(preResult)) {
                return handle(command.substring(commandEnd + 2), command.split(" ")[1]);
            } else {
                return handle(command.substring(commandEnd + 2), preResult);
            }
        } else if (command.startsWith("grep")) {
            String keyword = command.split(" ")[1];
            String filePath = Strings.isNullOrEmpty(preResult) ? command.split(" ")[2] : preResult;
            String tempFilePath = grep(keyword, filePath);
            return handle(command.substring(commandEnd + 2), tempFilePath);
        } else if (command.startsWith("wc -l")) {
            String tempFilePath;
            if (Strings.isNullOrEmpty(preResult)) {
                tempFilePath = wc(command.split(" ")[2]);
            } else {
                tempFilePath = wc(preResult);
            }
            return handle(command.substring(commandEnd + 2), tempFilePath);
        }else{
            return null;
        }
    }

    private static String wc(String filepath) throws IOException {
        String tempPath= System.currentTimeMillis()+".txt";
        File tempFile=new File(tempPath);
        tempFile.deleteOnExit();
        CharSink sink = Files.asCharSink(tempFile, Charsets.UTF_8, FileWriteMode.APPEND);

        ImmutableList<String> list = Files.asCharSource(new File(filepath), Charsets.UTF_8).readLines();
        sink.write(String.valueOf(list.size()));

        return tempPath;
    }

    private static String grep(String keyword, String filePath) throws IOException {
        String tempPath= System.currentTimeMillis()+".txt";
        File tempFile=new File(tempPath);
        tempFile.deleteOnExit();
        CharSink sink = Files.asCharSink(tempFile, Charsets.UTF_8, FileWriteMode.APPEND);

        LineProcessor<Integer> processor = new LineProcessor<Integer>() {

            @Override
            public boolean processLine(String line) throws IOException {
                if (line.contains(keyword)){
                    sink.write(line+"\n");
                }
                return true;
            }

            @Override
            public Integer getResult() {
                return null;
            }
        };
        Integer integer = Files.asCharSource(new File(filePath), Charsets.UTF_8).readLines(processor);
        return tempPath;
    }
}
