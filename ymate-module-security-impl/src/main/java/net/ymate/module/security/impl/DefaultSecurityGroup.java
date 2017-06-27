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
package net.ymate.module.security.impl;

import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.model.SecurityGroup;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/4 下午2:36
 * @version 1.0
 */
public class DefaultSecurityGroup implements ISecurity.IGroup {

    private String id;

    private String name;

    private String siteId;

    private IUserAuthenticator authenticator;

    public DefaultSecurityGroup(String id, String name, String siteId, IUserAuthenticator authenticator) {
        this.id = id;
        this.name = name;
        this.siteId = siteId;
        this.authenticator = authenticator;
    }

    public DefaultSecurityGroup(String id, String name, String siteId) {
        this.id = id;
        this.name = name;
        this.siteId = siteId;
    }

    public DefaultSecurityGroup(String name, String siteId) {
        this.name = name;
        this.siteId = siteId;
    }

    public DefaultSecurityGroup(SecurityGroup securityGroup) {
        this.id = securityGroup.getId();
        this.name = securityGroup.getName();
        this.siteId = securityGroup.getSiteId();
        //
        final Set<ISecurity.Role> _roles = new HashSet<ISecurity.Role>();
        if (BlurObject.bind(securityGroup.getIsAdminRole()).toBooleanValue()) {
            _roles.add(ISecurity.Role.ADMIN);
        }
        if (BlurObject.bind(securityGroup.getIsOperatorRole()).toBooleanValue()) {
            _roles.add(ISecurity.Role.OPERATOR);
        }
        if (BlurObject.bind(securityGroup.getIsUserRole()).toBooleanValue()) {
            _roles.add(ISecurity.Role.USER);
        }
        //
        final String[] _permissions = StringUtils.split(StringUtils.trimToEmpty(securityGroup.getPermission()), "|");
        //
        this.authenticator = new IUserAuthenticator() {

            public boolean isFounder() {
                return false;
            }

            public ISecurity.Role[] getUserRoles() {
                return _roles.toArray(new ISecurity.Role[_roles.size()]);
            }

            public String[] getUserPermissions() {
                return _permissions;
            }
        };
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public IUserAuthenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(IUserAuthenticator authenticator) {
        this.authenticator = authenticator;
    }
}
