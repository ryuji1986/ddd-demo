package org.ryuji.ddd.demo.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: showyou-irobot
 * @description: 表公共字段
 * @author: LiuXi
 * @create: 2020-11-19 17:18
 */
@Data
public class CommonModel<T extends CommonModel> extends Model {

    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

//    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
//    @TableLogic
//    @TableField(fill = FieldFill.INSERT)
//    protected Integer delFlag;

    @ApiModelProperty(value = "数据创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime gmtCreated;

    @ApiModelProperty(value = "数据修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    protected LocalDateTime gmtModified;
}