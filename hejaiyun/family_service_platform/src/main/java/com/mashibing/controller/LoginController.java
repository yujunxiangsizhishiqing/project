package com.mashibing.controller;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.bean.TblUserRecord;
import com.mashibing.returnJson.Permission;
import com.mashibing.returnJson.Permissions;
import com.mashibing.returnJson.ReturnObject;
import com.mashibing.returnJson.UserInfo;
import com.mashibing.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @RestController 等于 @Controller + @ResponseBody
 * */
@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*",methods = {},allowCredentials = "true")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/auth/2step-code")
    public Boolean test(){
        System.out.println("前端框架自带的一个验证规则，写不写无所谓");
        return true;
    }

    @RequestMapping("/auth/login")
    public String login(@RequestParam("username") String username , @RequestParam("password") String password, HttpSession httpSession){
        System.out.println("login");
        //System.out.println(username+" "+password);
        TblUserRecord tblUserRecord = loginService.login(username,password);
        tblUserRecord.setToken(tblUserRecord.getUserName());
        //将用户数据写入到session中
        httpSession.setAttribute("userRecord",tblUserRecord);
        //System.out.println(tblUserRecord+"\n"+"-----------------------");
        ReturnObject returnObject = new ReturnObject(tblUserRecord);
        return JSONObject.toJSONString(returnObject);
    }

    @RequestMapping("/user/info")
    public String getInfo(HttpSession session){
        System.out.println("get session info:"+"\n"+session.getAttribute("userRecord"));

        TblUserRecord tblUserRecord = (TblUserRecord) session.getAttribute("userRecord");
        //获取模块信息
        String[] split = tblUserRecord.getTblRole().getRolePrivileges().split("-");
        //创建权限集合对象
        Permissions permissions = new Permissions();
        //向权限集合对象中添加具体的权限
        List<Permission> permissionList = new ArrayList<>();
        for (String s : split) {
            permissionList.add(new Permission(s));
        }
        permissions.setPermissions(permissionList);
        //设置返回值的result
        UserInfo userInfo = new UserInfo(tblUserRecord.getUserName(),permissions);
        return JSONObject.toJSONString(new ReturnObject(userInfo));
    }

    @RequestMapping("/auth/logout")
    public void logOut(HttpSession session){
        System.out.println("login out");
        session.invalidate();
    }
}
