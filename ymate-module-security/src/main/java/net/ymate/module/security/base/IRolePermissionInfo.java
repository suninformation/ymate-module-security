/*
 * Copyright 2007-2025 the original author or authors.
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
package net.ymate.module.security.base;

/**
 * 角色权限接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2025/4/30 16:02
 * @since 1.0.0
 */
public interface IRolePermissionInfo extends IAttributeExt {

    /**
     * 角色权限关系唯一标识
     */
    String getId();

    /**
     * 角色唯一标识
     */
    String getRoleId();

    /**
     * 权限唯一标识
     */
    String getPermissionId();

    /**
     * 创建时间
     */
    Long getCreateTime();
}
