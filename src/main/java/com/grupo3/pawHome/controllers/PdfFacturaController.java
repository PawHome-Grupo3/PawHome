package com.grupo3.pawHome.controllers;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.dtos.FacturaDTO;
import com.grupo3.pawHome.entities.Usuario;
import com.grupo3.pawHome.services.FacturaService;
import com.grupo3.pawHome.services.PdfFacturaService;
import com.grupo3.pawHome.services.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class PdfFacturaController {
    private final UsuarioService usuarioService;
    private final FacturaService facturaService;
    private final PdfFacturaService pdfFacturaService;

    public PdfFacturaController(UsuarioService usuarioService, FacturaService facturaService, PdfFacturaService pdfFacturaService) {
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        this.pdfFacturaService = pdfFacturaService;
    }

    @GetMapping("/perfil/facturas/pdf/{id}")
    public ResponseEntity<byte[]> descargarPDF(@PathVariable Integer id, @AuthenticationPrincipal MyUserDetails userDetails) throws Exception {
        Usuario usuario = userDetails.getUsuario();
        List<FacturaDTO> facturasUsuario = facturaService.obtenerFacturasPorUsuario(usuario.getId());

        // Filtrar por ID
        FacturaDTO factura = facturasUsuario.stream()
                .filter(f -> f.getIdFactura().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Factura no encontrada o no pertenece al usuario."));

        byte[] pdfBytes = pdfFacturaService.generarFacturaPDF(factura);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "factura_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


}
