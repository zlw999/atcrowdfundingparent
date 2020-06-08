package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MenuService menuService;

    //将菜单信息带到main.jap页面中
    //避免重复登录的方法
    @RequestMapping("/main")
    public String main(HttpSession session) {
        List<TMenu> listParent = menuService.menuAll();
        session.setAttribute("listParent", listParent);
        return "main";
    }

   /* //处理登录的方法
    @RequestMapping(value = "/login")
    public String login(String loginacct, String userpswd, HttpSession session) {
        try {
            TAdmin admin = adminService.login(loginacct, userpswd);
            session.setAttribute("admin", admin);
        } catch (Exception e) {
            session.setAttribute("err", e.getMessage());
            return "forward:/WEB-INF/views/login.jsp";
        }
        return "redirect:/main";
    }*/

    //处理用户分页的方法
    @PreAuthorize("hasAnyRole('学徒','宗师') and hasAuthority('putong:luohan')")
    @RequestMapping(value = "/admin/index")
    public String index(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "pageSize", required = false, defaultValue = "2") Integer pageSize,
                        @RequestParam(name = "keyWord", required = false, defaultValue = "") String keyWord, Model model) {
        //分页工具的使用
        PageHelper.startPage(pageNum, pageSize);

        List<TAdmin> tAdmins = adminService.listPage(keyWord);
        PageInfo pageInfo = new PageInfo(tAdmins, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/index";
    }

    //处理用户注销的方法
  /*  @RequestMapping("/loginOut")
    public String loginOut(HttpSession session) {
        if (session != null) {

            session.invalidate();
        }

        return "login";
    }*/

    //跳转到新增页面的方法
    @RequestMapping("/admin/toAdd")
    public String toAdd() {

        return "admin/add";
    }
    //添加用户的方法
    @RequestMapping("/admin/add")
    public String add(TAdmin admin){
        adminService.saveAdmin(admin);

        //跳转到新增到用户的那一页
        return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
}

//根据id获取用户信息
    //跳转到修改页面
@RequestMapping("/admin/toUpdate")

    public String toUpdate(Integer id, Model model,Integer pageNum){

    TAdmin admin = adminService.getAdminById(id);
    model.addAttribute("admin",admin);
    return "admin/update";

}
//修改用户的方法
@RequestMapping("/admin/update")
    public  String update(TAdmin admin,Integer pageNum){
        adminService.updateAdmin(admin);

        return "redirect:/admin/index?pageNum="+pageNum;
}

    //单项删除用户的方法

@RequestMapping("/admin/delete")
    public  String delete(Integer id){
        adminService.deleteAdminById(id);

        return "redirect:/admin/index";

}

   //批量删除
    @RequestMapping("/admin/deleteBath")
    public  String deleteBath(String ids){
        String[] idstrs= ids.split(",");
        List<Integer> listId =new ArrayList<Integer>();
        for (String idStr : idstrs) {
            Integer id=Integer.parseInt(idStr);
            listId.add(id);
        }
        adminService.deleteBathByIds(listId);


        return "redirect:/admin/index";
    }

}
