/*
 * Copyright 2007-2018 the original author or authors.
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

import net.ymate.module.oauth.base.OAuthClientUserBean;
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.platform.cache.Caches;
import net.ymate.platform.cache.ICache;
import net.ymate.platform.cache.ICacheLocker;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 2018/2/10 下午7:41
 * @version 1.0
 */
public class OAuthUserAuthenticatorFactory extends DefaultAuthenticatorFactory {

    private static final Log _LOG = LogFactory.getLog(OAuthUserAuthenticatorFactory.class);

    private ICache __dataCache;

    @Override
    public void init(ISecurity owner) throws Exception {
        super.init(owner);
        __dataCache = Caches.get().getCacheProvider().getCache("security_data");
    }

    @Override
    public void destroy() throws Exception {
        __dataCache = null;
        super.destroy();
    }

    @Override
    public IUserAuthenticator createUserAuthenticatorIfNeed() {
        OAuthClientUserBean _clientUser = WebContext.getContext().getAttribute(OAuthClientUserBean.class.getName());
        if (_clientUser != null && StringUtils.isNotBlank(_clientUser.getUid())) {
            try {
                IUserAuthenticator _authenticator = (IUserAuthenticator) __dataCache.get(_clientUser.getUid());
                if (_authenticator == null) {
                    ICacheLocker _locker = __dataCache.acquireCacheLocker();
                    if (_locker != null) {
                        _locker.writeLock(_clientUser.getUid());
                    }
                    try {
                        _authenticator = getSecurityRepository().getUserAuthenticator(_clientUser.getUid());
                        _authenticator = new DefaultUserAuthenticator(checkUserIsFounder(_clientUser.getUid()), _authenticator.getUserRoles(), _authenticator.getUserPermissions());
                        //
                        __dataCache.put(_clientUser.getUid(), _authenticator);
                    } finally {
                        if (_locker != null) {
                            _locker.releaseWriteLock(_clientUser.getUid());
                        }
                    }
                }
                return _authenticator;
            } catch (Exception e) {
                _LOG.warn("", RuntimeUtils.unwrapThrow(e));
            }
        }
        return new DefaultUserAuthenticator();
    }
}
