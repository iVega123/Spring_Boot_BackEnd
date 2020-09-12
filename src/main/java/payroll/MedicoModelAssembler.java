package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MedicoModelAssembler implements RepresentationModelAssembler<Medico, EntityModel<Medico>> {

  @Override
  public EntityModel<Medico> toModel(Medico medico) {

    return EntityModel.of(medico, //
        linkTo(methodOn(MedicoController.class).one(medico.getId())).withSelfRel(),
        linkTo(methodOn(MedicoController.class).all()).withRel("medicos"));
  }
}