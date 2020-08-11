package cn.kgc.springboot_demo.service;

import cn.kgc.springboot_demo.mapper.UserMapper;
import cn.kgc.springboot_demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean verify(String usercode, String userpassword) {
        boolean flag;
        User user = userMapper.selectByUsercode(usercode);
        if (user == null)
            throw new RuntimeException(usercode+"不存在");
        else
            flag = user.getUserpassword().equals(userpassword);
        return flag;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void register(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public List<User> searchAll() {
        return userMapper.selectByExample(null);
    }
}
