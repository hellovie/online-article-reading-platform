package io.github.hellovie.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试搭建环境
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/18 22:22
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Hello world!";
    }
}
