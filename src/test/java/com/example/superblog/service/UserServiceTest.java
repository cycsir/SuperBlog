package com.example.superblog.service;

import com.example.superblog.enums.ResponseCode;
import com.example.superblog.vo.LoginVO;
import com.example.superblog.vo.ResponseVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    @Transactional
    public void testLogin() {
        LoginVO loginVO = new LoginVO();
        loginVO.setUsername("cyc");
        loginVO.setPassword("123456");
        ResponseVO responseVO = userService.login(loginVO);
        System.out.println(responseVO);
        assertEquals(ResponseCode.OK.getCode(), responseVO.getCode());
    }

}
