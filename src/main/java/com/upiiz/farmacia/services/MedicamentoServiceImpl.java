package com.upiiz.farmacia.services;

import com.upiiz.farmacia.entities.MedicamentoEntity;
import com.upiiz.farmacia.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public MedicamentoEntity guardarMedicamento(MedicamentoEntity medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public List<MedicamentoEntity> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    @Override
    public Optional<MedicamentoEntity> getMedicamentoPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Override
    public Optional<MedicamentoEntity> actualizarMedicamento(Long id, MedicamentoEntity medicamentoEntity) {
        Optional<MedicamentoEntity> medicamentoActualizar = getMedicamentoPorId(id);
        if (medicamentoActualizar.isPresent()) {
            MedicamentoEntity medicamentoActual = medicamentoActualizar.get();
            medicamentoActual.setNombre(medicamentoEntity.getNombre());
            medicamentoActual.setPresentacion(medicamentoEntity.getPresentacion());
            medicamentoActual.setLaboratorio(medicamentoEntity.getLaboratorio());
            medicamentoActual.setPrecio(medicamentoEntity.getPrecio());

            medicamentoRepository.save(medicamentoActual);
            return Optional.of(medicamentoActual);
        }
        return Optional.empty();
    }

    @Override
    public void eliminarMedicamento(Long id) {
        if (medicamentoRepository.existsById(id)) {
            medicamentoRepository.deleteById(id);
        }
    }
}