package payroll;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Medico {

  private @Id @GeneratedValue Long id;
  private String nome;
  private String email;
  private String senha;

  Medico() {}

  Medico(String nome, String email, String senha) {

    this.nome = nome;
    this.email = email;
    this.senha = senha;
  }

  public Long getId() {
    return this.id;
  }

  public String getNome() {
    return this.nome;
  }

  public String getEmail() {
    return this.email;
  }

  public String getSenha(){
    return this.senha;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public void setSenha(String senha){
    this.senha = senha;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Medico))
      return false;
    Medico medico = (Medico) o;
    return Objects.equals(this.id, medico.id) && Objects.equals(this.nome, medico.nome)
        && Objects.equals(this.email, medico.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.nome, this.email);
  }

  @Override
  public String toString() {
    return "Medico{" + "id=" + this.id + ", nome='" + this.nome + '\'' + ", email='" + this.email + '\'' + '}';
  }
}