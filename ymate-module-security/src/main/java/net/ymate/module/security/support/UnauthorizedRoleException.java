/*
 * Copyright 2007-2023 the original author or authors.
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

import net.ymate.module.security.annotation.RoleType;
import net.ymate.module.security.base.IUserInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 2023/1/28 16:44
 * @since 1.0.0
 */
public class UnauthorizedRoleException extends RuntimeException {

    private static String doBuildMessage(IUserInfo user, RoleType[] roleTypes) {
        return String.format("User[id=%s] role is not within the allowed range: [%s]", user.getId(), StringUtils.join(roleTypes, ", "));
    }

    private final IUserInfo user;

    private final RoleType[] roleTypes;

    public UnauthorizedRoleException(IUserInfo user, RoleType[] roleTypes) {
        super(doBuildMessage(user, roleTypes));
        this.roleTypes = roleTypes;
        this.user = user;
    }

    public IUserInfo getUser() {
        return user;
    }

    public RoleType[] getRoleTypes() {
        return roleTypes;
    }
}
