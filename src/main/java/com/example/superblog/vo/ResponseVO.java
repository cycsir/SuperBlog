package com.example.superblog.vo;

import com.example.superblog.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cycsir
 */
@Getter
@ToString
public class ResponseVO {
    private Integer code;
    private String message;

    private Object data;
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

    public static ResponseVO buildSuccess(String message, Object data) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.code = ResponseCode.OK.getCode();
        responseVO.message = message;
        responseVO.data = data;
        return responseVO;
    }
}
