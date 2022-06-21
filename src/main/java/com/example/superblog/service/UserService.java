package com.example.superblog.service;

import com.example.superblog.entity.User;
import com.example.superblog.mapper.UserMapper;
import com.example.superblog.vo.LoginVO;
import com.example.superblog.vo.ResponseVO;
import com.example.superblog.enums.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cyc
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ResponseVO login(LoginVO loginVO) {
        List<User> users = userMapper.findByUsername(loginVO.getUsername());
        if (users.isEmpty()) {
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "用户名不存在");
        }
        User user = users.get(0);
        if (user == null) {
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "用户不存在");
        }
        if (!user.getPassword().equals(loginVO.getPassword())) {
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "密码错误");
        }
        return ResponseVO.buildSuccess("登录成功");
    }
}
