package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.ServicoEstadia;
import com.lrittes.Hotel.Repository.ServicoEstadiaRepository;
import com.lrittes.Hotel.dto.ServicoEstadiaDTO;
import com.lrittes.Hotel.exception.estadia.InsertEstadiaException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoEstadiaService {

    @Autowired
    private ServicoEstadiaRepository servicoEstadiaRepository;


    public List<ServicoEstadiaDTO> findAll() {
        return servicoEstadiaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ServicoEstadiaDTO> findById(Long id) {
        return servicoEstadiaRepository.findBySeid(id)
                .map(this::convertToDTO);
    }

    public ServicoEstadiaDTO save(ServicoEstadiaDTO servicoEstadiaDTO) {
        try {
            ServicoEstadia servicoEstadia = convertToEntity(servicoEstadiaDTO);

            if (servicoEstadia.getDataHora() == null) {
                servicoEstadia.setDataHora(LocalDateTime.now());
            }

            servicoEstadia = servicoEstadiaRepository.save(servicoEstadia);
            return convertToDTO(servicoEstadia);
        } catch (InsertEstadiaException e) {
            throw new InsertEstadiaException("Erro ao salvar Estádia!\n" + e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Erro! " + e.getMessage());

        }
    }

    public ServicoEstadiaDTO update(Long id, ServicoEstadiaDTO servicoEstadiaDTO) {
        return servicoEstadiaRepository.findBySeid(id).map(existingServicoEstadia -> {
            existingServicoEstadia.setDataHora(servicoEstadiaDTO.getDataHora());
            existingServicoEstadia.setQuantidade(servicoEstadiaDTO.getQuantidade());
            existingServicoEstadia.setServicoExtraId(servicoEstadiaDTO.getServicoExtraId());
            existingServicoEstadia.setEstadiaId(servicoEstadiaDTO.getEstadiaId());

            return convertToDTO(servicoEstadiaRepository.save(existingServicoEstadia));
        }).orElseThrow(() -> new RuntimeException("Serviço Estadia não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        servicoEstadiaRepository.deleteBySeid(id);
    }

    private ServicoEstadiaDTO convertToDTO(ServicoEstadia servicoEstadia) {
        return new ServicoEstadiaDTO(
                servicoEstadia.getId(),
                servicoEstadia.getSeid(),
                servicoEstadia.getEstadiaId(),
                servicoEstadia.getServicoExtraId(),
                servicoEstadia.getDataHora(),
                servicoEstadia.getQuantidade(),
                servicoEstadia.getDescricao()
        );
    }

    private ServicoEstadia convertToEntity(ServicoEstadiaDTO servicoEstadiaDTO) {
        ServicoEstadia servicoEstadia = new ServicoEstadia();
        servicoEstadia.setId(servicoEstadiaDTO.getId());
        servicoEstadia.setDataHora(servicoEstadiaDTO.getDataHora());
        servicoEstadia.setQuantidade(servicoEstadiaDTO.getQuantidade());
        servicoEstadia.setDescricao(servicoEstadiaDTO.getDescricao());
        servicoEstadia.setServicoExtraId(servicoEstadiaDTO.getServicoExtraId());
        servicoEstadia.setEstadiaId(servicoEstadiaDTO.getEstadiaId());

        return servicoEstadia;
    }
}
