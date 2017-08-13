package com.mk.ssm.api.web;

import com.mk.ssm.bean.dto.BaseResult;
import com.mk.ssm.bean.entity.Color;
import com.mk.ssm.service.ColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yaming on 17-4-19.
 */
@Api(value = "ColorController",description = "颜色管理",consumes = "application/json")
@RestController
@RequestMapping("/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @ApiOperation(value = "添加颜色",notes = "传入颜色对象添加颜色",response = BaseResult.class)
    @ApiImplicitParam(name = "颜色",value="color",required = true,dataType = "BaseResult<Color>",paramType ="Color" )
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public BaseResult<Color> createColor(@RequestBody(required = false) Color color){
    BaseResult<Color> result=null;
    if(color==null){
        result=new BaseResult<Color>(false,"颜色信息不能为空");
    }else {
        int res=colorService.addColor(color);
        if(res>0){
            result=new BaseResult<Color>(true,color);
        }else if(res<0){
            result=new BaseResult<Color>(false,"颜色信息不完整");
        } else {
            result=new BaseResult<Color>(false,"添加失败");
        }
    }
    return result;
    }

    @ApiOperation(value = "删除颜色",notes = "传入删除颜色的Id",response = BaseResult.class)
    @ApiImplicitParam(name = "颜色",value = "color",required = true,dataType ="BaseResult<Color>",paramType = "java.long.Long")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public BaseResult<Color> deleteColor(@RequestParam("id") Long id){
          BaseResult<Color> result=null;
          int res=colorService.deleteColor(id);
          if(res>0){
              result=new BaseResult<Color>(true,"删除成功");
          }else {
              result=new BaseResult<Color>(false,"删除失败");
          }
          return result;
    }

    @ApiOperation(value = "修改颜色",notes = "传入修改的对象",response = BaseResult.class)
    @ApiImplicitParam(name = "颜色",value = "color",required = true,dataType = "BaseResult<Color>",paramType = "Color")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public BaseResult<Color> updateColor(@RequestBody(required = false) Color color){
        BaseResult<Color> result=null;
        if(color==null || color.getId()==null){
            result=new BaseResult<Color>(false,"对象不能为空");
        }else {
            int res=colorService.updateColor(color);
            if(res>0){
                Color color1=colorService.findColorById(color.getId());
                result=new BaseResult<Color>(true,color1);
            }else if(res<0){
                result=new BaseResult<Color>(false,"没有这个颜色,无法修改");
            }else {
                result=new BaseResult<Color>(false,"修改失败");
            }
        }
        return result;
    }

    @ApiOperation(value = "查询颜色",notes = "传入查询的条件",response = BaseResult.class)
    @ApiImplicitParam(name = "颜色",value = "color",required = false,dataType = "BaseResult<List<Color>>",paramType = "Color")
    @RequestMapping(value ="/find",method =RequestMethod.GET)
    public BaseResult<List<Color>> findColors(Color color){
        BaseResult<List<Color>> result=null;
        List<Color> list=colorService.findColors(color);

        if(list!=null){
            result=new BaseResult<List<Color>>(true,list);
        }else {
            result=new BaseResult<List<Color>>(false,"没有结果");
        }
        return result;
    }


}
