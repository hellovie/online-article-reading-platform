package io.github.hellovie.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 登录账号所需要的用户信息域. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
public class CustomUser implements UserDetails {
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** true：账号未过期 */
    private boolean accountNonExpired;
    /** true：账号未锁定 */
    private boolean locked;
    /** true：用户凭据未过期 */
    private boolean credentialsNonExpired;
    /** true：账号启用 */
    private boolean enabled;
    /** 用户角色 */
    private List<GrantedAuthority> authorities;

    public CustomUser(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
