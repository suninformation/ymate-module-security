/*
 * Copyright (C) 2007-present the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.ymate.module.security.base;

import net.ymate.platform.commons.ext.IAttributeExt;

/**
 * 数据作用域接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2026/3/22 22:08
 * @since 1.0.0
 */
public interface IDataScopeInfo extends IAttributeExt {

    /**
     * 唯一标识
     */
    String getId();

    /**
     * 名称
     */
    String getName();

    /**
     * 排序
     */
    Float getSort();

    /**
     * 类型（由实际业务决定）
     */
    String getType();
}
