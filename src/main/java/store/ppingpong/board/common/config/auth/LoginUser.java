package store.ppingpong.board.common.config.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import store.ppingpong.board.user.domain.User;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class LoginUser implements UserDetails {

    private final User user;

    public LoginUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> user.getUserInfo().getUserType().name());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getLoginInfo().getEncodePassword();
    }

    @Override
    public String getUsername() {
        return user.getUserInfo().getNickname();
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

    public Long getUserId() {return user.getId();}
}
