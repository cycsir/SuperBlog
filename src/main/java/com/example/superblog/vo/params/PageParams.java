package com.example.superblog.vo.params;


import lombok.Data;

@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;
}
