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
package net.ymate.module.security.taglib;

import net.ymate.framework.core.taglib.AbstractDataTagSupport;
import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.Security;
import net.ymate.module.security.annotation.LogicType;
import net.ymate.module.security.annotation.RoleType;
import net.ymate.module.security.support.SecurityProxy;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.List;

/**
 * 判断当前用户是否拥有指定角色和权限
 *
 * @author 刘镇 (suninformation@163.com) on 17/5/22 上午11:26
 * @version 1.0
 */
public class PermissionTag extends AbstractDataTagSupport {

    /**
     * 是否拥有用户角色
     */
    private boolean userRole;

    /**
     * 是否拥有管理员角色
     */
    private boolean adminRole;

    /**
     * 是否拥有操作员角色
     */
    private boolean operatorRole;

    /**
     * 权限判断逻辑类型: AND或OR, 默认值: OR
     */
    private String logicType;

    /**
     * 需要验证的用户权限列表, 多个用"|"分隔
     */
    private String permissions;

    @Override
    protected Object doProcessTagData() throws JspException {
        IAuthenticatorFactory _factory = Security.get().getModuleCfg().getAuthenticatorFactory();
        if (_factory != null) {
            IUserAuthenticator _authenticator = _factory.createUserAuthenticatorIfNeed();
            if (_authenticator != null) {
                if (_authenticator.isFounder()) {
                    return true;
                }
                // 进行用户角色判断
                List<RoleType> _roles = new ArrayList<RoleType>();
                if (userRole) {
                    _roles.add(RoleType.USER);
                }
                if (adminRole) {
                    _roles.add(RoleType.ADMIN);
                }
                if (operatorRole) {
                    _roles.add(RoleType.OPERATOR);
                }
                //
                String[] _permissions = StringUtils.split(StringUtils.trimToEmpty(permissions), "|");
                //
                boolean _returnFlag = SecurityProxy.containsUserRole(_roles.toArray(new RoleType[0]), _authenticator);
                // 进行用户权限判断
                if (_returnFlag && SecurityProxy.containsUserPermissions(LogicType.valueOf(StringUtils.defaultIfBlank(logicType, "OR").toUpperCase()), _permissions, _authenticator)) {
                    return true;
                }
            }
        }
        return null;
    }

    public boolean isUserRole() {
        return userRole;
    }

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public boolean isAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public boolean isOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(boolean operatorRole) {
        this.operatorRole = operatorRole;
    }

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
