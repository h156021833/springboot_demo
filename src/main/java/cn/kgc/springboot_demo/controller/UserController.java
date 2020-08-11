package cn.kgc.springboot_demo.controller;

import cn.kgc.springboot_demo.pojo.User;
import cn.kgc.springboot_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String usercode, String userpassword, Model model){
        boolean flag = false;
        try {
             flag = userService.verify(usercode, userpassword);
        } catch (Exception e){
            String msg = "登录失败:" + e.getMessage();
            model.addAttribute("msg",msg);
        }
        if (flag){
            return "redirect:/userList";
        } else {
            return "login";
        }
    }

    @GetMapping("/registerPage")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String register(User user,Model model){
        boolean flag = false;
        try {
            userService.register(user);
            flag = true;
        } catch (Exception e){
            String msg = "注册失败:" + e.getMessage();
            model.addAttribute("msg",msg);
        }
        if (flag){
            return "redirect:/loginPage";
        } else {
            return "register";
        }
    }

    @GetMapping("/userList")
    public String userList(Model model){
        List<User> list = userService.searchAll();
        model.addAttribute("userList",list);
        return "userList";
    }
}
