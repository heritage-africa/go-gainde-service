package heritage.africa.go_gainde_service.security;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import heritage.africa.go_gainde_service.entity.Utilisateur;
public class CustomUserDetails implements UserDetails {


    private Utilisateur user;

    public CustomUserDetails(Utilisateur user) {
        this.user = user;
    }

    /**
     * Returns a Collection containing one GrantedAuthority with the role
     * name prefixed with "ROLE_".
     * @return a Collection of one GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public Utilisateur getUser() {
        return user;
    }
    
}
