package com.example.superblog.vo;

import com.example.superblog.enums.ResponseCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cycsir
 */
@Getter
@ToString
public class ResponseVO {
    private Integer code;
    private String message;

    public static ResponseVO buildSuccess(String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.code = ResponseCode.OK.getCode();
        responseVO.message = message;
        return responseVO;
    }

    public static ResponseVO buildFail(Integer code, String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.code = code;
        responseVO.message = message;
        return responseVO;
    }
}
