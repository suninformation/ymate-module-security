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

import net.ymate.module.security.annotation.SecurityAble;
import net.ymate.module.security.handle.SecurityAbleHandler;
import net.ymate.module.security.impl.DefaultSecurityConfig;
import net.ymate.module.security.support.SecurityProxy;
import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.core.*;
import net.ymate.platform.core.beans.IBeanLoadFactory;
import net.ymate.platform.core.beans.IBeanLoader;
import net.ymate.platform.core.beans.proxy.IProxyFactory;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.IModuleConfigurer;
import net.ymate.platform.core.module.impl.DefaultModuleConfigurer;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:06
 * @version 1.0
 */
public final class Security implements IModule, ISecurity {

    private static volatile ISecurity instance;

    private IApplication owner;

    private ISecurityConfig config;

    private ISecurityService service;

    private boolean initialized;

    public static ISecurity get() {
        ISecurity inst = instance;
        if (inst == null) {
            synchronized (Security.class) {
                inst = instance;
                if (inst == null) {
                    instance = inst = YMP.get().getModuleManager().getModule(Security.class);
                }
            }
        }
        return inst;
    }

    public Security() {
    }

    public Security(ISecurityConfig config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public void initialize(IApplication owner) throws Exception {
        if (!initialized) {
            //
            YMP.showVersion("Initializing ymate-module-security-${version}", new Version(1, 0, 0, Security.class, Version.VersionType.Release));
            //
            this.owner = owner;
            if (config == null) {
                IApplicationConfigureFactory configureFactory = owner.getConfigureFactory();
                if (configureFactory != null) {
                    IApplicationConfigurer configurer = configureFactory.getConfigurer();
                    if (configurer != null) {
                        IBeanLoadFactory beanLoaderFactory = configurer.getBeanLoadFactory();
                        if (beanLoaderFactory != null) {
                            IBeanLoader beanLoader = beanLoaderFactory.getBeanLoader();
                            if (beanLoader != null) {
                                beanLoader.registerHandler(SecurityAble.class, new SecurityAbleHandler());
                            }
                        }
                        IModuleConfigurer moduleConfigurer = configurer.getModuleConfigurer(MODULE_NAME);
                        if (moduleConfigurer != null) {
                            config = DefaultSecurityConfig.create(configureFactory.getMainClass(), moduleConfigurer);
                        } else {
                            config = DefaultSecurityConfig.create(configureFactory.getMainClass(), DefaultModuleConfigurer.createEmpty(MODULE_NAME));
                        }
                    }
                }
                if (config == null) {
                    config = DefaultSecurityConfig.defaultConfig();
                }
            }
            if (!config.isInitialized()) {
                config.initialize(this);
            }
            if (config.isEnabled()) {
                service = ClassUtils.loadClass(ISecurityService.class);
                if (service != null) {
                    service.initialize(this);
                }
                IProxyFactory proxyFactory = owner.getBeanFactory().getProxyFactory();
                if (proxyFactory != null) {
                    proxyFactory.registerProxy(new SecurityProxy(this));
                }
            }
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void close() throws Exception {
        if (initialized) {
            initialized = false;
            //
            if (service != null) {
                service.close();
                service = null;
            }
            //
            config = null;
            owner = null;
        }
    }

    @Override
    public IApplication getOwner() {
        return owner;
    }

    @Override
    public ISecurityConfig getConfig() {
        return config;
    }

    @Override
    public ISecurityService getService() {
        return service;
    }
}
