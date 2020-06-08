package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {
   @Autowired
    private TMenuMapper tMenuMapper;
    public List<TMenu> menuAll() {
        //保存所有菜单信息
        List<TMenu> tMenus = tMenuMapper.selectByExample(null);
        //保存父菜单的集合
       List<TMenu> listParent = new ArrayList<TMenu>();
        for (TMenu tMenu : tMenus) {
            //所有父节点
            if(tMenu.getPid()==0){
                listParent.add(tMenu);
            }
        }
        //为所有的父节点关联对应的子节点
        for (TMenu tMenu : tMenus) {
            if(tMenu.getPid()!=0){
                for (TMenu parent : listParent) {
                    //判断子节点是否在父节点内
                    if(tMenu.getPid()==parent.getId()){
                        //将自己饿点放到父节点中
                        parent.getListChild().add(tMenu);
                    }
                }
            }

        }
        return listParent;
    }
}
