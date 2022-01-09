package basic.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/9 9:47
 */
public class ServerThread implements Runnable {
    Socket socket;

    public ServerThread(Socket socket) throws Exception {
        this.socket = socket;
        //运行构造器不代表分配到了子线程，分配到子线程后执行run
        Server.LOGGER.trace(LocalDateTime.now().toString() + socket.getRemoteSocketAddress() + "正在加入群聊");
        new PrintStream(socket.getOutputStream()).println("连接服务器成功，排队加入群聊ing");
    }

    /**
     * 从当前套接字接收消息，群发给其他主机
     */
    @Override
    public void run() {
        try {
            synchronized (Server.ONLINE_SOCKETS) {
                //将当前套接字加入列表       （调试发现，某个线程停在这儿时，其他线程成功被阻塞？？？
                Server.ONLINE_SOCKETS.add(socket);
            }
            Server.LOGGER.trace(LocalDateTime.now().toString() + socket.getRemoteSocketAddress() + "加入群聊成功");
            new PrintStream(socket.getOutputStream()).println("加入群聊成功");
            //接收消息
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //群发消息
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                for (Socket onlineSocket : Server.ONLINE_SOCKETS) {
                    if (onlineSocket != socket) {
                        new PrintStream(onlineSocket.getOutputStream()).println(msg);
                    }
                }
            }
        } catch (Exception e) {
            //连接断开
            Server.LOGGER.trace(LocalDateTime.now().toString() + socket.getRemoteSocketAddress() + "断开连接" + e);
            synchronized (Server.ONLINE_SOCKETS) {
                Server.ONLINE_SOCKETS.remove(socket);
            }
        }
    }
}
