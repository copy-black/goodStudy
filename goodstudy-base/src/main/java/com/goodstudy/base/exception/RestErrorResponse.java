package com.goodstudy.base.exception;

import java.io.Serializable;

/**
 * Description: 错误响应参数包装
 *
 * @Author: Jack
 * Date: 2023/03/28 18:51
 * Version: 1.0
 */
public class RestErrorResponse implements Serializable {

    /**
     * 错误信息
     */
    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String message) {
        this.errMessage = errMessage;
    }
}
