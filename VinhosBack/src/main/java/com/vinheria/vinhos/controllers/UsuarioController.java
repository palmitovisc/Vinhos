package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.entities.Usuario;
import com.vinheria.vinhos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    /* ───────────── 1) CADASTRAR ───────────── */
    // aberto: permite criar o primeiro usuário (admin) ou auto-cadastro
    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario u) {
        u.setSenha(encoder.encode(u.getSenha()));   // BCrypt ✔
        if (u.getPerfil() == null) u.setPerfil("USUARIO");
        return repo.save(u);
    }

    /* ───────────── 2) LISTAR TODOS ─────────── */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> listar() {
        return repo.findAll();
    }

    /* ───────────── 3) VER UM USUÁRIO ───────── */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Usuario ver(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    /* ───────────── 4) ATUALIZAR ────────────── */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario dados) {
        return repo.findById(id).map(u -> {
            u.setNome(dados.getNome());
            u.setEmail(dados.getEmail());
            if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
                u.setSenha(encoder.encode(dados.getSenha()));
            }
            u.setPerfil(dados.getPerfil());
            return repo.save(u);
        }).orElse(null);
    }

    /* ───────────── 5) DELETAR ──────────────── */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
