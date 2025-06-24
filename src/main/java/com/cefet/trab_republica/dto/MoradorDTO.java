package com.cefet.trab_republica.dto;

import java.time.LocalDate;

public class MoradorDTO {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String celular;
    private String email;
    private String contatosFamilia;

    public MoradorDTO() {}

    public MoradorDTO(Long id, String nome, String cpf, LocalDate dataNascimento,
                      String celular, String email, String contatosFamilia) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.celular = celular;
        this.email = email;
        this.contatosFamilia = contatosFamilia;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContatosFamilia() {
		return contatosFamilia;
	}

	public void setContatosFamilia(String contatosFamilia) {
		this.contatosFamilia = contatosFamilia;
	}

}
