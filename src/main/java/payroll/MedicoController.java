package payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
class MedicoController {

  private final MedicoRepository repository;
  private final MedicoModelAssembler assembler;
  MedicoController(MedicoRepository repository, MedicoModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  // Aggregate root

  @GetMapping("/medicos")
  CollectionModel<EntityModel<Medico>> all() {
  
    List<EntityModel<Medico>> medicos = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());
  
    return CollectionModel.of(medicos, linkTo(methodOn(MedicoController.class).all()).withSelfRel());
  }

  @PostMapping("/medicos")
  Medico newMedico(@RequestBody Medico newMedico) {
    return repository.save(newMedico);
  }

  // Single item
  @GetMapping("/medicos/{id}")
  EntityModel<Medico> one(@PathVariable Long id) {

    Medico medico = repository.findById(id) //
        .orElseThrow(() -> new MedicoNotFoundException(id));
  
    return assembler.toModel(medico);
  }

  @PutMapping("/medicos/{id}")
  Medico replaceMedico(@RequestBody Medico newMedico, @PathVariable Long id) {

    return repository.findById(id)
      .map(medico -> {
        medico.setEmail(newMedico.getEmail());
        medico.setNome(newMedico.getNome());
        medico.setSenha(newMedico.getSenha());
        return repository.save(medico);
      })
      .orElseGet(() -> {
        newMedico.setId(id);
        return repository.save(newMedico);
      });
  }

  @DeleteMapping("/medicos/{id}")
  ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

    repository.deleteById(id);
  
    return ResponseEntity.noContent().build();
  }
}