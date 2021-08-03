package lt.addendum.job.interview.task.configurations;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfiguration {

    @Value("${timeout.connection}")
    private Integer timeout;

    @Value("${qr.client.url}")
    private String url;

    @Bean
    public WebClient createWebClient() throws SSLException {

        SslContext context = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient = HttpClient.create()
                .secure(t -> t.sslContext(context))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(conn -> conn
                .addHandler(new ReadTimeoutHandler(timeout, TimeUnit.SECONDS))
                .addHandler(new WriteTimeoutHandler(timeout)))
                .keepAlive(true);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(url)
                .build();
    }
}
