package com.upiiz.farmacia.repositories;

import com.upiiz.farmacia.entities.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {
}
