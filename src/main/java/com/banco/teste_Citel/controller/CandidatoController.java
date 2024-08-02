package com.banco.teste_Citel.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.banco.teste_Citel.service.CandidatoService;

@RestController
@RequestMapping("/")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    @GetMapping
    public String home() {
        return "Bem-vindo à aplicação!";
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/candidatos/por-estado")
    public ResponseEntity<Map<String, Long>> contarCandidatosPorEstado() {
        return new ResponseEntity<Map<String, Long>>(candidatoService.contarCandidatosPorEstado(), HttpStatus.OK);
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/candidatos/imc-medio-por-faixa-etaria")
    public ResponseEntity<Map<String, Double>> calcularIMCmedioPorFaixaEtaria() {
        return ResponseEntity.ok(candidatoService.calcularIMCmedioPorFaixaEtaria());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/candidatos/percentual-obesos-por-genero")
    public ResponseEntity<Map<String, Double>> calcularPercentualObesosPorGenero() {
        return ResponseEntity.ok(candidatoService.calcularPercentualObesosPorGenero());
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/candidatos/media-idade-por-tipo-sanguineo")
    public ResponseEntity<Map<String, Double>> calcularMediaIdadePorTipoSanguineo() {
        return ResponseEntity.ok(candidatoService.calcularMediaIdadePorTipoSanguineo());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/candidatos/possiveis-doadores-por-tipo-sanguineo")
    public ResponseEntity<Map<String, Long>> calcularPossiveisDoadoresPorTipoSanguineo() {
        return ResponseEntity.ok(candidatoService.calcularPossiveisDoadoresPorTipoSanguineo());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/candidatos/adicionar")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            candidatoService.processJsonFile(file);
            return ResponseEntity.ok("File uploaded and data saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao processar o arquivo: " + e.getMessage());
        }
    }
}

