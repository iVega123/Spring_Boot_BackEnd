package payroll.model;

import payroll.enums.*;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Clientes")
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private Status status;

  Cliente() {}

  Cliente(String nome, Status status) {

    this.nome = nome;
    this.status = status;
  }

  public Long getId() {
    return this.id;
  }

  public String getNome() {
    return this.nome;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Cliente))
      return false;
    Cliente cliente = (Cliente) o;
    return Objects.equals(this.id, cliente.id) && Objects.equals(this.nome, cliente.nome)
        && this.status == cliente.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.nome, this.status);
  }

  @Override
  public String toString() {
    return "Cliente{" + "id=" + this.id + ", nome='" + this.nome + '\'' + ", status=" + this.status + '}';
  }
}