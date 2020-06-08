package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.AdminService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.DateUtil;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TAdminMapper tAdminMapper;
    public TAdmin login(String loginacct, String userpswd) {
        //创建判断类对象
        TAdminExample example = new TAdminExample();
        TAdminExample.Criteria criteria = example.createCriteria();
        //判断用户名或密码是否与数据库一致
        criteria.andLoginacctEqualTo(loginacct);
        //加密文
        String md5 = MD5Util.digest(userpswd);
        criteria.andUserpswdEqualTo(md5);
        //调用查询方法
        List<TAdmin> tAdmins = tAdminMapper.selectByExample(example);
        // 判断是否有该用户
        if(tAdmins==null || tAdmins.size()==0){
            throw  new RuntimeException("登录失败，请检查账号或密码");
        }
        TAdmin tAdmin = tAdmins.get(0);

        return tAdmin;
    }

    public List<TAdmin> listPage(String keyWord) {
        TAdminExample example = new TAdminExample();
        if(StringUtil.isNotEmpty(keyWord)){
            TAdminExample.Criteria criteria = example.createCriteria();
           criteria.andLoginacctLike("%"+keyWord+"%");
            TAdminExample.Criteria criteria2= example.createCriteria();
            criteria2.andUsernameLike("%"+keyWord+"%");
            TAdminExample.Criteria criteria3= example.createCriteria();
            criteria3.andEmailLike("%"+keyWord+"%");
            example.or(criteria);
            example.or(criteria2);
            example.or(criteria3);

            //倒序显示用户，显示新增的用户
          //  example.setOrderByClause("createtime desc");

        }
        List<TAdmin> tAdmins = tAdminMapper.selectByExample(example);
        return tAdmins;
    }



    public void saveAdmin(TAdmin admin) {

        //指定创建用户的时间
        admin.setCreatetime(DateUtil.getFormatTime());
        //指定默认密码
       /* admin.setUserpswd(MD5Util.digest(Const.DEFALUT_PASSWORD));*/
            admin.setUserpswd(new BCryptPasswordEncoder().encode(Const.DEFALUT_PASSWORD));
        tAdminMapper.insertSelective(admin);
    }

    public TAdmin getAdminById(Integer id) {
        TAdmin tAdmin = tAdminMapper.selectByPrimaryKey(id);

        return  tAdmin;
    }

    public void updateAdmin(TAdmin admin) {
        tAdminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteAdminById(Integer id) {
        tAdminMapper.deleteByPrimaryKey(id);
    }

    public void deleteBathByIds(List<Integer> listId) {
        tAdminMapper.deleteBathByIds(listId);
    }
}
