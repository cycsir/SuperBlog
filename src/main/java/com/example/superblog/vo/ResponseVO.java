package com.example.superblog.vo;

import com.example.superblog.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author cycsir
 */
@Getter
@ToString
@AllArgsConstructor
public class ResponseVO {

    private boolean success;
    private Integer code;
    private String message;

    private Object data;

    public ResponseVO() {

    }


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

    public static ResponseVO success(Object data){
        return new ResponseVO(true, 200, "success", data);
    }


    public static ResponseVO fail(int code, String msg){
        return new ResponseVO(false, code, msg, null);
    }
}
