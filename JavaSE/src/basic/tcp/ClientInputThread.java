package basic.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 创建客户端的接收消息线程
 *
 * @Author: 赵鑫
 * @Date: 2022/1/9 9:45
 */
public class ClientInputThread extends Thread {
    private final Socket socket;

    public ClientInputThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //得到字节输出流并包装为打印流 × 因为打印流是输出流
            //得到字节流->缓冲流(BufferedInputStream) × 因为BufferedInputStream只能按字节读取
            //字节流->转换流->缓冲流，按行读取
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //处理接收到的字符流
            String msg;
            //平时会处于 阻塞 状态，等待读取
            //如果另一端断开连接，会读取到 null
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("receive msg: " + msg);
            }
            System.out.println("服务器断开连接");
        } catch (Exception e) {
            System.out.println("服务器断开连接");
        }
    }
}
