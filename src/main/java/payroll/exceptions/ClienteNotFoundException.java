package payroll.exceptions;

public class ClienteNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ClienteNotFoundException(Long id) {
    super("Não foi encontrado um médico com o seguinte id:" + id);
  }
}
