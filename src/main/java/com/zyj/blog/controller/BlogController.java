package com.zyj.blog.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blogs")  // 标记在类上的作用？？
public class BlogController {

    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @GetMapping
    public String listBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                            @RequestParam(value = "keyWord", required = false, defaultValue = "") String keyWord) {
        logger.info("order: " + order + ";keyWord: " + keyWord);
        return "redirect:/index?order=" + order + "&keyWord=" + keyWord;
    }
}
