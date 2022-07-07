package com.example.superblog.mapper;

import com.example.superblog.entity.BlogFile;
import org.springframework.data.repository.CrudRepository;

public interface FileMapper extends CrudRepository<BlogFile, Integer> {

}
