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
 * @author 刘镇 (suninformation@163.com) on 2025/4/30 16:10
 * @since 1.0.0
 */
public interface IGroupDataScopeInfo extends IAttributeExt {

    /**
     * 组数据范围唯一标识
     */
    String getId();

    /**
     * 组唯一标识
     */
    String getGroupId();

    /**
     * 部门唯一标识
     */
    String getDeptId();

    /**
     * 创建时间
     */
    Long getCreateTime();
}
