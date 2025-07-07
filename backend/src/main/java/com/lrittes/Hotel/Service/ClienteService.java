package com.lrittes.Hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrittes.Hotel.Model.Cliente;
import com.lrittes.Hotel.Repository.ClienteRepository;
import com.lrittes.Hotel.dto.ClienteDTO;
import com.lrittes.Hotel.exception.cliente.ClienteNotFoundException;
import com.lrittes.Hotel.exception.cliente.ResourceConflictException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO findById(Long id) {
        return clienteRepository.findByClienteId(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com ID " + id + " não encontrado!"));
    }

    public ClienteDTO login(String email, String password) {
        return clienteRepository.login(email,password)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente com Email " + email + " não encontrado!"));
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        try {
            Cliente cliente = convertToEntity(clienteDTO);
    
            cliente = clienteRepository.save(cliente);
            return convertToDTO(cliente);
            
        } catch (Exception e) {
            throw new ResourceConflictException(e.getMessage());
        }
    }

    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        return clienteRepository.findByClienteId(id).map(existingCliente -> {
            existingCliente.setCpf(clienteDTO.getCpf());
            existingCliente.setNome(clienteDTO.getNome());
            existingCliente.setEndereco(clienteDTO.getEndereco());
            existingCliente.setTelefone(clienteDTO.getTelefone());
            return convertToDTO(clienteRepository.save(existingCliente));
        }).orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        clienteRepository.deleteByClienteId(id);
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        return new ClienteDTO(
                        cliente.getId(),
                        cliente.getClienteId(),
                        cliente.getCpf(), 
                        cliente.getNome(), 
                        cliente.getEmail(), 
                        cliente.getPassword(), 
                        cliente.getEndereco(), 
                        cliente.getTelefone());
    }

    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setClienteId(clienteDTO.getClienteId());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setPassword(clienteDTO.getPassword());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setTelefone(clienteDTO.getTelefone());
        return cliente;
    }
}
