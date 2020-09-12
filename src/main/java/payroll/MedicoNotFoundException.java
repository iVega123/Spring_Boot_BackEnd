package payroll;

class MedicoNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    MedicoNotFoundException(Long id) {
    super("Não foi encontrado um médico com o seguinte id:" + id);
  }
}