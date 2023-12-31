package com.example.crud.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Utiliza a interface UserDetails do Spring Security que obriga a implementação de alguns métodos
@Table(name="users")
@Entity(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String login;

    private String password;

    private UserRole role;

    public User (String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Faz o DE PARA entre a tabela e as autorização do Spring Security
        // Caso o usuário seja admin retorna as autorizações de ADMIN e USER normal
        if (this.role == UserRole.ADMIN){
            return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"),
                            new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Caso não seja admin retorna somente a autorização de USER normal
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
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
}
