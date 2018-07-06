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

import java.lang.annotation.*;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午5:02
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {

    /**
     * @return 权限组或权限名称, 默认为空
     */
    String name() default "";

    /**
     * @return 限制访问角色集合, 默认为继承类级设置，若无父级设置则RoleType.INHERIT等同于RoleType.ALL
     */
    RoleType[] roles() default RoleType.INHERIT;

    /**
     * @return 权限码集合
     */
    String[] value() default {};

    /**
     * @return 权限码匹配逻辑类型, 默认为继承类级设置，若无父级设置则LogicType.INHERIT等同于LogicType.OR
     */
    LogicType logicType() default LogicType.INHERIT;
}
