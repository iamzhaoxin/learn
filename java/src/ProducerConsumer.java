import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();//创建共享区对象，作为共享仓库
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
    }
}

//生产者
class Producer extends Thread {
    private Buffer buffer;
    private Queue<String> qu = new LinkedList<String>();

    public Producer(Buffer buffer) {
        this.buffer = buffer;//接收共享对象
        read();//读取文件，最多15个字符串（调整79行）
    }

    @Override
    public void run() {
        for (String q : qu) {
            try {
                buffer.add(q);//将读取到的字符串存到共享区
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //读取字符串
    private void read() {
        Path path = Paths.get("C:\\Users\\14791\\Desktop\\input.txt");
        String s = null;
        try {
            s = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String t = "";
        if (s != null) {
            System.out.println("***"+s+"***");
            for (int i = 0; i < s.length(); i++) {
                while (Character.isLetterOrDigit(s.charAt(i))) {//如果是字母或数字，就继续添加到临时字符串中
                    t = t + s.charAt(i);
                    i++;
                }
                if (!t.equals("")) {
                    qu.add(t);//将临时字符串存到队列中
                    //System.out.println(t);
                    t = "";//清空临时字符串
                }
            }
        } else {
            System.out.println("未读取到任何内容");
        }
    }
}

//消费者
class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.print("{");
        for (int i = 0; i < 15; i++) {
            if (buffer.isEmpty()){//如果共享区为空，等待150ms
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!buffer.isEmpty()){//等待后不为空，则“消费”
                String val = new StringBuilder(buffer.pull()).reverse().toString();
                if (i != 0)
                    System.out.print(",");
                System.out.print("\"" + val + "\"");
            }
        }
        System.out.print("}");
    }
}

//共享区
class Buffer {
    private Queue<String> queue = new LinkedList<String>();//共享区，以队列形式存储

    public synchronized void add(String val) {
        queue.add(val);
        notify();//对资源解锁，通知消费者去消费
    }

    public synchronized String pull() {
        String val = queue.poll();
        notify();
        return val;
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}
