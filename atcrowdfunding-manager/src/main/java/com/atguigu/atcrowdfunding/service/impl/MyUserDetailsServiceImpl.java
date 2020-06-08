package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TAdminMapper tAdminMapper;
    @Autowired
    private TRoleMapper tRoleMapper;
    @Autowired
    private TPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TAdminExample example=new TAdminExample();
        TAdminExample.Criteria criteria= example.createCriteria();
        criteria.andLoginacctEqualTo(username);
        List<TAdmin> listAdmin= tAdminMapper.selectByExample(example);
        TAdmin admin= listAdmin.get(0);
        Set<GrantedAuthority> authorities =new HashSet<GrantedAuthority>();

        //查询用户对应的角色信息
        List<TRole> listRole= tRoleMapper.listRole(admin.getId());

        //角色对应的权限信息
        List<TPermission> listPermission=  permissionMapper.listPermission(admin.getId());


        //将权限和角色存放到 authorities集合中
        for (TRole tRole : listRole) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+tRole.getName()));
        }
        for (TPermission tPermission : listPermission) {
            authorities.add(new SimpleGrantedAuthority(tPermission.getName()));
        }


        return new User(admin.getLoginacct().toString(),admin.getUserpswd().toString(),authorities);
    }
}
