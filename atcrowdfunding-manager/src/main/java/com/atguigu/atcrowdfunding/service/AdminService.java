package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TAdmin;

import java.util.List;


public interface AdminService {
    //用户登录的方法
    public TAdmin login(String loginacct,String userpswd);
    //分页查询
    public List<TAdmin> listPage(String keyWord);
    //添加用户的方法
    void saveAdmin(TAdmin admin);
    //获取用户的id
    TAdmin getAdminById(Integer id);
    //修改用户的方法
    void updateAdmin(TAdmin admin);
    //单项删除用户的方法
    void deleteAdminById(Integer id);
    //批量删除用户的方法
    void deleteBathByIds(List<Integer> listId);

}
