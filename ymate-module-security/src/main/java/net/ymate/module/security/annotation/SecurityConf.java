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
package net.ymate.module.security.annotation;

import net.ymate.module.security.IAuthenticatorFactory;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author 刘镇 (suninformation@163.com) on 2023/01/28 02:25
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityConf {

    /**
     * @return 模块是否已启用, 默认值: true
     */
    boolean enabled() default true;

    /**
     * @return 缓存名称前缀, 默认值: ""
     */
    String cacheNamePrefix() default StringUtils.EMPTY;

    /**
     * @return 用户认证工厂接口实例对象
     */
    Class<? extends IAuthenticatorFactory> authenticatorFactoryClass() default IAuthenticatorFactory.class;
}
