package payroll;

class ClienteNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ClienteNotFoundException(Long id) {
    super("Não foi encontrado um médico com o seguinte id:" + id);
  }
}
