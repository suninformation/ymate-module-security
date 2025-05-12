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
package net.ymate.module.security.impl;

import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.base.IUserInfo;
import net.ymate.module.security.support.UnauthorizedPermissionException;
import net.ymate.module.security.support.UnauthorizedRoleException;
import net.ymate.platform.cache.Caches;
import net.ymate.platform.cache.ICache;
import net.ymate.platform.cache.ICacheLocker;
import net.ymate.platform.commons.util.RuntimeUtils;
import net.ymate.platform.webmvc.util.ExceptionProcessHelper;
import net.ymate.platform.webmvc.util.IExceptionProcessor;
import net.ymate.platform.webmvc.util.WebErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/1 下午3:54
 * @version 1.0
 */
public class DefaultAuthenticatorFactory implements IAuthenticatorFactory {

    private static final Log LOG = LogFactory.getLog(DefaultAuthenticatorFactory.class);

    private static final IUserAuthenticator DEFAULT_USER_AUTHENTICATOR = new DefaultUserAuthenticator();

    private ISecurity owner;

    private String cacheName;

    private int cacheTimeout;

    private ICache authenticatorsCache;

    private boolean initialized;

    @Override
    public void initialize(ISecurity owner) throws Exception {
        if (!initialized) {
            this.owner = owner;
            cacheName = String.format("%s%s_authenticators", StringUtils.trimToEmpty(owner.getConfig().getCacheNamePrefix()), ISecurity.MODULE_NAME);
            cacheTimeout = owner.getConfig().getCacheTimeout();
            authenticatorsCache = owner.getOwner().getModuleManager().getModule(Caches.class).getConfig().getCacheProvider().getCache(cacheName);
            doInitialize();
            initialized = true;
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    protected void doInitialize() throws Exception {
        IExceptionProcessor processor = target -> {
            if (owner.getOwner().isDevEnv() && LOG.isWarnEnabled()) {
                LOG.warn(target.getMessage());
            }
            return new IExceptionProcessor.Result(WebErrorCode.REQUEST_RESOURCE_UNAUTHORIZED, WebErrorCode.MSG_REQUEST_RESOURCE_UNAUTHORIZED);
        };
        ExceptionProcessHelper.DEFAULT.registerProcessor(UnauthorizedRoleException.class, processor);
        ExceptionProcessHelper.DEFAULT.registerProcessor(UnauthorizedPermissionException.class, processor);
    }

    protected ISecurity getOwner() {
        return owner;
    }

    protected String getCacheName() {
        return cacheName;
    }

    protected int getCacheTimeout() {
        return cacheTimeout;
    }

    protected ICache getAuthenticatorsCache() {
        return authenticatorsCache;
    }

    @Override
    public IUserAuthenticator getUserAuthenticator() {
        IUserInfo user = owner.getService().getCurrentUser();
        if (user != null && StringUtils.isNotBlank(user.getId())) {
            String cacheKey = user.getId();
            IUserAuthenticator authenticator = (IUserAuthenticator) authenticatorsCache.get(cacheKey);
            if (authenticator == null) {
                try {
                    ICacheLocker locker = authenticatorsCache.acquireCacheLocker();
                    locker.writeLock(cacheKey);
                    try {
                        authenticator = (IUserAuthenticator) authenticatorsCache.get(cacheKey);
                        if (authenticator == null) {
                            authenticator = owner.getService().getUserAuthenticator(user);
                            if (authenticator == null) {
                                authenticator = DEFAULT_USER_AUTHENTICATOR;
                            }
                            authenticatorsCache.put(cacheKey, authenticator, cacheTimeout);
                        }
                        return authenticator;
                    } finally {
                        locker.releaseWriteLock(cacheKey);
                    }
                } catch (Exception e) {
                    if (LOG.isWarnEnabled()) {
                        LOG.warn(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
                    }
                    authenticator = DEFAULT_USER_AUTHENTICATOR;
                }
            }
            return authenticator;
        }
        return DEFAULT_USER_AUTHENTICATOR;
    }
}
