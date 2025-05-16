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

import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.annotation.RoleType;
import net.ymate.module.security.base.IUserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/27 下午4:16
 * @version 1.0
 */
public class DefaultUserAuthenticator implements IUserAuthenticator {

    private final IUserInfo user;

    private final boolean founder;

    private final Collection<RoleType> roleTypes = new HashSet<>();

    private final Collection<String> permissions = new HashSet<>();

    public DefaultUserAuthenticator() {
        user = null;
        founder = false;
    }

    public DefaultUserAuthenticator(IUserInfo user, boolean founder, RoleType[] roleTypes, String[] permissions) {
        this.user = user;
        this.founder = founder;
        if (ArrayUtils.isNotEmpty(roleTypes)) {
            this.roleTypes.addAll(Arrays.asList(roleTypes));
        }
        if (ArrayUtils.isNotEmpty(permissions)) {
            Arrays.stream(permissions)
                    .filter(StringUtils::isNotBlank)
                    .map(String::toLowerCase)
                    .forEach(this.permissions::add);
        }
    }

    public DefaultUserAuthenticator(IUserInfo user, boolean founder, Collection<RoleType> roleTypes, Collection<String> permissions) {
        this.user = user;
        this.founder = founder;
        if (roleTypes != null && !roleTypes.isEmpty()) {
            this.roleTypes.addAll(roleTypes);
        }
        if (permissions != null && !permissions.isEmpty()) {
            permissions.stream()
                    .filter(StringUtils::isNotBlank)
                    .map(String::toLowerCase)
                    .forEach(this.permissions::add);
        }
    }

    @Override
    public IUserInfo getUser() {
        return user;
    }

    @Override
    public boolean isFounder() {
        return founder;
    }

    @Override
    public Collection<RoleType> getRoleTypes() {
        return Collections.unmodifiableCollection(roleTypes);
    }

    @Override
    public Collection<String> getPermissions() {
        return Collections.unmodifiableCollection(permissions);
    }
}
