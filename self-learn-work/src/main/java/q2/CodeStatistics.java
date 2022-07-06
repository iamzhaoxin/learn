package q2;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

import java.io.File;
import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/5
 */
public class CodeStatistics {
    public static void main(String[] args) throws IOException {
        String sourcePath = "attachments/Question 2/StringUtils.java";
        String targetPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "StringUtils.java";
        Files.copy(new File(sourcePath), new File(targetPath));

        LineProcessor<Integer> codeLineProcessor = new LineProcessor<Integer>() {
            Integer codeLinesNumber = 0;

            boolean annotation = false;

            @Override
            public boolean processLine(String line) throws IOException {
                line = CharMatcher.whitespace().removeFrom(line);
                if (!Strings.isNullOrEmpty(line)) {
                    judge(line, true);
                }
                return true;
            }

            /**
             * @param line 要判断的剩余字符串
             * @param noCountLine 避免codeLinesNumber重复计数
             */
            private void judge(String line, boolean noCountLine) {
                if (!annotation) {
                    if (line.length() == 1 && noCountLine) {
                        codeLinesNumber++;
                        return;
                    }
                    for (int i = 0; i < line.length() - 1; i++) {
                        if (line.startsWith("//", i)) {
                            break;
                        } else if (line.startsWith("/*", i)) {
                            i += 2;
                            annotation = true;
                            int rightAnno = line.indexOf("*/", i);
                            if (rightAnno != -1) {
                                annotation = false;
                                i = rightAnno + 2;
                            }
                        } else {
                            if (noCountLine) {
                                codeLinesNumber++;
                                System.out.println(line);
                                noCountLine = false;
                            }
                            if (line.charAt(i) == '"') {
                                i++;
                                while (i < line.length() && line.charAt(i) != '"') {
                                    i++;
                                }

                            }
                        }
                    }
                } else {
                    int end = line.indexOf("*/");
                    if (end != -1) {
                        annotation = false;
                        judge(line.substring(end + 2), noCountLine);
                    }
                }
            }

            private void add() {

            }

            @Override
            public Integer getResult() {
                return codeLinesNumber;
            }
        };

        System.out.println(Files.asCharSource(new File(sourcePath), Charsets.UTF_8).readLines(codeLineProcessor));
    }
}
