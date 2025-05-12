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
 * 安全组接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:10 AM
 * @since 1.0.0
 */
public interface IGroupInfo extends IAttributeExt {

    /**
     * 组唯一标识
     */
    String getId();

    /**
     * 组名称
     */
    String getName();

    /**
     * 组描述
     */
    String getDescription();

    /**
     * 类型：{0-用户 1-操作员 2-管理员}
     */
    IRoleInfo.Type getType();
}
