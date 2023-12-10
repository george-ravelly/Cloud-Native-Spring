package ufrn.cloud.carrinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CarrinhoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarrinhoApplication.class, args);
    }

}
