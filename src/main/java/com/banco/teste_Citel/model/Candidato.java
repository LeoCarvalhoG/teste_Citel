package com.banco.teste_Citel.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@javax.persistence.Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "O CPF não pode ser nulo")
    @Pattern(regexp = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)", message = "O CPF deve ter 14 dígitos")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @NotNull(message = "O RG não pode ser nulo")
    @Size(min = 7, max = 20, message = "O RG deve ter entre 7 e 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String rg;

    @NotNull(message = "A data de nascimento não pode ser nula")
    @Column(nullable = false)
    @JsonProperty("data_nasc")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataNasc;

    @NotNull(message = "O sexo não pode ser nulo")
    @Size(min = 1, max = 10, message = "O sexo deve ter entre 1 e 10 caracteres")
    @Column(nullable = false, length = 10)
    private String sexo;

    @NotNull(message = "O nome da mãe não pode ser nulo")
    @Size(min = 2, max = 100, message = "O nome da mãe deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String mae;

    @NotNull(message = "O nome do pai não pode ser nulo")
    @Size(min = 2, max = 100, message = "O nome do pai deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String pai;

    //@Email(message = "O email deve ser válido")
    @Column(nullable = false, length = 255)
    private String email;

    @NotNull(message = "O CEP não pode ser nulo")
    @Pattern(regexp = "(^\\d{5}-\\d{3})", message = "O CEP deve ter 8 dígitos")
    @Column(nullable = false, length = 10)
    private String cep;

    @NotNull(message = "O endereço não pode ser nulo")
    @Size(min = 5, max = 200, message = "O endereço deve ter entre 5 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String endereco;

    @NotNull(message = "O número não pode ser nulo")
    @Column(nullable = false)
    private int numero;

    @NotNull(message = "O bairro não pode ser nulo")
    @Size(min = 2, max = 100, message = "O bairro deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotNull(message = "A cidade não pode ser nula")
    @Size(min = 2, max = 100, message = "A cidade deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String cidade;

    @NotNull(message = "O estado não pode ser nulo")
    @Size(min = 2, max = 20, message = "O estado deve ter entre 2 e 20 caracteres")
    @Column(nullable = false, length = 20)
    private String estado;

    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})", message = "O telefone fixo deve ter 10 dígitos")
    @Column(length = 14)
    @JsonProperty("telefone_fixo")
    private String telefoneFixo;

    @Pattern(regexp = "(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})", message = "O celular deve ter 11 dígitos")
    @Column(length = 15)
    private String celular;

    @NotNull(message = "A altura não pode ser nula")
    @Column(nullable = false)
    private double altura;

    @NotNull(message = "O peso não pode ser nulo")
    @Column(nullable = false)
    private double peso;

    @NotNull(message = "O tipo sanguíneo não pode ser nulo")
    @Size(min = 2, max = 3, message = "O tipo sanguíneo deve ter entre 2 e 3 caracteres")
    @Column(nullable = false, length = 3)
    @JsonProperty("tipo_sanguineo")
    private String tipoSanguineo;

    public Candidato() {
    }

    public Candidato(String nome, String cpf, String rg, LocalDate dataNasc, String sexo, String mae,
                     String pai, String email, String cep, String endereco, int numero, String bairro,
                     String cidade, String estado, String telefoneFixo, String celular, double altura,
                     double peso, String tipoSanguineo) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.mae = mae;
        this.pai = pai;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefoneFixo = telefoneFixo;
        this.celular = celular;
        this.altura = altura;
        this.peso = peso;
        this.tipoSanguineo = tipoSanguineo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
    
    @JsonProperty("data_nasc")
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    
    @JsonProperty("data_nasc")
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @JsonProperty("telefone_fixo")
    public String getTelefoneFixo() {
        return telefoneFixo;
    }
    
    @JsonProperty("telefone_fixo")
    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getIdade() {
        return LocalDate.now().getYear() - this.dataNasc.getYear();
    }
    
    @JsonProperty("tipo_sanguineo")
    public String getTipoSanguineo() {
        return tipoSanguineo;
    }
    
    @JsonProperty("tipo_sanguineo")
    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }
}

