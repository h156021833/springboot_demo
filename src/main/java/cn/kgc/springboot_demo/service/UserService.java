package cn.kgc.springboot_demo.service;

import cn.kgc.springboot_demo.pojo.User;

import java.util.List;

public interface UserService {

    User verify(String usercode, String userpassword);

    void register(User user);

    List<User> searchAll();
}
