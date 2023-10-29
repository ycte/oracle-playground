package com.ycte.service;

import com.ycte.dao.UserDao;
import com.ycte.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    UserDao userDao;

    public void addUser(String userId, String userName, String password, String college, String name,
                        String tel, String address) {
        userDao.insert(new UserPojo(0, userId, userName, password, college, name, tel, address));
    }

}

