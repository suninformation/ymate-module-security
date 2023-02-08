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
 * 授权项接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:13 AM
 * @since 1.0.0
 */
public interface IPermissionInfo extends IAttributeExt {

    /**
     * 权限唯一标识
     */
    String getId();

    /**
     * 权限名称
     */
    String getName();

    /**
     * 权限唯一代码
     */
    String getCode();

    /**
     * 图标
     */
    String getIcon();

    /**
     * URL地址
     */
    String getUrl();

    /**
     * 模块或组件URL地址
     */
    String getComponentUrl();

    /**
     * 父级权限唯一标识
     */
    String getParentId();

    /**
     * 根权限唯一标识
     */
    String getRootId();

    /**
     * 层级路径
     */
    String getPath();

    /**
     * 层级深度
     */
    Long getDepth();

    /**
     * 排序
     */
    Float getSort();

    /**
     * 是否全屏显示
     */
    Integer getIsFullScreen();

    /**
     * 是否外部链接
     */
    Integer getIsOutsideUrl();

    /**
     * 类型：{0-菜单 1-动作 2-模块或组件}
     */
    Integer getType();

    /**
     * 状态：{0-启用 1-禁用}
     */
    Integer getStatus();

    /**
     * 创建时间
     */
    Long getCreateTime();

    /**
     * 最后修改时间
     */
    Long getLastModifyTime();
}
