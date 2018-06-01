package com.myspring.security.mysecuritydemo.service;

import com.myspring.security.mysecuritydemo.domain.SysPermission;
import com.myspring.security.mysecuritydemo.domain.SysRole;
import com.myspring.security.mysecuritydemo.domain.SysUser;
import com.myspring.security.mysecuritydemo.mapper.SysPermissionMapper;
import com.myspring.security.mysecuritydemo.mapper.SysRoleMapper;
import com.myspring.security.mysecuritydemo.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
  * @author lht
  * @doc   自定义用户信息
  * @date 2018/6/1
*/
@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    private Logger log= LoggerFactory.getLogger(this.getClass());
    @Override
    public UserDetails loadUserByUsername(String username) {

        SysUser user = sysUserMapper.findByUserName(username);

        if (user != null) {
            List<SysPermission> permissions = sysPermissionMapper.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            List<SysRole> roles = sysRoleMapper.findRoleByUserid(user.getId());
            for (SysRole role : roles) {
                if (!StringUtils.isEmpty(role.getName())) {
                    log.info("role.getName():"+role.getName());
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                    //此处将角色信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            for (SysPermission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }

    }
}
