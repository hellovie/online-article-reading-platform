package io.github.hellovie.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录验证服务
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 15:00
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 验证请求登录的用户信息
     *
     * @param username 用户名
     * @return CustomUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Todo：对接数据库，查询数据表中的用户信息进行比对。
        String databaseUsername = "root";
        // 明文密码：hellovie
        String databasePassword = "{bcrypt}$2a$10$GmDhlfd38VtSLiw3YD6gJujo/xhpiwLIEQgtx0eLGr1VFdZNfkJdm";
        List<GrantedAuthority> authorities = new ArrayList<>(1);
        // 角色字符串要全大写
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        CustomUser customUser = new CustomUser(databaseUsername, databasePassword, authorities);
        customUser.setEnabled(true);
        customUser.setLocked(true);
        customUser.setAccountNonExpired(true);
        customUser.setCredentialsNonExpired(true);
        return customUser;
    }
}
