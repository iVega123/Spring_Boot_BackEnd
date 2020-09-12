package payroll;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

  @Override
  public EntityModel<Cliente> toModel(Cliente cliente) {

    EntityModel<Cliente> clienteModel = EntityModel.of(cliente,
        linkTo(methodOn(ClienteController.class).one(cliente.getId())).withSelfRel(),
        linkTo(methodOn(ClienteController.class).all()).withRel("clientes"));


    if (cliente.getStatus() == Status.MARCADO) {
      clienteModel.add(linkTo(methodOn(ClienteController.class).cancel(cliente.getId())).withRel("cancel"));
      clienteModel.add(linkTo(methodOn(ClienteController.class).complete(cliente.getId())).withRel("complete"));
    }

    return clienteModel;
  }
}