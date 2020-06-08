package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private TRoleMapper tRoleMapper;

    //获取角色和分页的方法
    public List<TRole> listRolePage(String keyWord) {
        TRoleExample example=new TRoleExample();
        if(StringUtil.isNotEmpty(keyWord)){
            TRoleExample.Criteria criteria=   example.createCriteria();
            criteria.andNameLike("%"+keyWord+"%");
        }
        return tRoleMapper.selectByExample(example);
    }
    //添加角色的方法
    public int saveRole(TRole role) {
     return  tRoleMapper.insertSelective(role);


    }
    //删除角色的方法
    public int deleteBath(List<Integer> idInts) {
        TRoleExample example = new TRoleExample();
        TRoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(idInts);
       return tRoleMapper.deleteByExample(example);
    }
}

