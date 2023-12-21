package ufrn.cloud.usuario.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.usuario.cliente.Cliente;
import ufrn.cloud.usuario.cliente.ClienteService;

import java.util.function.Function;

@Component
public class ClientePostFunction {
    private final ClienteService clienteService;

    public ClientePostFunction(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Bean
    public Function<Cliente, Cliente> criarCliente() {
        System.out.println("criando novo usuário");
        return clienteService::save;
    }

}
