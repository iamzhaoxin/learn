package q5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/6
 */
public class ClientThread {
    static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 7766);
            new clientReceive().start();

            PrintStream printStream = new PrintStream(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String msg = scanner.nextLine();
                if ("exit".equalsIgnoreCase(msg)) {
                    socket.close();
                    break;
                }
                printStream.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Socket closed");
        }
    }

    static class clientReceive extends Thread {
        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] receive = new byte[1024];
                while (inputStream.read(receive) != -1) {
                    System.out.println(ByteBuffer.wrap(receive).getInt());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
