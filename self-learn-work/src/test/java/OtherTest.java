import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/5
 */
@Slf4j
public class OtherTest {
    @Test
    public void test() throws IOException {
        HttpGet request = new HttpGet("https://blog.51cto.com/u_14479502/3115669");
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
        HttpResponse response = HttpClients.createDefault().execute(request);
        System.out.println();
    }
}
