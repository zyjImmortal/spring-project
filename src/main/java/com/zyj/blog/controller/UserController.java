package com.zyj.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.zyj.blog.domain.Authority;
import com.zyj.blog.domain.User;
import com.zyj.blog.service.AuthorityService;
import com.zyj.blog.service.UserService;
import com.zyj.blog.util.ConstraintViolationExceptionHandler;
import com.zyj.blog.vo.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username) {
        logger.info("username:" + username);
        return "userspace/u";
    }

    @GetMapping("/{username}/blogs")
    public String listBlogByOrder(@PathVariable("username") String username,
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "keyWord", required = false, defaultValue = "keyWord") String keyWord) {
        if (category != null) {
            logger.info("category:" + category);
            logger.info("selfLink:" + "redirect:/u/" + username + "/blogs?category=" + category);
            return "/userspace/u";
        } else if (keyWord != null && !keyWord.isEmpty()) {
            logger.info("keyWord:" + keyWord);
            logger.info("selfLink:" + "redirect:/u/" + username + "/blogs?keyWord=" + keyWord);
            return "/userspace/u";
        }
        logger.info("order:" + order);
        logger.info("selfLink:" + "redirect:/u/" + username + "/blogs?order=" + order);
        return "/userspace/u";
    }

    @GetMapping("/{username}/blog/{id}")
    public String listBlogByOrder(@PathVariable("id") Long id) {
        logger.info("blogId:" + id);
        return "/userspace/blog";
    }

    @GetMapping("/{username}/blog/edit")
    public String editBlog() {
        return "/userspace/blogEdit";
    }

    @GetMapping
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "name", required = false, defaultValue = "") String name, Model model) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<User> pages = userService.listUsersByNmaeLike(name, pageable);
        List<User> list = pages.getContent();
        model.addAttribute("page", pages);
        model.addAttribute("userList", list);
        return new ModelAndView(async == true ? "users/list:: #mainContainerReplace" : "users/list", "userModel",
                model);
    }

    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User(null, null, null, null));
        return new ModelAndView("users/add", "userModel", model);
    }

    @PostMapping
    public ResponseEntity<Response> saveOrUpdateUser(User user, Long authorityId) {
        // if (userService.loadUserByUsername(user.getUsername()).getUsername()) {
        //     logger.info("user" + user.toString());
        //     return ResponseEntity.ok().body(new Response(false, "您添加的用户账号已存在", user));
        // }
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId).get());
        user.setAuthorities(authorities);
        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            // TODO: handle exception
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        logger.info("ID:" + id);
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("删除失败:" + e.getMessage());;
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        model.addAttribute("user", user.get());
        return new ModelAndView("users/edit", "userModel", model);
    }
}
