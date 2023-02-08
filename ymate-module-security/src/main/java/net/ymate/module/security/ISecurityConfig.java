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
package net.ymate.module.security;

import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IInitialization;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:02
 * @version 1.0
 */
@Ignored
public interface ISecurityConfig extends IInitialization<ISecurity> {

    String ENABLED = "enabled";

    String CACHE_NAME_PREFIX = "cache_name_prefix";

    String AUTHENTICATOR_FACTORY_CLASS = "authenticator_factory_class";

    /**
     * 模块是否已启用, 默认值: true
     *
     * @return 返回false表示禁用
     */
    boolean isEnabled();

    /**
     * @return 缓存名称前缀, 默认值: ""
     */
    String getCacheNamePrefix();

    /**
     * @return 返回用户认证工厂接口实例对象
     */
    IAuthenticatorFactory getAuthenticatorFactory();
}