package payroll.controller;


import java.util.List;
import java.util.stream.Collectors;
import payroll.enums.*;
import payroll.model.*;
import payroll.repository.*;
import payroll.modelassembler.*;
import payroll.exceptions.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteModelAssembler assembler;

    ClienteController(ClienteRepository clienteRepository, ClienteModelAssembler assembler) {

        this.clienteRepository = clienteRepository;
        this.assembler = assembler;
    }

    @GetMapping("/clientes")
    public CollectionModel<EntityModel<Cliente>> all() {

        List<EntityModel<Cliente>> clientes = clienteRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(clientes, //
                linkTo(methodOn(ClienteController.class).all()).withSelfRel());
    }

    @GetMapping("/clientes/{id}")
    public EntityModel<Cliente> one(@PathVariable Long id) {

        Cliente cliente = clienteRepository.findById(id) //
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return assembler.toModel(cliente);
    }

    @PostMapping("/clientes")
    ResponseEntity<EntityModel<Cliente>> newCliente(@RequestBody Cliente cliente) {

        cliente.setStatus(Status.MARCADO);
        Cliente newCliente = clienteRepository.save(cliente);

        return ResponseEntity //
                .created(linkTo(methodOn(ClienteController.class).one(newCliente.getId())).toUri()) //
                .body(assembler.toModel(newCliente));
    }

    @DeleteMapping("/clientes/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {

        Cliente cliente = clienteRepository.findById(id) //
                .orElseThrow(() -> new ClienteNotFoundException(id));

        if (cliente.getStatus() == Status.MARCADO) {
            cliente.setStatus(Status.CANCELADO);
            return ResponseEntity.ok(assembler.toModel(clienteRepository.save(cliente)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Método não Permitido!") //
                        .withDetail("Você não pode cancelar uma ordem " + cliente.getStatus() + " status"));
    }

    @PutMapping("/clientes/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {

        Cliente cliente = clienteRepository.findById(id) //
                .orElseThrow(() -> new ClienteNotFoundException(id));

        if (cliente.getStatus() == Status.MARCADO) {
            cliente.setStatus(Status.FINALIZADO);
            return ResponseEntity.ok(assembler.toModel(clienteRepository.save(cliente)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an order that is in the " + cliente.getStatus() + " status"));
    }
}