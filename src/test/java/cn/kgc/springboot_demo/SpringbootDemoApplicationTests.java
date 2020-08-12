package cn.kgc.springboot_demo;

import cn.kgc.springboot_demo.mapper.UserMapper;
import cn.kgc.springboot_demo.pojo.User;
import cn.kgc.springboot_demo.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class)
public class SpringbootDemoApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        User user = userService.verify("admin", "1234567");
        Assert.assertNotNull(user);
    }

    @Test
    public void test02(){
        User user = new User();
        user.setUsercode("dx");
        user.setUserpassword("123");
        userService.register(user);
        User user1 = userMapper.selectByUsercode("dx");
        Assert.assertNotNull(user1);
    }

    @Test
    public void test03(){
        List<User> list = userService.searchAll();
        int size = list.size();
        boolean flag = size == 13 || size == 14;
        Assert.assertTrue(flag);
    }



}
