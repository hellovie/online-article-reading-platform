package io.github.hellovie.user.controller;

import io.github.hellovie.core.util.ResultResponse;
import io.github.hellovie.file.domain.dto.FileDTO;
import io.github.hellovie.file.service.FileUploadService;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.request.LoginRequest;
import io.github.hellovie.user.domain.request.RegisterRequest;
import io.github.hellovie.user.domain.request.UserStatusRequest;
import io.github.hellovie.user.domain.vo.LoginVO;
import io.github.hellovie.user.domain.vo.UserVO;
import io.github.hellovie.user.mapper.UserMapper;
import io.github.hellovie.user.service.AuthService;
import io.github.hellovie.user.service.UserService;
import io.github.hellovie.user.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.Map;

import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_ADMIN_KEY;
import static io.github.hellovie.user.domain.enums.RolesConstant.ROLE_SUPER_ADMIN_KEY;
import static io.github.hellovie.user.service.auth.AuthStrategyType.DEFAULT_AUTH_STRATEGY;

/**
 * 用户账户信息Api. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Api(tags = "用户账号信息接口")
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource(name = "authServiceImpl")
    private AuthService authService;
    @Resource(name = "fileUploadServiceImpl")
    private FileUploadService fileUploadService;

    /**
     * 用户登录 (默认登录策略, username + password).
     *
     * @param request 登录用户所需的信息.
     * @return 登录用户的信息及 token 令牌.
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResultResponse<LoginVO> login(HttpServletRequest httpRequest, @Valid @RequestBody LoginRequest request) throws UnknownHostException {
        Map<String, Object> map = authService.login(DEFAULT_AUTH_STRATEGY, request, IpUtil.getIpAddr(httpRequest));
        UserDTO userDTO = (UserDTO) map.get("user");
        LoginVO loginVO = userMapper.toLoginVO(userDTO);
        loginVO.setToken((String) map.get("token"));
        FileDTO avatar = userDTO.getAvatar();
        String fullPath = avatar.getFullPath(fileUploadService.getStorageService(avatar.getStorage()).getRootPath());
        loginVO.setAvatar(fullPath);
        return ResultResponse.success(loginVO);
    }

    /**
     * 用户注册 (默认注册策略, username + password).
     *
     * @param request 注册用户所需的信息.
     * @return 用户信息.
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResultResponse<LoginVO> register(HttpServletRequest httpRequest, @Valid @RequestBody RegisterRequest request) throws UnknownHostException {
        Map<String, Object> map = authService.register(DEFAULT_AUTH_STRATEGY, request, IpUtil.getIpAddr(httpRequest));
        UserDTO userDTO = (UserDTO) map.get("user");
        LoginVO loginVO = userMapper.toLoginVO((UserDTO) map.get("user"));
        loginVO.setToken((String) map.get("token"));
        FileDTO avatar = userDTO.getAvatar();
        String fullPath = avatar.getFullPath(fileUploadService.getStorageService(avatar.getStorage()).getRootPath());
        loginVO.setAvatar(fullPath);
        return ResultResponse.success(loginVO);
    }

    /**
     * 设置用户状态 (启用/禁用, 锁定/解锁) (仅管理员能够访问).
     *
     * @param request 修改用户状态所需信息.
     * @return 无数据.
     */
    @ApiOperation("设置用户状态")
    @PostMapping("/status")
    @RolesAllowed({ROLE_ADMIN_KEY, ROLE_SUPER_ADMIN_KEY})
    public ResultResponse changeStatus(@Valid @RequestBody UserStatusRequest request) {
        userService.changeUserStatus(request);
        return ResultResponse.success(null);
    }

    /**
     * 获取用户账号信息.
     *
     * @return 用户个人账号信息.
     */
    @ApiOperation("获取用户账号信息")
    @GetMapping
    public ResultResponse<UserVO> getUserAccountInfo() throws UnknownHostException {
        UserDTO userDTO = userService.getUserAccountInfoByToken();
        UserVO userVO = userMapper.toVO(userDTO);
        FileDTO avatar = userDTO.getAvatar();
        String fullPath = avatar.getFullPath(fileUploadService.getStorageService(avatar.getStorage()).getRootPath());
        userVO.setAvatar(fullPath);
        return ResultResponse.success(userVO);
    }
}
