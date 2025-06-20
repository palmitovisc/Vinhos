package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.entities.Comissao;
import com.vinheria.vinhos.repositories.ComissaoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comissoes")
public class ComissaoController {

    private final ComissaoRepository comissaoRepository;

    public ComissaoController(ComissaoRepository comissaoRepository) {
        this.comissaoRepository = comissaoRepository;
    }

    /* ---------- LISTAR / VER ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping
    public List<Comissao> listar() { return comissaoRepository.findAll(); }

    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/{id}")
    public Comissao ver(@PathVariable Long id) {
        return comissaoRepository.findById(id).orElse(null);
    }

    /* ---------- CRIAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Comissao criar(@RequestBody Comissao dto) {

        dto.setData(LocalDateTime.now());

        // se valor nulo, calcula com base no total do pedido
        if (dto.getValor() == null &&
            dto.getPercentual() != null &&
            dto.getPedido() != null) {

            Double calculado = dto.calcularComissao();  // método da entidade
            dto.setValor(calculado);
        }

        return comissaoRepository.save(dto);
    }

    /* ---------- ATUALIZAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Comissao atualizar(@PathVariable Long id, @RequestBody Comissao dto) {
        return comissaoRepository.findById(id).map(comissao -> {
            comissao.setValor(dto.getValor());
            comissao.setPercentual(dto.getPercentual());
            comissao.setData(dto.getData());
            comissao.setRepresentante(dto.getRepresentante());
            comissao.setPedido(dto.getPedido());
            return comissaoRepository.save(comissao);
        }).orElse(null);
    }

    /* ---------- DELETAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        comissaoRepository.deleteById(id);
    }

    /* ---------- FILTROS AUXILIARES ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorRepresentante/{representanteId}")
    public List<Comissao> buscarPorRepresentante(@PathVariable Long representanteId) {
        return comissaoRepository.findAll().stream()
                .filter(c -> c.getRepresentante().getId().equals(representanteId))
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorPedido/{pedidoId}")
    public List<Comissao> buscarPorPedido(@PathVariable Long pedidoId) {
        return comissaoRepository.findAll().stream()
                .filter(c -> c.getPedido().getId().equals(pedidoId))
                .toList();
    }
}