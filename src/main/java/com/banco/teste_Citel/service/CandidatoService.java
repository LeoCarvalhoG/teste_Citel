package com.banco.teste_Citel.service;

import com.banco.teste_Citel.exception.ResourceNotFoundException;
import com.banco.teste_Citel.model.Candidato;
import com.banco.teste_Citel.repository.CandidatoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private ObjectMapper objectMapper;  

    @Autowired
    public CandidatoService(CandidatoRepository candidatoRepository, ObjectMapper objectMapper) {
        this.candidatoRepository = candidatoRepository;
        this.objectMapper = objectMapper;
    }

    public void saveAll(List<Candidato> candidatos) {
        candidatoRepository.saveAll(candidatos);
    }

    public void processJsonFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não fornecido ou vazio.");
        }
        try {
            List<Candidato> candidatos = parseJsonFile(file);
            saveAll(candidatos);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar o arquivo: " + e.getMessage(), e);
        }
    }

    private List<Candidato> parseJsonFile(MultipartFile file) throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        return List.of(objectMapper.readValue(file.getInputStream(), Candidato[].class));
    }

    public Map<String, Long> contarCandidatosPorEstado() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        if (candidatos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum candidato encontrado.");
        }
        return candidatos.stream()
                .collect(Collectors.groupingBy(Candidato::getEstado, Collectors.counting()));
    }

    public Map<String, Double> calcularIMCmedioPorFaixaEtaria() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        if (candidatos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum candidato encontrado.");
        }
        return candidatos.stream()
                .collect(Collectors.groupingBy(
                        c -> getFaixaEtaria(getIdade(c.getDataNasc())),
                        Collectors.averagingDouble(c -> c.getPeso() / (c.getAltura() * c.getAltura()))
                ));
    }

    public Map<String, Double> calcularPercentualObesosPorGenero() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        if (candidatos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum candidato encontrado.");
        }
        Map<String, Long> totalPorGenero = candidatos.stream()
                .collect(Collectors.groupingBy(Candidato::getSexo, Collectors.counting()));

        Map<String, Long> obesosPorGenero = candidatos.stream()
                .filter(c -> c.getPeso() / (c.getAltura() * c.getAltura()) > 30)
                .collect(Collectors.groupingBy(Candidato::getSexo, Collectors.counting()));

        return obesosPorGenero.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (e.getValue() * 100.0) / totalPorGenero.get(e.getKey())
                ));
    }

    public Map<String, Double> calcularMediaIdadePorTipoSanguineo() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        if (candidatos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum candidato encontrado.");
        }
        return candidatos.stream()
                .collect(Collectors.groupingBy(
                        Candidato::getTipoSanguineo,
                        Collectors.averagingInt(c -> getIdade(c.getDataNasc()))
                ));
    }

    public Map<String, Long> calcularPossiveisDoadoresPorTipoSanguineo() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        if (candidatos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum candidato encontrado.");
        }

        Map<String, Long> possiveisDoadores = candidatos.stream()
                .filter(this::isAptoParaDoar)
                .collect(Collectors.groupingBy(Candidato::getTipoSanguineo, Collectors.counting()));

        return possiveisDoadores.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> candidatos.stream()
                                .filter(c -> isAptoParaReceber(c.getTipoSanguineo(), e.getKey()))
                                .count()
                ));
    }

    public Candidato salvarCandidato(Candidato candidato) {
        return candidatoRepository.save(candidato);
    }

    private boolean isAptoParaDoar(Candidato candidato) {
        int idade = getIdade(candidato.getDataNasc());
        return idade >= 16 && idade <= 69 && candidato.getPeso() > 50;
    }

    private boolean isAptoParaReceber(String tipoSanguineoReceptor, String tipoSanguineoDoador) {
        switch (tipoSanguineoReceptor) {
            case "A+":
                return tipoSanguineoDoador.equals("A+") || tipoSanguineoDoador.equals("A-") ||
                       tipoSanguineoDoador.equals("O+") || tipoSanguineoDoador.equals("O-");
            case "A-":
                return tipoSanguineoDoador.equals("A-") || tipoSanguineoDoador.equals("O-");
            case "B+":
                return tipoSanguineoDoador.equals("B+") || tipoSanguineoDoador.equals("B-") ||
                       tipoSanguineoDoador.equals("O+") || tipoSanguineoDoador.equals("O-");
            case "B-":
                return tipoSanguineoDoador.equals("B-") || tipoSanguineoDoador.equals("O-");
            case "AB+":
                return true;
            case "AB-":
                return tipoSanguineoDoador.equals("A-") || tipoSanguineoDoador.equals("B-") ||
                       tipoSanguineoDoador.equals("O-") || tipoSanguineoDoador.equals("AB-");
            case "O+":
                return tipoSanguineoDoador.equals("O+") || tipoSanguineoDoador.equals("O-");
            case "O-":
                return tipoSanguineoDoador.equals("O-");
            default:
                return false;
        }
    }

    private int getIdade(LocalDate dataNasc) {
        return Period.between(dataNasc, LocalDate.now()).getYears();
    }

    private String getFaixaEtaria(int idade) {
        int faixa = (idade / 10) * 10;
        return faixa + " a " + (faixa + 9);
    }
}
