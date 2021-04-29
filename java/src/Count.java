import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Count {

    public static void main(String[] args) {
        int numChar = 0;

        Path path = Paths.get("src/input.txt");
        String s = null;
        try {
            s = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(s).length(); i++) {
            while (Character.isLetterOrDigit(s.charAt(i))) {//如果是字母或数字，就继续添加到临时字符串中
                t.append(s.charAt(i));
                i++;
                numChar++;
                if(i>=s.length())
                    break;
            }
            if (!t.toString().equals("")) {
                System.out.println(t);
                t = new StringBuilder();//清空临时字符串
            }
        }

        System.out.println("字符数： "+numChar);
    }
}
