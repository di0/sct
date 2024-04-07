package br.com.tiviati.sct.repository;

import br.com.tiviati.sct.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> { }
