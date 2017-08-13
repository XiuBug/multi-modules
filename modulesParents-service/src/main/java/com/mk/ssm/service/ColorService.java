package com.mk.ssm.service;

import com.mk.ssm.bean.entity.Color;

import java.util.List;

/**
 * Created by yaming on 17-4-18.
 */
public interface ColorService {
    /**
     * 添加颜色
     * @param color
     * @return
     */
    int addColor(Color color);

    /**
     * 删除颜色
     * @param id
     * @return
     */
    int deleteColor(Long id);

    /**
     * 修改颜色
     * @param color
     * @return
     */
    int updateColor(Color color);

    /**
     * 查询颜色
     * @param color
     * @return
     */
    List<Color> findColors(Color color);

    /**
     * 根据id查询颜色
     * @param id
     * @return
     */
    Color findColorById(Long id);
}
