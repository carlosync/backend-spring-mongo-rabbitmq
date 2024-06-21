package com.foundationvip.btg.pedidos.controller;

import com.foundationvip.btg.pedidos.controller.dto.ApiResponse;
import com.foundationvip.btg.pedidos.controller.dto.PaginationResponse;
import com.foundationvip.btg.pedidos.controller.dto.PedidoResponse;
import com.foundationvip.btg.pedidos.service.PedidoService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("customers/{customerId}/pedidos")
    public ResponseEntity<ApiResponse<PedidoResponse>> listPedidos(@PathVariable("customerId") Long customerId,
                                                                   @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        var pageResponse = pedidoService.findAllByCustomerId(customerId, PageRequest.of(page, pageSize));
        var totalOnPedidos = pedidoService.findTotalOnPedidosByCustomerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                Map.of("totalOnPedidos", totalOnPedidos),
                pageResponse.getContent(),
                PaginationResponse.fromPage(pageResponse)
        ));
    }
}
