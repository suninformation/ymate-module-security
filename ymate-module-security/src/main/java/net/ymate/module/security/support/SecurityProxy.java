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
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.PermissionMeta;
import net.ymate.module.security.SecurityPrivilegeException;
import net.ymate.module.security.annotation.Security;
import net.ymate.platform.core.beans.annotation.Order;
import net.ymate.platform.core.beans.annotation.Proxy;
import net.ymate.platform.core.beans.proxy.IProxy;
import net.ymate.platform.core.beans.proxy.IProxyChain;
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

    public Object doProxy(IProxyChain proxyChain) throws Throwable {
        PermissionMeta _meta = PermissionMeta.bind(proxyChain.getTargetMethod());
        if (_meta != null && !net.ymate.module.security.Security.get().isFiltered(_meta)) {
            IUserAuthenticator _authenticator = net.ymate.module.security.Security.get().getModuleCfg().getUserAuthenticator();
            if (_authenticator != null) {
                // 进行用户角色判断
                if (ArrayUtils.isNotEmpty(_meta.getRoles())) {
                    boolean _flag = false;
                    ISecurity.Role[] _roles = _authenticator.getUserRoles();
                    if (ArrayUtils.isNotEmpty(_roles)) {
                        for (ISecurity.Role _role : _roles) {
                            if (ArrayUtils.contains(_meta.getRoles(), _role)) {
                                _flag = true;
                                break;
                            }
                        }
                    }
                    if (!_flag) {
                        String _errMsg = "User role is not within the allowed range: " + JSON.toJSONString(_roles);
                        if (proxyChain.getProxyFactory().getOwner().getConfig().isDevelopMode() && _LOG.isDebugEnabled()) {
                            _LOG.debug(_errMsg);
                        }
                        throw new SecurityPrivilegeException(_errMsg);
                    }
                }
                // 进行用户权限判断
                if (ArrayUtils.isNotEmpty(_meta.getPermissions())) {
                    boolean _flag = false;
                    String[] _permissions = _authenticator.getUserPermissions();
                    if (ArrayUtils.isNotEmpty(_permissions)) {
                        switch (_meta.getLogicType()) {
                            case OR:
                                for (String _right : _permissions) {
                                    if (ArrayUtils.contains(_meta.getPermissions(), _right)) {
                                        _flag = true;
                                        break;
                                    }
                                }
                                break;
                            case AND:
                                _flag = Collections.indexOfSubList(Arrays.asList(_permissions), Arrays.asList(_meta.getPermissions())) != -1;
                                break;
                        }
                    }
                    if (!_flag) {
                        String _errMsg = "User permissions are not within the allowed range: " + JSON.toJSONString(_permissions);
                        if (proxyChain.getProxyFactory().getOwner().getConfig().isDevelopMode() && _LOG.isDebugEnabled()) {
                            _LOG.debug(_errMsg);
                        }
                        throw new SecurityPrivilegeException(_errMsg);
                    }
                }
            }
        }
        return proxyChain.doProxyChain();
    }
}