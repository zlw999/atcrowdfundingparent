package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TMenu;

import java.util.List;

public interface MenuService {
    //获取所有左侧菜单的方法
    public List<TMenu> menuAll();
}
