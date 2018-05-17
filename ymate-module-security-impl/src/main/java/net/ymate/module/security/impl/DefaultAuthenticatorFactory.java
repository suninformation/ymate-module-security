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

import net.ymate.framework.webmvc.support.UserSessionBean;
import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.repository.ISecurityRepository;
import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/1 下午3:54
 * @version 1.0
 */
public class DefaultAuthenticatorFactory implements IAuthenticatorFactory {

    private static final Log _LOG = LogFactory.getLog(DefaultAuthenticatorFactory.class);

    private ISecurity __owner;

    @Override
    public void init(ISecurity owner) throws Exception {
        __owner = owner;
    }

    @Override
    public void destroy() throws Exception {
        __owner = null;
    }

    protected ISecurity getOwner() {
        return __owner;
    }

    protected ISecurityRepository getSecurityRepository() {
        return __owner.getOwner().getBean(ISecurityRepository.class);
    }

    @Override
    public IUserAuthenticator createUserAuthenticatorIfNeed() {
        UserSessionBean _sessionBean = UserSessionBean.current();
        if (_sessionBean != null) {
            try {
                IUserAuthenticator _authenticator = _sessionBean.getAttribute(IUserAuthenticator.class.getName());
                if (_authenticator == null) {
                    _authenticator = getSecurityRepository().getUserAuthenticator(_sessionBean.getUid());
                    _authenticator = new DefaultUserAuthenticator(checkUserIsFounder(_sessionBean.getUid()), _authenticator.getUserRoles(), _authenticator.getUserPermissions());
                    //
                    _sessionBean.addAttribute(IUserAuthenticator.class.getName(), _authenticator);
                }
                return _authenticator;
            } catch (Exception e) {
                _LOG.warn("", RuntimeUtils.unwrapThrow(e));
            }
        }
        return new DefaultUserAuthenticator();
    }

    @Override
    public boolean checkUserIsFounder(String uid) {
        return false;
    }
}
