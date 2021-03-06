package com.mk.ssm.api.web;

import com.mk.ssm.bean.dto.BaseResult;
import com.mk.ssm.bean.entity.User;
import com.mk.ssm.common.enums.ResultEnum;
import com.mk.ssm.common.exception.BizException;
import com.mk.ssm.service.RoleService;
import com.mk.ssm.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>Resource: yankee
 * <p>Date: 17-4-3
 * <p>Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    /**
     * 添加用户
     * @param user @valid验证（hibernate-validator）
     * @param result 验证结果，使用aop统一处理
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/user";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }


    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    private void setCommonData(Model model) {
        model.addAttribute("roleList", roleService.findAll());
    }

    /**
     * 优雅的异常分类：业务异常（捕获的XXService层抛出的业务异常）、非业务异常（系统异常）
     * @return json数据
     */
    @RequiresPermissions("user:view")
    @ResponseBody
    @RequestMapping("/findUsers")
    public BaseResult<List<User>> findUsers(String idOrUsername){
        BaseResult<List<User>> resp = null;
        try {
            List<User> list = userService.findUsers(idOrUsername);
            resp = new BaseResult<List<User>>(true, list);
        } catch(BizException e){
            resp = new BaseResult(false, e.getMessage());
        } catch(Exception e){
            resp = new BaseResult(false, ResultEnum.INNER_ERROR.getMsg());
        }
        return resp;
    }
}
