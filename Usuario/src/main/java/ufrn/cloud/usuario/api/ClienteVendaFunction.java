package ufrn.cloud.usuario.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ufrn.cloud.usuario.cliente.Cliente;
import ufrn.cloud.usuario.cliente.ClienteService;
import ufrn.cloud.usuario.dto.VendaClienteDTO;

import java.util.function.Function;

@Component
public class ClienteVendaFunction {
    private final ClienteService service;

    public ClienteVendaFunction(ClienteService service) {
        this.service = service;
    }

    @Bean
    public Function<VendaClienteDTO, String> clienteValidation() {
        return vendaCliente -> {
            System.out.println("validando usuário!");
            if (!service.clienteExiste(vendaCliente.userId())) return vendaCliente.codigoVenda().toString();
            return "";
        };
    }
}
