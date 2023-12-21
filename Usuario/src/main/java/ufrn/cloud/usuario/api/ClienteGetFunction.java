package ufrn.cloud.usuario.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.usuario.cliente.Cliente;
import ufrn.cloud.usuario.cliente.ClienteService;

import java.util.function.Function;

@Component
public class ClienteGetFunction {

    private final ClienteService clienteService;

    public ClienteGetFunction(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Bean
    public Function<String, Cliente> getCliente() {
        System.out.println("buscando usuário");
        return id -> clienteService.getById(Long.parseLong(id));
    }
}
