package cn.kgc.springboot_demo.controller;

import cn.kgc.springboot_demo.pojo.User;
import cn.kgc.springboot_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate redisTemplate;

    @GetMapping("/loginPage")
    public String loginPage() {
        User login = (User) redisTemplate.opsForValue().get("loginUser");
        if (login != null){
            redisTemplate.expire("loginUser",600, TimeUnit.SECONDS);
            return "redirect:/userList";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(String usercode, String userpassword, Model model) {
        User user = null;
        try {
            user = userService.verify(usercode, userpassword);
        } catch (Exception e) {
            String msg = "登录失败:" + e.getMessage();
            model.addAttribute("msg", msg);
        }
        if (user != null) {
            redisTemplate.opsForValue().set("loginUser",user);
            redisTemplate.expire("loginUser",600, TimeUnit.SECONDS);
            return "redirect:/userList";
        } else {
            return "login";
        }
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        boolean flag = false;
        try {
            userService.register(user);
            flag = true;
        } catch (Exception e) {
            String msg = "注册失败:" + e.getMessage();
            model.addAttribute("msg", msg);
        }
        if (flag) {
            List<User> list = (List<User>) redisTemplate.opsForValue().get("userList");
            if (list != null){
                list = userService.searchAll();
                redisTemplate.opsForValue().set("userList",list);
            }
            return "redirect:/loginPage";
        } else {
            return "register";
        }
    }

    @GetMapping("/userList")
    public String userList(Model model) {
        User user = (User) redisTemplate.opsForValue().get("loginUser");
        if (user == null){
            return "redirect:/loginPage";
        }else {
            redisTemplate.expire("loginUser",600, TimeUnit.SECONDS);
            List<User> list = (List<User>) redisTemplate.opsForValue().get("userList");
            if (list == null){
                list = userService.searchAll();
                redisTemplate.opsForValue().set("userList",list);
            }
            model.addAttribute("userList", list);
            return "userList";
        }
    }
}
