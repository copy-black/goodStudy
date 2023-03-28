package com.goodstudy.content.model.dto;

import com.goodstudy.base.exception.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Description: 新增课程基本信息dto
 *
 * @Author: Jack
 * Date: 2023/03/28 11:35
 * Version: 1.0
 */
@Data
@ApiModel(value = "AddCourseDto", description = "新增课程基本信息")
public class AddCourseDto {

    /**
     * 授课模型
     */
    @NotEmpty(groups = {ValidationGroups.Inster.class}, message = "授课模型不能为空")
    @NotEmpty(groups = {ValidationGroups.Update.class},message = "修改课程名称不能为空")
    @ApiModelProperty(value = "授课模型(普通, 录播，直播)", required = true)
    private String teachmode;

    /**
     * 课程名称
     */
    @NotEmpty(message = "课程名称不能为空")
    private String name;
    ;

    /**
     * 课程标签
     */
    @ApiModelProperty(value = "课程标签")
    private String tags;

    /**
     * 大分类
     */
    @NotEmpty(message = "大分类不能为空")
    @ApiModelProperty(value = "大分类", required = true)
    private String mt;

    /**
     * 小分类
     */
    @NotEmpty(message = "小分类不能为空")
    @ApiModelProperty(value = "小分类", required = true)
    private String st;

    /**
     * 课程等级
     */
    @NotEmpty(message = "课程等级不能为空")
    @ApiModelProperty(value = "课程等级", required = true)
    private String grade;

    /**
     * 课程介绍
     */
    @ApiModelProperty(value = "课程介绍")
    @Size(message = "课程描述内容过少", min = 10)
    private String description;

    /**
     * 适用人群
     */
    @NotEmpty(message = "适用人群不能为空")
    @ApiModelProperty(value = "适用人群", required = true)
    @Size(message = "适用人群内容过少", min = 10)
    private String users;
    ;

    /**
     * 课程图片
     */
    @ApiModelProperty(value = "课程图片", required = true)
    private String pic;

    /**
     * 收费规则
     */
    @NotEmpty(message = "收费规则不能为空")
    @ApiModelProperty(value = "收费规则, 对应数据字典", required = true)
    private String charge;

    /**
     * 课程价格
     */
    @ApiModelProperty(value = "课程价格")
    private Float price;

    /**
     * 课程原价
     */
    @ApiModelProperty(value = "课程原价")
    private Float originalPrice;

    /**
     * QQ
     */
    @ApiModelProperty(value = "QQ")
    private String qq;

    /**
     * 微信
     */
    @ApiModelProperty(value = "微信")
    private String wechat;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期")
    private String vaildDays;
}
