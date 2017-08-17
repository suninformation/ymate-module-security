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
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.context.WebContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/1 下午3:54
 * @version 1.0
 */
public class DefaultAuthenticatorFactory implements IAuthenticatorFactory {

    private static final String __SITE_ID = "site_id";

    private static final Log _LOG = LogFactory.getLog(DefaultAuthenticatorFactory.class);

    private ISecurity __owner;

    public void init(ISecurity owner) throws Exception {
        __owner = owner;
    }

    public void destroy() throws Exception {
        __owner = null;
    }

    public IUserAuthenticator createUserAuthenticatorIfNeed() {
        UserSessionBean _sessionBean = UserSessionBean.current();
        if (_sessionBean != null) {
            try {
                IUserAuthenticator _authenticator = _sessionBean.getAttribute(IUserAuthenticator.class.getName());
                if (_authenticator == null) {
                    String _siteId = __doGetCurrentSiteId(_sessionBean);
                    //
                    _authenticator = __owner.getModuleCfg().getStorageAdapter().getUserAuthenticator(_siteId, _sessionBean.getUid());
                    _authenticator = new DefaultUserAuthenticator(__doCheckUserIsFounder(_sessionBean.getUid(), _siteId), _authenticator.getUserRoles(), _authenticator.getUserPermissions());
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

    /**
     * @param sessionBean 当前用户会话对象
     * @return 尝试获取site_id参数值, 子类可根据实际需求重写该方法
     */
    protected String __doGetCurrentSiteId(UserSessionBean sessionBean) {
        String _siteId = sessionBean.getAttribute(__SITE_ID);
        if (StringUtils.isBlank(_siteId)) {
            _siteId = WebContext.getRequest().getParameter(__SITE_ID);
            if (StringUtils.isBlank(_siteId)) {
                _siteId = WebContext.getContext().getAttribute(__SITE_ID);
            }
        }
        return StringUtils.defaultIfBlank(_siteId, "default");
    }

    /**
     * @param uid    当前用户唯一标识ID
     * @param siteId 所属站点唯一标识ID
     * @return 判断当前用户是否为该站点创建者(超级用户), 默认值: false, 子类可根据实际需求重写该方法
     */
    protected boolean __doCheckUserIsFounder(String uid, String siteId) {
        return false;
    }
}
