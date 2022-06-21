package com.example.superblog.mapper;

import com.example.superblog.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author cycsir
 */
public interface UserMapper extends CrudRepository<User, Integer> {

    /**
     * 通过用户名查询用户
     */
    List<User> findByUsername(String name);
}
