package basic.tcp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 服务器模式的群聊,服务器使用线程池优化     TODO：服务器断开连接后，只能由子线程发现，无法结束主线程🤬
 *
 * @Author: 赵鑫
 * @Date: 2022/1/8 22:52
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("客户端启动，正在请求连接");
        try {
            //1. 创建接收消息的Socket对象，参数：目标服务器。（不指定，则本地端口号随机）
            Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
            //创建线程随时接收消息
            new ClientInputThread(socket).start();
            //2. 得到字节输出流，包装成打印流
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            //3. 发送数据
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String msg = scanner.nextLine();

                if ("exit".equalsIgnoreCase(msg)) {
                    System.out.println("exited");
                    socket.close();
                    break;
                }
                //如果是print，服务器接收到数据读取时，readLine读取不到换行，gg
                printStream.println(msg);
                //要刷新！！！
                printStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


