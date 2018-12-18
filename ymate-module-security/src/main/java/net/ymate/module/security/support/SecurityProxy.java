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

import com.alibaba.fastjson.JSON;
import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.PermissionMeta;
import net.ymate.module.security.annotation.LogicType;
import net.ymate.module.security.annotation.RoleType;
import net.ymate.module.security.annotation.Security;
import net.ymate.platform.core.beans.annotation.Order;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
import net.ymate.platform.webmvc.exception.RequestUnauthorizedException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.Collections;

/**
 * 访问权限控制代理, 用于处理被声明@Security和@Permission注解的类和方法
 *
 * @author 刘镇 (suninformation@163.com) on 17/2/25 下午4:28
 * @version 1.0
 */
@Proxy(annotation = Security.class, order = @Order(-899))
public class SecurityProxy implements IProxy {

    private static final Log _LOG = LogFactory.getLog(SecurityProxy.class);

    public static boolean containsUserRole(RoleType[] roles, IUserAuthenticator _authenticator) {
        if (ArrayUtils.isNotEmpty(roles)) {
            boolean _flag = false;
            RoleType[] _roles = _authenticator.getUserRoles();
            if (ArrayUtils.isNotEmpty(_roles)) {
                for (RoleType _role : _roles) {
                    for (RoleType _r : roles) {
                        if (_r.compareTo(_role) == 0) {
                            _flag = true;
                            break;
                        }
                    }
                    if (_flag) {
                        break;
                    }
                }
            }
            return _flag;
        }
        return true;
    }

    public static boolean containsUserPermissions(LogicType logicType, String[] permissions, IUserAuthenticator _authenticator) {
        if (ArrayUtils.isNotEmpty(permissions)) {
            boolean _flag = false;
            String[] _permissions = _authenticator.getUserPermissions();
            if (ArrayUtils.isNotEmpty(_permissions)) {
                switch (logicType) {
                    case OR:
                        for (String _right : _permissions) {
                            if (ArrayUtils.contains(permissions, _right)) {
                                _flag = true;
                                break;
                            }
                        }
                        break;
                    case AND:
                        _flag = Collections.indexOfSubList(Arrays.asList(_permissions), Arrays.asList(permissions)) != -1;
                        break;
                }
            }
            return _flag;
        }
        return true;
    }

    @Override
    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        PermissionMeta _meta = PermissionMeta.bind(proxyChain.getTargetMethod());
        if (_meta != null && !net.ymate.module.security.Security.get().isFiltered(_meta)) {
            IAuthenticatorFactory _factory = net.ymate.module.security.Security.get().getModuleCfg().getAuthenticatorFactory();
            if (_factory != null) {
                IUserAuthenticator _authenticator = _factory.createUserAuthenticatorIfNeed();
                if (_authenticator != null && !_authenticator.isFounder()) {
                    // 进行用户角色判断
                    if (!containsUserRole(_meta.getRoles(), _authenticator)) {
                        String _errMsg = "User role is not within the allowed range: " + JSON.toJSONString(_meta.getRoles());
                        if (proxyChain.getProxyFactory().getOwner().getConfig().isDevelopMode() && _LOG.isDebugEnabled()) {
                            _LOG.debug(_errMsg);
                        }
                        throw new RequestUnauthorizedException(_errMsg);
                    }
                    // 进行用户权限判断
                    if (!containsUserPermissions(_meta.getLogicType(), _meta.getPermissions(), _authenticator)) {
                        String _errMsg = "User permissions are not within the allowed range: " + JSON.toJSONString(_meta.getPermissions());
                        if (proxyChain.getProxyFactory().getOwner().getConfig().isDevelopMode() && _LOG.isDebugEnabled()) {
                            _LOG.debug(_errMsg);
                        }
                        throw new RequestUnauthorizedException(_errMsg);
                    }
                }
            }
        }
        return proxyChain.doProxyChain();
    }
}
