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

import java.io.Serializable;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/9 下午6:11
 * @version 1.0
 */
public interface IUserAuthenticator extends Serializable {

    /**
     * @return 当前用户是否为超级用户(即站点创建者, 该用户将默认拥有全部权限且不可删除)
     */
    boolean isFounder();

    /**
     * @return 返回所拥有的角色集合
     */
    ISecurity.Role[] getUserRoles();

    /**
     * @return 返回所拥有的权限码集合
     */
    String[] getUserPermissions();
}
