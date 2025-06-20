package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.entities.Cliente;
import com.vinheria.vinhos.repositories.ClienteRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")          // ← base da rota
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /* ---------- LISTAR TODOS ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping                    // GET /clientes
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /* ---------- CADASTRAR ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @PostMapping                   // POST /clientes
    public Cliente cadastrar(@RequestBody Cliente dto) {
        return clienteRepository.save(dto);   // usa dto
    }

    /* ---------- BUSCAR POR ID ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/{id}")           // GET /clientes/{id}
    public Cliente buscarPorId(@PathVariable Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    /* ---------- ATUALIZAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")           // PUT /clientes/{id}
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente dto) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(dto.getNome());
            cliente.setCnpjCpf(dto.getCnpjCpf());
            cliente.setEndereco(dto.getEndereco());
            cliente.setResponsavel(dto.getResponsavel());
            cliente.setContato(dto.getContato());
            return clienteRepository.save(cliente);
        }).orElse(null);
    }

    /* ---------- DELETAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")        // DELETE /clientes/{id}
    public void deletar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }

    /* ---------- BUSCAR POR NOME ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorNome/{nome}")   // GET /clientes/buscarPorNome/{nome}
    public List<Cliente> buscarPorNome(@PathVariable String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
}