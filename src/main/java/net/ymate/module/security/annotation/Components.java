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

import java.lang.annotation.*;

/**
 * 声明一个类为组件, 组件对象处理器将自动扫描分析并进行生命周期维护及访问权限等控制和管理
 *
 * @author 刘镇 (suninformation@163.com) on 17/2/20 下午5:28
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Components {

    /**
     * @return 组件唯一标识, 默认为当前类名称
     */
    String id() default "";

    /**
     * @return 组件图标(支持I18N)
     */
    String icon() default "";

    /**
     * @return 组件名称(用于显示, 支持I18N)
     */
    String name() default "";

    /**
     * @return 组件链接地址
     */
    String mapping() default "";

    /**
     * @return 菜单分组集合
     */
    MenuGroup[] value() default {};

    /**
     * @return 组件通用权限
     */
    Permission permission() default @Permission;

    /**
     * @return 组件是否可见, 默认为显示
     */
    ISecurity.DisplayType displayType() default ISecurity.DisplayType.SHOW;

    /**
     * @return 组件排序, 由大到小排列, 默认值为0表示按默认顺序
     */
    Order order() default @Order(0);

    /**
     * @return 自定义I18N资源文件名称
     */
    String resourcesName() default "";
}
