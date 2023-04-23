package io.github.hellovie.user.controller;

import io.github.hellovie.core.util.ResultResponse;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.domain.request.UserStatusRequest;
import io.github.hellovie.user.domain.vo.LoginVO;
import io.github.hellovie.user.mapper.UserMapper;
import io.github.hellovie.user.service.UserService;
import io.github.hellovie.user.util.IpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_ADMIN_KEY;
import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_SUPER_ADMIN_KEY;

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
    @Resource(name = "userServiceImpl")
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
    public ResultResponse<LoginVO> login(HttpServletRequest httpRequest, @Valid @RequestBody LoginRequest request) {
        Map<String, Object> map = userService.login(request, IpUtil.getIpAddr(httpRequest));
        LoginVO loginVO = userMapper.toVO((UserDTO) map.get("user"));
        loginVO.setToken((String) map.get("token"));
        return ResultResponse.success(loginVO);
    }

    /**
     * 用户注册
     *
     * @param request 注册用户所需的信息
     * @return 用户信息
     */
    @PostMapping("/register")
    public ResultResponse<LoginVO> register(HttpServletRequest httpRequest, @Valid @RequestBody RegisterRequest request) {
        Map<String, Object> map = userService.register(request, IpUtil.getIpAddr(httpRequest));
        LoginVO loginVO = userMapper.toVO((UserDTO) map.get("user"));
        loginVO.setToken((String) map.get("token"));
        return ResultResponse.success(loginVO);
    }

    /**
     * 设置用户状态(启用/禁用，锁定/解锁)(仅管理员能够访问)
     *
     * @param request 修改用户状态所需信息
     * @return 无数据
     */
    @PostMapping("/status")
    @RolesAllowed({ROLE_ADMIN_KEY, ROLE_SUPER_ADMIN_KEY})
    public ResultResponse changeStatus(@Valid @RequestBody UserStatusRequest request) {
        userService.changeUserStatus(request);
        return ResultResponse.success(null);
    }
}
