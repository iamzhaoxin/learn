package basic.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/** Server.java
 * @Author: 赵鑫
 * @Date: 2022/1/7 22:21
 */
public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("服务端启动");
        //创建接收端对象
        DatagramSocket socket = new DatagramSocket(8888);
        //数据包接收数据到buffer
        byte[] buffer = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            socket.receive(packet);
            //buffer 的0~packet.length 转换为string，防止多余转换
            String msg = new String(buffer, 0, packet.getLength());
//            String address = packet.getSocketAddress().toString();    //输出  /192.168.50.147:6666
            String ip = packet.getAddress().toString();
            int port = packet.getPort();
            System.out.println("from "+ip+":"+port+"  msg: "+msg);
        }
    }
}
