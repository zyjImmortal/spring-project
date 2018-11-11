package com.zyj.blog.controller;

import java.util.ArrayList;
import java.util.List;

import com.zyj.blog.vo.Menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @GetMapping
    public ModelAndView listUsers(Model model) {
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu("用户管理", "/users"));
        model.addAttribute("list", menus);
        return new ModelAndView("/admins/index", "model", model);
    }
}
