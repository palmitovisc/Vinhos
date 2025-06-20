package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.entities.Pedido;
import com.vinheria.vinhos.repositories.PedidoRepository;
import com.vinheria.vinhos.security.AuthzService;          // helper dono
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository repo;
    private final AuthzService authzService;              // injeta helper

    public PedidoController(PedidoRepository repo, AuthzService authzService) {
        this.repo = repo;
        this.authzService = authzService;
    }

    /* ---------- LISTAR ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping
    public List<Pedido> listar() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean admin = SecurityContextHolder.getContext().getAuthentication()
                         .getAuthorities().stream()
                         .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return admin ? repo.findAll()
                     : repo.findByRepresentanteUsuarioEmail(email);
    }

    /* ---------- VER POR ID ---------- */
    @PreAuthorize("hasRole('ADMIN') or @authzService.isOwnerPedido(#id)")
    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    /* ---------- CRIAR ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @PostMapping
    public Pedido emitir(@RequestBody Pedido p) {
        return repo.save(p);
    }

    /* ---------- ATUALIZAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @RequestBody Pedido p) {
        return repo.findById(id).map(ped -> {
            ped.setStatus(p.getStatus());
            ped.setTotal(p.getTotal());
            ped.setCliente(p.getCliente());
            ped.setRepresentante(p.getRepresentante());
            return repo.save(ped);
        }).orElse(null);
    }

    /* ---------- DELETAR ---------- */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repo.deleteById(id);
    }

    /* ---------- CONSULTAS AUXILIARES ---------- */
    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorCliente/{clienteId}")
    public List<Pedido> buscarPorCliente(@PathVariable Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    @PreAuthorize("hasAnyRole('ADMIN','REPRESENTANTE')")
    @GetMapping("/buscarPorRepresentante/{representanteId}")
    public List<Pedido> buscarPorRepresentante(@PathVariable Long representanteId) {
        return repo.findByRepresentanteId(representanteId);
    }
}
