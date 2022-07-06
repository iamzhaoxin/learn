package q5;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/6
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {


        try (
                Selector selector = Selector.open();
                SocketChannel client = SocketChannel.open();
        ) {
            client.configureBlocking(false);
            client.bind(new InetSocketAddress(7767));
            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress("127.0.0.1", 7766));

            System.out.println("input url:");
            while (selector.select() > 0) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid()) {
                        if (key.isConnectable()) {
                            client.finishConnect();
                            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                            log.info("finished connect");
                        }
                        if (key.isReadable()) {
                            ByteBuffer readBuffer = ByteBuffer.allocate(1025);
                            int count = client.read(readBuffer);
//                            log.info("                                  read length from server: {}", count);
                            if (count > 0) {
                                readBuffer.flip();
                                int receive = readBuffer.getInt();
                                System.out.println(receive);
                            }
                        }
                        if (key.isWritable()) {
                            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                            ReadableByteChannel channel = Channels.newChannel(System.in);
                            ByteBuffer write = ByteBuffer.allocate(1024);
                            int readLength = channel.read(write);
                            if (readLength > 0) {
                                write.flip();
                                client.write(write);
                            }
                        }
                    }
                }
            }
        }

    }
}
