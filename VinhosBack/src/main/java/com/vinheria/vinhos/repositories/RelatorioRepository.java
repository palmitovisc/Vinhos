package com.vinheria.vinhos.repositories;

import com.vinheria.vinhos.entities.Pedido;                 // ENTIDADE real
import com.vinheria.vinhos.views.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Pedido, Long> {

    /* VIEW: vw_comissoes_detalhadas */
    @Query(value = "SELECT * FROM vw_comissoes_detalhadas", nativeQuery = true)
    List<ComissaoDetalhadaView> comissoesDetalhadas();

    /* VIEW: vw_pedidos_resumo */
    @Query(value = "SELECT * FROM vw_pedidos_resumo", nativeQuery = true)
    List<PedidoResumoView> pedidosResumo();

    /* VIEW: vw_vendas_por_representante */
    @Query(value = "SELECT * FROM vw_vendas_por_representante", nativeQuery = true)
    List<VendasPorRepresentanteView> vendasPorRepresentante();
}