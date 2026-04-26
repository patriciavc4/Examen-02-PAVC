package com.upiiz.farmacia.services;

import com.upiiz.farmacia.entities.MedicamentoEntity;
import java.util.List;
import java.util.Optional;

public interface MedicamentoService {
    MedicamentoEntity guardarMedicamento(MedicamentoEntity medicamento);
    List<MedicamentoEntity> listarMedicamentos();
    Optional<MedicamentoEntity> getMedicamentoPorId(Long id);
    Optional<MedicamentoEntity> actualizarMedicamento(Long id, MedicamentoEntity medicamento);
    void eliminarMedicamento(Long id);
}