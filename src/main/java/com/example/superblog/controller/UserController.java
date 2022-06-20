package com.example.superblog.controller;

import com.example.superblog.service.UserService;
import com.example.superblog.vo.LoginVO;
import com.example.superblog.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author 17804
 */

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseVO login(@RequestBody LoginVO loginVO) {
        return userService.login(loginVO);
    }
}
