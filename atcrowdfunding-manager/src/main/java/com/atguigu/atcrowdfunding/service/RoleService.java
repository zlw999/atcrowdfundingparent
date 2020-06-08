package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TRole;

import java.util.List;

public interface RoleService {

    public List<TRole> listRolePage(String keyWord);

    public  int saveRole(TRole role);


    int deleteBath(List<Integer> idInts);

}
