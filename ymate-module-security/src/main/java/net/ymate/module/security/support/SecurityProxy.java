/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.module.security.support;

import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.PermissionMeta;
import net.ymate.module.security.annotation.LogicType;
import net.ymate.module.security.annotation.RoleType;
import net.ymate.platform.core.beans.annotation.Order;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import net.ymate.platform.webmvc.exception.RequestForbiddenException;
import net.ymate.platform.webmvc.exception.UserSessionInvalidException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * 访问权限控制代理, 用于处理被声明@Permission注解的类方法
 *
 * @author 刘镇 (suninformation@163.com) on 17/2/25 下午4:28
 * @version 1.0
 */
@Order(-85000)
public class SecurityProxy implements IProxy {

    public static boolean containsRoleTypes(RoleType[] roleTypes, IUserAuthenticator authenticator) {
        if (ArrayUtils.isNotEmpty(roleTypes)) {
            return authenticator.getRoleTypes()
                    .stream()
                    .anyMatch(roleType -> Arrays.stream(roleTypes)
                            .anyMatch(rt -> rt.compareTo(roleType) == 0));
        }
        return true;
    }

    public static boolean containsPermissions(LogicType type, String[] permissions, IUserAuthenticator authenticator) {
        if (ArrayUtils.isNotEmpty(permissions)) {
            boolean flag = false;
            if (LogicType.OR.equals(type)) {
                flag = authenticator.getPermissions()
                        .stream()
                        .anyMatch(permission -> ArrayUtils.contains(permissions, permission));
            } else if (LogicType.AND.equals(type)) {
                flag = authenticator.getPermissions().containsAll(Arrays.asList(permissions));
            }
            return flag;
        }
        return true;
    }

    private final ISecurity owner;

    public SecurityProxy(ISecurity owner) {
        this.owner = owner;
    }

    @Override
    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        PermissionMeta permissionMeta = PermissionMeta.createAndGet(proxyChain.getTargetMethod());
        if (permissionMeta != null) {
            IAuthenticatorFactory authenticatorFactory = owner.getConfig().getAuthenticatorFactory();
            if (authenticatorFactory != null) {
                IUserAuthenticator authenticator = authenticatorFactory.getUserAuthenticator();
                if (authenticator != null && authenticator.getUser() != null) {
                    if (!authenticator.isFounder()) {
                        // 进行用户角色判断
                        if (!containsRoleTypes(permissionMeta.getRoleTypes(), authenticator)) {
                            throw new UnauthorizedRoleException(authenticator.getUser(), permissionMeta.getRoleTypes());
                        }
                        // 进行用户权限判断
                        if (!containsPermissions(permissionMeta.getLogicType(), permissionMeta.getPermissions(), authenticator)) {
                            throw new UnauthorizedPermissionException(authenticator.getUser(), permissionMeta.getPermissions());
                        }
                    }
                } else {
                    throw new UserSessionInvalidException();
                }
            } else {
                throw new RequestForbiddenException();
            }
        }
        return proxyChain.doProxyChain();
    }
}
