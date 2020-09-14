package com.eldorado.springboot.eldoradobackend.model;

import javax.persistence.*;
import com.eldorado.springboot.eldoradobackend.enums.*;

import com.google.api.client.util.DateTime;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @Column(name = "dia_marcado")
    private DateTime diamarcado;

    @Column(name = "status")
    private Status status;

    // additional properties

    public Consulta() {
    
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public DateTime getDiaMarcado() {
        return diamarcado;
    }

    public void setDiaMarcado(DateTime diamarcado) {
        this.diamarcado = diamarcado;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}