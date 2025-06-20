package com.vinheria.vinhos.controllers;

import com.vinheria.vinhos.repositories.RelatorioRepository;
import com.vinheria.vinhos.views.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioRepository relatorioRepository;

    public RelatorioController(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    @GetMapping("/comissoes")
    public List<ComissaoDetalhadaView> comissoes() {
        return relatorioRepository.comissoesDetalhadas();
    }

    @GetMapping("/pedidos")
    public List<PedidoResumoView> pedidos() {
        return relatorioRepository.pedidosResumo();
    }

    @GetMapping("/vendas-representantes")
    public List<VendasPorRepresentanteView> vendasPorRepresentante() {
        return relatorioRepository.vendasPorRepresentante();
    }
}