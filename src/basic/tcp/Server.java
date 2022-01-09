package basic.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵鑫
 * @Date: 2022/1/9 9:22
 * @Target: 服务器模式的群聊, 服务器使用线程池优化
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
public class Server {
    //创建日志对象        缺省权限：package-private
    static final Logger LOGGER = LoggerFactory.getLogger("Server.class");
    //保存连接到的主机，用于群发
    static final List<Socket> ONLINE_SOCKETS = new ArrayList<>();
    //创建线程池
    /**
     * keepAliveTime：临时线程等待超时时间
     * 表示创建的临时线程在空闲的时候最长的等待时间，但因为子线程一直在while中等待接收消息，不是空闲状态，所以不会关闭
     */
    private static final ExecutorService executorService = new ThreadPoolExecutor(1, 2, 6,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        try {
            System.out.println("服务器启动");
            //1. 注册端口
            ServerSocket serverSocket = new ServerSocket(9999);
            //2. 循环中接收每个客户端的socket管道连接，并交给子线程处理
            while (true) {
                Socket socket = serverSocket.accept();
                //3. 交给线程池调用线程处理
                try {
                    executorService.execute(new ServerThread(socket));
                } catch (Exception e) {
                    LOGGER.info(socket.getRemoteSocketAddress() + "失败，线程资源不足");
                    new PrintStream(socket.getOutputStream()).println("连接人数过多,稍后重试");
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
