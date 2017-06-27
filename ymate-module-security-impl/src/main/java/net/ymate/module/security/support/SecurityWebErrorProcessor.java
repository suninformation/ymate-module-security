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
package net.ymate.module.security.support;

import net.ymate.framework.core.Optional;
import net.ymate.framework.webmvc.ErrorCode;
import net.ymate.framework.webmvc.WebErrorProcessor;
import net.ymate.module.security.SecurityPrivilegeException;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.webmvc.IWebMvc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 刘镇 (suninformation@163.com) on 17/3/5 上午3:02
 * @version 1.0
 */
public class SecurityWebErrorProcessor extends WebErrorProcessor {

    private static final Log _LOG = LogFactory.getLog(SecurityWebErrorProcessor.class);

    @Override
    public void onError(IWebMvc owner, Throwable e) {
        Throwable _unwrapThrow = RuntimeUtils.unwrapThrow(e);
        if (_unwrapThrow instanceof SecurityPrivilegeException) {
            try {
                __doInit(owner);
                //
                __doShowErrorMsg(owner, ErrorCode.REQUEST_RESOURCE_UNAUTHORIZED, __doGetI18nMsg(Optional.SYSTEM_REQUEST_RESOURCE_UNAUTHORIZED_KEY, "请求的资源未授权或无权限")).render();
            } catch (Throwable e1) {
                _LOG.warn("", RuntimeUtils.unwrapThrow(e1));
            }
        } else {
            super.onError(owner, e);
        }
    }
}
