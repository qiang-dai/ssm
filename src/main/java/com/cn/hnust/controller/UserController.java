package com.cn.hnust.controller;

import com.cn.hnust.pojo.User;
import com.cn.hnust.service.IThirdPartyStickerInfoService;
import com.cn.hnust.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sticker")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IThirdPartyStickerInfoService thirdPartyStickerInfoService;

    @RequestMapping("/crawl")
    public String craw(HttpServletRequest request, Model model) {
        thirdPartyStickerInfoService.processTask();
        return "showUser";
    }

    @RequestMapping("/local")
    public String local(HttpServletRequest request, Model model) {
        thirdPartyStickerInfoService.localTask();
        return "showUser";
    }

    @RequestMapping("/showUser")
    public String toIndex(HttpServletRequest request,Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        model.addAttribute("user", user);
        return "showUser";
    }
}
