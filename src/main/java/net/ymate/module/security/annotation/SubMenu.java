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
package net.ymate.module.security.annotation;

import net.ymate.module.security.ISecurity;
import net.ymate.platform.core.beans.annotation.Order;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/22 上午1:18
 * @version 1.0
 */
public @interface SubMenu {

    /**
     * @return 菜单项图标(支持I18N)
     */
    String icon() default "";

    /**
     * @return 菜单名称(用于显示, 支持I18N)
     */
    String name();

    /**
     * @return 菜单链接地址
     */
    String mapping();

    /**
     * @return 子菜单权限
     */
    Permission permission() default @Permission;

    /**
     * @return 子菜单是否可见, 默认为显示
     */
    ISecurity.DisplayType displayType() default ISecurity.DisplayType.SHOW;

    /**
     * @return 子菜单排序(限同一层级), 由大到小排列, 默认值为0表示按默认顺序
     */
    Order order() default @Order(0);
}
