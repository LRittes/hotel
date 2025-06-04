package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Estadia;
import com.lrittes.Hotel.Model.ServicoEstadia;
import com.lrittes.Hotel.Model.ServicoExtra;
import com.lrittes.Hotel.Repository.EstadiaRepository;
import com.lrittes.Hotel.Repository.ServicoEstadiaRepository;
import com.lrittes.Hotel.Repository.ServicoExtraRepository;
import com.lrittes.Hotel.dto.ServicoEstadiaDTO;
import com.lrittes.Hotel.exception.estadia.InsertEstadiaException;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicoEstadiaService {

    @Autowired
    private ServicoEstadiaRepository servicoEstadiaRepository;

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private ServicoExtraRepository servicoExtraRepository;

    public List<ServicoEstadiaDTO> findAll() {
        return servicoEstadiaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ServicoEstadiaDTO> findById(Long id) {
        return servicoEstadiaRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ServicoEstadiaDTO save(ServicoEstadiaDTO servicoEstadiaDTO) {
        try {
            ServicoEstadia servicoEstadia = convertToEntity(servicoEstadiaDTO);
            servicoEstadia = servicoEstadiaRepository.save(servicoEstadia);
            return convertToDTO(servicoEstadia);
        } catch (InsertEstadiaException e) {
            throw new InsertEstadiaException("Erro ao salvar Estádia!\n" + e.getMessage());
        } catch (RuntimeException e){
            throw new RuntimeException("Erro! " + e.getMessage());

        }
    }

    public ServicoEstadiaDTO update(Long id, ServicoEstadiaDTO servicoEstadiaDTO) {
        return servicoEstadiaRepository.findById(id).map(existingServicoEstadia -> {
            existingServicoEstadia.setDataHora(servicoEstadiaDTO.getDataHora());
            existingServicoEstadia.setQuantidade(servicoEstadiaDTO.getQuantidade());

            estadiaRepository.findById(servicoEstadiaDTO.getEstadiaId()).ifPresentOrElse(
                existingServicoEstadia::setEstadia,
                () -> { throw new RuntimeException("Estadia não encontrada com ID: " + servicoEstadiaDTO.getEstadiaId()); }
            );

            servicoExtraRepository.findById(servicoEstadiaDTO.getServicoExtraId()).ifPresentOrElse(
                existingServicoEstadia::setServicoExtra,
                () -> { throw new RuntimeException("Serviço Extra não encontrado com ID: " + servicoEstadiaDTO.getServicoExtraId()); }
            );

            return convertToDTO(servicoEstadiaRepository.save(existingServicoEstadia));
        }).orElseThrow(() -> new RuntimeException("Serviço Estadia não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        servicoEstadiaRepository.deleteById(id);
    }

    private ServicoEstadiaDTO convertToDTO(ServicoEstadia servicoEstadia) {
        return new ServicoEstadiaDTO(
                servicoEstadia.getId(),
                servicoEstadia.getEstadia().getId(),
                servicoEstadia.getServicoExtra().getId(),
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

        Estadia estadia = estadiaRepository.findById(servicoEstadiaDTO.getEstadiaId())
                .orElseThrow(() -> new RuntimeException("Estadia não encontrada com ID: " + servicoEstadiaDTO.getEstadiaId()));
        servicoEstadia.setEstadia(estadia);

        ServicoExtra servicoExtra = servicoExtraRepository.findById(servicoEstadiaDTO.getServicoExtraId())
                .orElseThrow(() -> new RuntimeException("Serviço Extra não encontrado com ID: " + servicoEstadiaDTO.getServicoExtraId()));
        servicoEstadia.setServicoExtra(servicoExtra);

        return servicoEstadia;
    }
}
