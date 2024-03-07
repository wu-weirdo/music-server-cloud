package com.whf.music.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * mybatisplus 自动填充字段
 *
 * @author whf
 * @date 2024/3/5
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_TIME = "createTime";
    private final static String UPDATE_TIME = "updateTime";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        // 创建时间
        setFieldValByName(CREATE_TIME, date, metaObject);
        // 更新时间
        setFieldValByName(UPDATE_TIME, date, metaObject);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间
        setFieldValByName(UPDATE_TIME, new Date(), metaObject);
    }
}
