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
package net.ymate.module.security;

import net.ymate.module.security.base.*;
import net.ymate.platform.core.support.IDestroyable;
import net.ymate.platform.core.support.IInitialization;

import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 2023/2/9 00:24
 * @since 1.0.0
 */
public interface ISecurityService extends IInitialization<ISecurity>, IDestroyable {

    /**
     * 获取当前用户
     *
     * @return 返回用户对象
     */
    IUserInfo getCurrentUser();

    /**
     * 获取用户认证接口实例
     *
     * @return 返回用户认证接口实例对象
     */
    IUserAuthenticator getUserAuthenticator(IUserInfo user);

    /**
     * @param user 用户对象
     * @return 返回用户所在部门的对象集合
     */
    Set<IDeptInfo> getDepartments(IUserInfo user);

    /**
     * @param user 用户对象
     * @return 返回用户所在组对象集合
     */
    Set<IGroupInfo> getGroups(IUserInfo user);

    /**
     * @param user 用户对象
     * @return 返回用户所拥有的角色对象集合
     */
    Set<IRoleInfo> getRoles(IUserInfo user);

    /**
     * @param user 用户对象
     * @return 返回用户所拥有的权限对象集合
     */
    Set<IPermissionInfo> getPermissions(IUserInfo user);
}
