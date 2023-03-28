package com.goodstudy.base.exception;

/**
 * Description: goodStudy项目异常类
 *
 * @Author: Jack
 * Date: 2023/03/28 18:42
 * Version: 1.0
 */
public class GoodStudyException extends RuntimeException {

    /**
     * 异常信息
     */
    private String errMessage;

    public GoodStudyException() {
        super();
    }

    public GoodStudyException(String errMessage) { // 获取异常信息的方法
        super(errMessage);
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError) { // 抛出异常的静态方法，接收 CommonError 枚举类型参数
        throw new GoodStudyException(commonError.getErrMessage());
    }

    public static void cast(String errMessage) { // 抛出异常的静态方法，接收异常信息参数
        throw new GoodStudyException(errMessage);
    }
}
