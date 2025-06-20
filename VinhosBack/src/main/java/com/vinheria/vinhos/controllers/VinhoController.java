package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.entities.Vinho;
import com.vinheria.vinhos.repositories.VinhoRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vinhos")
public class VinhoController {

    private final VinhoRepository vinhoRepository;

    public VinhoController(VinhoRepository vinhoRepository) {
        this.vinhoRepository = vinhoRepository;
    }

    @GetMapping
    public List<Vinho> listarTodos() {
        return vinhoRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Vinho cadastrar(@RequestBody Vinho v) {
    return vinhoRepository.save(v);
}

    @GetMapping("/{id}")
    public Vinho buscarPorId(@PathVariable Long id) {
        return vinhoRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Vinho atualizar(@PathVariable Long id, @RequestBody Vinho v) {
    return vinhoRepository.findById(id).map(vinho -> {
        vinho.setNome(v.getNome());
        vinho.setSafra(v.getSafra());
        vinho.setTipo(v.getTipo());
        vinho.setNotas(v.getNotas());
        vinho.setHarmonizacoes(v.getHarmonizacoes());
        vinho.setImagemUrl(v.getImagemUrl());
        return vinhoRepository.save(vinho);
    }).orElse(null);
}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
    vinhoRepository.deleteById(id);
}

    @GetMapping("/buscarPorNome/{nome}")
    public List<Vinho> buscarPorNome(@PathVariable String nome) {
        return vinhoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
