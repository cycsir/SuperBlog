package com.example.superblog.service;

import com.example.superblog.entity.User;
import com.example.superblog.mapper.UserMapper;
import com.example.superblog.vo.LoginVO;
import com.example.superblog.vo.ResponseVO;
import com.example.superblog.enums.ResponseCode;
import org.springframework.stereotype.Service;

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
        User user = userMapper.findByUsername(loginVO.getUsername());
        if (user == null) {
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "用户不存在");
        }
        if (!user.getPassword().equals(loginVO.getPassword())) {
            return ResponseVO.buildFail(ResponseCode.ERROR.getCode(), "密码错误");
        }
        return ResponseVO.buildSuccess("登录成功");
    }
}
