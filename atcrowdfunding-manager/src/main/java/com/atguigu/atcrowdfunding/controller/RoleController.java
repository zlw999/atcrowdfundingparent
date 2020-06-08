package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/role/index")
    public String index() {
        return "role/index";
    }

    @PreAuthorize("hasRole('大师')")
    @ResponseBody    //是将数据格式化成json
    @RequestMapping("/role/loadData")
    public PageInfo<TRole> loadData(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize,
            @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord) {
        PageHelper.startPage(pageNum, pageSize);

        List<TRole> listRole = roleService.listRolePage(keyWord);

        PageInfo<TRole> pageInfo = new PageInfo<TRole>(listRole, 5);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping("/role/save")
    public String save(TRole role){
        int row= roleService.saveRole(role);
        if(row==1){
            return "yes";
        }
        return "no";
    }

    @ResponseBody
    @RequestMapping("/role/deleteBath")
    public String deleteBath(String ids){
        String[] idstrs=ids.split(",");
        List<Integer> idInts=new ArrayList<Integer>();
        for (String idstr : idstrs) {
            idInts.add(Integer.parseInt(idstr));
        }
        int row= roleService.deleteBath(idInts);
        if(row>=1){
            return "yes";
        }

        return "no";
    }
}