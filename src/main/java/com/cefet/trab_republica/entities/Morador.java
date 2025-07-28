package com.cefet.trab_republica.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_morador")
public class Morador implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(name = "cpf", unique = true, nullable = false, length = 14)
	private String cpf;

	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	@Column(nullable = false, length = 15)
	private String celular;

	@Column(unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "contatos_familia", nullable = false, length = 255)
	private String contatosFamilia;

	@Column(nullable = false, length = 255)
	private String senha;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	public Morador() {
	}

	public Morador(String nome, String cpf, LocalDate dataNascimento, String celular, String email,
			String contatosFamilia, String senha, UserRole role) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.celular = celular;
		this.email = email;
		this.contatosFamilia = contatosFamilia;
		this.senha = senha;
		this.role = role;
	}

	// Getters e Setters para todos os campos...
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	// --- UserDetails ---
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        // role == USER
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Morador that)) return false;
        return Objects.equals(this.id, that.id);
    }
}
