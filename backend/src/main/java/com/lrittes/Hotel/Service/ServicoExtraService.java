package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.ServicoExtra;
import com.lrittes.Hotel.Repository.ServicoExtraRepository;
import com.lrittes.Hotel.dto.ServicoExtraDTO;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoExtraService {

    @Autowired
    private ServicoExtraRepository servicoExtraRepository;

    public List<ServicoExtraDTO> findAll() {
        return servicoExtraRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ServicoExtraDTO> findById(Long id) {
        return servicoExtraRepository.findBySeid(id)
                .map(this::convertToDTO);
    }

    public ServicoExtraDTO save(ServicoExtraDTO servicoExtraDTO) {
        ServicoExtra servicoExtra = convertToEntity(servicoExtraDTO);
        servicoExtra = servicoExtraRepository.save(servicoExtra);
        return convertToDTO(servicoExtra);
    }

    public ServicoExtraDTO update(Long id, ServicoExtraDTO servicoExtraDTO) {
        return servicoExtraRepository.findBySeid(id).map(existingServicoExtra -> {
            existingServicoExtra.setDescricao(servicoExtraDTO.getDescricao());
            existingServicoExtra.setPreco(servicoExtraDTO.getPreco());
            return convertToDTO(servicoExtraRepository.save(existingServicoExtra));
        }).orElseThrow(() -> new RuntimeException("Serviço Extra não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        servicoExtraRepository.deleteBySeid(id);
    }

    private ServicoExtraDTO convertToDTO(ServicoExtra servicoExtra) {
        return new ServicoExtraDTO(servicoExtra.getId(), servicoExtra.getSeid(), servicoExtra.getDescricao(), servicoExtra.getPreco());
    }

    private ServicoExtra convertToEntity(ServicoExtraDTO servicoExtraDTO) {
        ServicoExtra servicoExtra = new ServicoExtra();
        servicoExtra.setId(servicoExtraDTO.getId());
        servicoExtra.setDescricao(servicoExtraDTO.getDescricao());
        servicoExtra.setPreco(servicoExtraDTO.getPreco());
        return servicoExtra;
    }
}
