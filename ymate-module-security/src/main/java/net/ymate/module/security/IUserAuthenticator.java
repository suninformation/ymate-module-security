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
package net.ymate.module.security;

import net.ymate.module.security.annotation.RoleType;
import net.ymate.module.security.base.IUserInfo;
import net.ymate.module.security.impl.DefaultUserAuthenticator;
import net.ymate.platform.core.beans.annotation.Ignored;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/9 下午6:11
 * @version 1.0
 */
@Ignored
public interface IUserAuthenticator extends Serializable {

    IUserAuthenticator DEFAULT_USER_AUTHENTICATOR = new DefaultUserAuthenticator();

    IUserInfo getUser();

    /**
     * @return 当前用户是否为超级用户
     */
    boolean isFounder();

    /**
     * @return 返回所拥有的角色类型集合
     */
    Collection<RoleType> getRoleTypes();

    /**
     * @return 返回所拥有的权限码集合
     */
    Collection<String> getPermissions();
}
