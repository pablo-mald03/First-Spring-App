package com.springcourse.expert.user.infrastructure.database.entity;

import com.springcourse.expert.user.domain.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String firstName;
    private String lastName;

    /*
     * SE LE IMPLEMENTA EL METODO QUE PERMITE RETORNAR LOS PERMISOS QUE TIENE EL USUARIO
     *
     * ESTOS METODOS DEBEN TENER LOGICA EN RELACION A LOS CAMPOS CUYA INFORMACION LA PROPORCIONA HIBERNATE
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /*
     * METODO QUE REPRESENTA EL NOMBRE DE USUARIO O ATRIBUTO UNICO QUE DISTINGUE AL USUARIO DE OTROS
     * */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
