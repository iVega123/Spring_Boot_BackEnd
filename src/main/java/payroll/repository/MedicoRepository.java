package payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payroll.model.*;
public interface MedicoRepository extends JpaRepository<Medico, Long> {

}