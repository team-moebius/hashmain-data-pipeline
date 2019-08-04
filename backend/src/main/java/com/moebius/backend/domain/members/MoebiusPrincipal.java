package com.moebius.backend.domain.members;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class MoebiusPrincipal implements UserDetails, CredentialsContainer {

    @Getter(AccessLevel.NONE)
    private final Member member;

    private Map<String, Object> attributes;

    @Override
    public void eraseCredentials() {
        member.setPassword(null);
        attributes = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getRoles();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getId().toHexString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return member.isActive();
    }
}
