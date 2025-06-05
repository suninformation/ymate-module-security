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
package net.ymate.module.security.impl;

import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.ISecurityConfig;
import net.ymate.platform.core.module.IModuleConfigurer;
import net.ymate.platform.core.module.impl.DefaultModuleConfigurable;

/**
 * @author 刘镇 (suninformation@163.com) on 2023/01/28 02:25
 * @since 1.0.0
 */
public final class DefaultSecurityConfigurable extends DefaultModuleConfigurable {

    public static Builder builder() {
        return new Builder();
    }

    private DefaultSecurityConfigurable() {
        super(ISecurity.MODULE_NAME);
    }

    public static final class Builder {

        private final DefaultSecurityConfigurable configurable = new DefaultSecurityConfigurable();

        private Builder() {
        }

        public Builder enabled(boolean enabled) {
            configurable.addConfig(ISecurityConfig.ENABLED, String.valueOf(enabled));
            return this;
        }

        public Builder proxyEnabled(boolean proxyEnabled) {
            configurable.addConfig(ISecurityConfig.PROXY_ENABLED, String.valueOf(proxyEnabled));
            return this;
        }

        public Builder cacheNamePrefix(String cacheNamePrefix) {
            configurable.addConfig(ISecurityConfig.CACHE_NAME_PREFIX, cacheNamePrefix);
            return this;
        }

        public Builder cacheTimeout(int cacheTimeout) {
            configurable.addConfig(ISecurityConfig.CACHE_TIMEOUT, String.valueOf(cacheTimeout));
            return this;
        }

        public Builder authenticatorFactoryClass(Class<? extends IAuthenticatorFactory> authenticatorFactoryClass) {
            configurable.addConfig(ISecurityConfig.AUTHENTICATOR_FACTORY_CLASS, authenticatorFactoryClass.getName());
            return this;
        }

        public IModuleConfigurer build() {
            return configurable.toModuleConfigurer();
        }
    }
}