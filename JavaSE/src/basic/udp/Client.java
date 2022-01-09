package basic.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Client.java
 * @Author: 赵鑫
 * @Date: 2022/1/7 22:14
 */
public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("客户端启动");
        //创建发送端对象
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("input msg:");
            String msg = scanner.nextLine();

            if ("exit".equalsIgnoreCase(msg)) {
                System.out.println("exit success");
                socket.close();
                break;
            }
            //创建数据包对象封装数据
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getLocalHost(), 8888);
            socket.send(packet);
        }
    }
}
