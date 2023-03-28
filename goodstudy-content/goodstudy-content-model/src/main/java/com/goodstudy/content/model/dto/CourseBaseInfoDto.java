package com.goodstudy.content.model.dto;

import com.goodstudy.content.model.po.CourseBase;
import lombok.Data;

/**
 * Description: 课程基本信息dto
 *
 * @Author: Jack
 * Date: 2023/03/28 11:35
 * Version: 1.0
 */
@Data
public class CourseBaseInfoDto extends CourseBase {

    /**
     * 收费规则，对应数据字典
     */
    private String charge;

    /**
     * 价格
     */
    private Float price;

    /**
     * 原价
     */
    private Float originalPrice;

    /**
     * 咨询qq
     */
    private String qq;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 电话
     */
    private String phone;

    /**
     * 有效期天数
     */
    private Integer validDays;

    /**
     * 大分类名称
     */
    private String mtName;

    /**
     * 小分类名称
     */
    private String stName;
}
