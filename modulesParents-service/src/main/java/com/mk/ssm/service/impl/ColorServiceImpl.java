package com.mk.ssm.service.impl;

import com.mk.ssm.mapper.dao.ColorDao;
import com.mk.ssm.bean.entity.Color;
import com.mk.ssm.service.ColorService;
import com.mk.ssm.common.utils.Constants;
import com.mk.ssm.common.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yaming on 17-4-18.
 */
@Service
public class ColorServiceImpl implements ColorService{
    @Autowired
    private ColorDao colorDao;
    @Override
    public int addColor(Color color) {
        color.setCreateAt(TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_DATE_S));
        color.setAble(Constants.ABLE_CONFIG.DEFAULT_ABLE);
        String title=color.getTitle();
        Integer red=color.getRed();
        Integer green=color.getGreen();
        Integer blue=color.getBlue();
        if(title !=null && red!=null && green!=null && blue!=null){
            return colorDao.addColor(color);
        }else {
            return Constants.ABLE_CONFIG.NUM;
        }

    }

    @Override
    public int deleteColor(Long id) {
        return colorDao.deleteColor(id,Constants.ABLE_CONFIG.UNABLE);
    }

    @Override
    public int updateColor(Color color) {
        Color color1=colorDao.findById(color.getId(),Constants.ABLE_CONFIG.DEFAULT_ABLE);
        if(color1!=null){
            return colorDao.updateColor(color);
        }else {
            return Constants.ABLE_CONFIG.NUM;
        }
    }

    @Override
    public List<Color> findColors(Color color) {
        color.setAble(Constants.ABLE_CONFIG.DEFAULT_ABLE);
        if(color.getPage()!=null&&color.getPagesize()!=null){
            color.setPages((long) ((color.getPage()-Constants.PAGE_CONFIG.PageNum)*color.getPagesize()));
        }
        List<Color> list=colorDao.findColorByColumn(color);
        if(list !=null && list.size()!=0){
           //防止报空指针
            Color color1=new Color();
            color1.setAble(Constants.ABLE_CONFIG.DEFAULT_ABLE);
            //查出总页数
            Long total= Long.valueOf(colorDao.findColorByColumn(color1).size());
            for(Color c:list){
                c.setPage(color.getPage());
                c.setPagesize(color.getPagesize());
                c.setTotalNum(total);
            }
            return list;
        }else {
            return list;
        }
    }

    @Override
    public Color findColorById(Long id) {
        return colorDao.findById(id,Constants.ABLE_CONFIG.DEFAULT_ABLE);
    }
}
