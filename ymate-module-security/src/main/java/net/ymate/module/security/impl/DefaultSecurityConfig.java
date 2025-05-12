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
import net.ymate.module.security.annotation.SecurityConf;
import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.core.configuration.IConfigReader;
import net.ymate.platform.core.module.IModuleConfigurer;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:10
 * @version 1.0
 */
public final class DefaultSecurityConfig implements ISecurityConfig {

    private boolean enabled = true;

    private boolean initialized;

    private String cacheNamePrefix;

    private int cacheTimeout;

    private IAuthenticatorFactory authenticatorFactory;

    public static DefaultSecurityConfig defaultConfig() {
        return builder().build();
    }

    public static DefaultSecurityConfig create(IModuleConfigurer moduleConfigurer) {
        return new DefaultSecurityConfig(null, moduleConfigurer);
    }

    public static DefaultSecurityConfig create(Class<?> mainClass, IModuleConfigurer moduleConfigurer) {
        return new DefaultSecurityConfig(mainClass, moduleConfigurer);
    }

    public static Builder builder() {
        return new Builder();
    }

    private DefaultSecurityConfig() {
    }

    private DefaultSecurityConfig(Class<?> mainClass, IModuleConfigurer moduleConfigurer) {
        IConfigReader configReader = moduleConfigurer.getConfigReader();
        //
        SecurityConf confAnn = mainClass == null ? null : mainClass.getAnnotation(SecurityConf.class);
        //
        enabled = configReader.getBoolean(ENABLED, confAnn == null || confAnn.enabled());
        if (enabled) {
            cacheNamePrefix = configReader.getString(CACHE_NAME_PREFIX, confAnn != null ? confAnn.cacheNamePrefix() : null);
            cacheTimeout = configReader.getInt(CACHE_TIMEOUT, confAnn != null ? confAnn.cacheTimeout() : 0);
            authenticatorFactory = configReader.getClassImpl(AUTHENTICATOR_FACTORY_CLASS, confAnn == null || confAnn.authenticatorFactoryClass().equals(IAuthenticatorFactory.class) ? null : confAnn.authenticatorFactoryClass().getName(), IAuthenticatorFactory.class);
        }
    }

    @Override
    public void initialize(ISecurity owner) throws Exception {
        if (!initialized) {
            if (enabled) {
                if (authenticatorFactory == null) {
                    authenticatorFactory = ClassUtils.loadClass(IAuthenticatorFactory.class);
                    if (authenticatorFactory == null) {
                        authenticatorFactory = new DefaultAuthenticatorFactory();
                    }
                }
                authenticatorFactory.initialize(owner);
            }
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (!initialized) {
            this.enabled = enabled;
        }
    }

    @Override
    public String getCacheNamePrefix() {
        return cacheNamePrefix;
    }

    public void setCacheNamePrefix(String cacheNamePrefix) {
        if (!initialized) {
            this.cacheNamePrefix = cacheNamePrefix;
        }
    }

    @Override
    public int getCacheTimeout() {
        return cacheTimeout;
    }

    public void setCacheTimeout(int cacheTimeout) {
        if (!initialized) {
            this.cacheTimeout = cacheTimeout;
        }
    }

    @Override
    public IAuthenticatorFactory getAuthenticatorFactory() {
        return authenticatorFactory;
    }

    public void setAuthenticatorFactory(IAuthenticatorFactory authenticatorFactory) {
        if (!initialized) {
            this.authenticatorFactory = authenticatorFactory;
        }
    }

    public static final class Builder {

        private final DefaultSecurityConfig config = new DefaultSecurityConfig();

        private Builder() {
        }

        public Builder enabled(boolean enabled) {
            config.setEnabled(enabled);
            return this;
        }

        public Builder cacheNamePrefix(String cacheNamePrefix) {
            config.setCacheNamePrefix(cacheNamePrefix);
            return this;
        }

        public Builder cacheTimeout(int cacheTimeout) {
            config.setCacheTimeout(cacheTimeout);
            return this;
        }

        public Builder authenticatorFactory(IAuthenticatorFactory authenticatorFactory) {
            config.setAuthenticatorFactory(authenticatorFactory);
            return this;
        }

        public DefaultSecurityConfig build() {
            return config;
        }
    }
}