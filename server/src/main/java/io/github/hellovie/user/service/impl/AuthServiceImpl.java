package io.github.hellovie.user.service.impl;

import io.github.hellovie.exception.system.MappingException;
import io.github.hellovie.user.service.AuthService;
import io.github.hellovie.user.service.auth.AuthStrategyType;
import io.github.hellovie.user.service.auth.BaseAuthStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static io.github.hellovie.exception.CommonExceptionType.STRATEGY_TYPE_NOT_MAPPER_STRATEGY_COMPONENT;

/**
 * 认证服务实现类 (用户账号注册或登录的策略上下文). <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Component("authServiceImpl")
public class AuthServiceImpl implements AuthService {
    /** 用户账号注册或登录的具体策略 (根据 map value 的类型自动装配, bean name 为 map key). */
    @Resource
    private Map<String, BaseAuthStrategy> authStrategiesMap;

    /**
     * 登录账号.
     *
     * @param strategyType 运用的策略类型.
     * @param request      用户登录所需信息.
     * @param ip           访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    @Override
    public Map<String, Object> login(AuthStrategyType strategyType, Object request, String ip) {
        BaseAuthStrategy activeAuthStrategy = checkAuthStrategyType(strategyType);
        return activeAuthStrategy.loginStrategy(request, ip);
    }

    /**
     * 注册账号 (普通用户).
     *
     * @param strategyType 运用的策略类型.
     * @param request      注册用户所需信息.
     * @param ip           访问的 IP 地址.
     * @return user (用户信息), token (token 令牌)
     */
    @Override
    public Map<String, Object> register(AuthStrategyType strategyType, Object request, String ip) {
        BaseAuthStrategy activeAuthStrategy = checkAuthStrategyType(strategyType);
        return activeAuthStrategy.registerStrategy(request, ip);
    }

    /**
     * 判断是否为有效策略, 有效则返回指定的策略实现, 反之抛出异常.
     *
     * @param strategyType 策略类型.
     * @return 指定策略的 BaseAuthStrategy.
     */
    private BaseAuthStrategy checkAuthStrategyType(AuthStrategyType strategyType) {
        for (AuthStrategyType value : AuthStrategyType.values()) {
            if (strategyType.equals(value)) {
                return this.authStrategiesMap.get(value.name());
            }
        }
        throw new MappingException(STRATEGY_TYPE_NOT_MAPPER_STRATEGY_COMPONENT);
    }
}