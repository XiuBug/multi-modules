package com.mk.ssm.mapper.dao;

import com.mk.ssm.bean.entity.Color;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yaming on 17-4-18.
 */
public interface ColorDao {
    int addColor(Color color);
    int deleteColor(@Param("id") Long id, @Param("able") Integer able);
    Color findById(@Param("id") Long id, @Param("able") Integer able);
    int updateColor(Color color);
    List<Color> findColorByColumn(Color color);




}
