package com.banco.teste_Citel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.banco.teste_Citel.model.Candidato;
import com.banco.teste_Citel.repository.CandidatoRepository;

@SpringBootTest
class TesteCitelApplicationTests {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Test
    void contextLoads() {
        assertThat(candidatoRepository).isNotNull();
    }

    @Test
    void testDatabaseConnection() {
        Candidato candidato = new Candidato();
        candidato.setNome("Test");
        candidato.setCpf("00000000000");
        candidatoRepository.save(candidato);

        assertThat(candidatoRepository.findById(candidato.getId())).isPresent();
    }
}
