package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.entities.ItemPedido;
import com.vinheria.vinhos.repositories.ItemPedidoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoController(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    /* ---------- LISTAR ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping
    public List<ItemPedido> listar() {
        return itemPedidoRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/{id}")
    public ItemPedido buscarPorId(@PathVariable Long id) {
        return itemPedidoRepository.findById(id).orElse(null);
    }

    /* ---------- ADICIONAR ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @PostMapping
    public ItemPedido add(@RequestBody ItemPedido dto) {
        return itemPedidoRepository.save(dto);         // usa dto
    }

    /* ---------- ATUALIZAR ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @PutMapping("/{id}")
    public ItemPedido atualizar(@PathVariable Long id, @RequestBody ItemPedido dto) {
        return itemPedidoRepository.findById(id).map(item -> {
            item.setQuantidade(dto.getQuantidade());
            item.setPreco(dto.getPreco());
            item.setPedido(dto.getPedido());
            item.setVinho(dto.getVinho());
            return itemPedidoRepository.save(item);
        }).orElse(null);
    }

    /* ---------- DELETAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        itemPedidoRepository.deleteById(id);
    }

    /* ---------- BUSCAS AUXILIARES ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorPedido/{pedidoId}")
    public List<ItemPedido> buscarPorPedido(@PathVariable Long pedidoId) {
        return itemPedidoRepository.findAll().stream()
                .filter(item -> item.getPedido().getId().equals(pedidoId))
                .toList();
    }

    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorVinho/{vinhoId}")
    public List<ItemPedido> buscarPorVinho(@PathVariable Long vinhoId) {
        return itemPedidoRepository.findAll().stream()
                .filter(item -> item.getVinho().getId().equals(vinhoId))
                .toList();
    }
}