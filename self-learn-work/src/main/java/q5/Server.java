package q5;

import com.google.common.base.CharMatcher;
import com.google.common.collect.HashMultimap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/6
 */
@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {

        ByteBuffer readBuffer = ByteBuffer.allocate(1025);
        HashMultimap<SelectionKey, ByteBuffer> writeBufferMap = HashMultimap.create();

        try (
                // 多路复用器
                Selector selector = Selector.open();
                // 服务器通道（所有客户端通道的父通道）
                ServerSocketChannel server = ServerSocketChannel.open();
        ) {
            // 非阻塞
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(7766));
            // 注册到选择器，监听-连接事件
            server.register(selector, SelectionKey.OP_ACCEPT);
//            log.info("开始监听ACCEPT");

            while (selector.select() > 0) {
                // 已就绪的监听事件
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    // 如果不remove，会导致下次selector.selectedKeys()时，仍有“上次轮询的 已处理的 未删除的 遗留的”SelectionKey
                    keys.remove();
                    // 如果有效
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            // 服务器通道 接收 客户端通道
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                            log.info("accept client: {}", client.getRemoteAddress());
                        }
                        if (key.isReadable()) {
                            // 清空缓冲区
                            readBuffer.clear();
                            // 根据key从selector取出 客户端通道
                            SocketChannel client = (SocketChannel) key.channel();
                            // 从管道读，写入readBuffer
                            try {
                                int readLength = client.read(readBuffer);
                                // 切换读模式
                                readBuffer.flip();
                                String url = new String(readBuffer.array(), 0, readLength);
                                log.info("reading from {}, url={}", client.getRemoteAddress(), url);

                                int count = getCountByHttp(url.trim());
                                log.info("count={}", count);
                                ByteBuffer writeBuffer = ByteBuffer.allocate(Integer.SIZE).putInt(count);
                                // 存入客户端管道对应的writeBuffer（可能同一个client在连续两次轮询中都查询，所以用HashMultimap
                                writeBufferMap.put(key, writeBuffer);
                                // 进入写模式
//                              client.register(selector, SelectionKey.OP_WRITE);
//                              log.info("ready write to {}:{}", client.getRemoteAddress(), count);
                            } catch (IOException e) {
                                log.info("远程主机强迫关闭了一个现有的连接。");
                                client.close();
                                break;
                            }
                        }
                        if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            Set<ByteBuffer> writeBuffers = writeBufferMap.get(key);
                            for (ByteBuffer writeBuffer : writeBuffers) {
                                log.info("write to {}", client.getRemoteAddress());
                                writeBuffer.flip();
                                client.write(writeBuffer);
                            }
                            writeBufferMap.removeAll(key);
//                            client.register(selector, SelectionKey.OP_READ);
                        }
                    }
                }
//                log.info(keys.toString());
            }
        }

    }

    private static int getCountByHttp(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        HttpResponse response = HttpClients.createDefault().execute(request);
        String responseString = EntityUtils.toString(response.getEntity());
        String result = CharMatcher.whitespace().removeFrom(responseString);
        return result.length();
    }

}
