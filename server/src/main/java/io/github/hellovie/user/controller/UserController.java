package io.github.hellovie.user.controller;

import io.github.hellovie.core.ResultResponse;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.domain.vo.LoginVO;
import io.github.hellovie.user.domain.vo.UserVO;
import io.github.hellovie.user.mapper.UserMapper;
import io.github.hellovie.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户api
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:28
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource(name="userServiceImpl")
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     *
     * @param request 登录用户所需的信息
     * @return 登录用户的信息及token令牌
     */
    @PostMapping("/login")
    public ResultResponse<LoginVO> login(@RequestBody LoginRequest request) {
        LoginVO loginVO = userMapper.toVO(userService.login(request));
        return ResultResponse.success(loginVO);
    }

    /**
     * 用户注册
     *
     * @param request 注册用户所需的信息
     * @return 用户信息
     */
    @PostMapping("/register")
    public ResultResponse<UserVO> register(@RequestBody RegisterRequest request) {
        UserVO userVO = userMapper.toVO(userService.register(request));
        return ResultResponse.success(userVO);
    }
}
