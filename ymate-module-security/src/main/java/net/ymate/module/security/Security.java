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
package net.ymate.module.security;

import net.ymate.module.security.handle.SecurityHandler;
import net.ymate.module.security.impl.DefaultSecurityModuleCfg;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:06
 * @version 1.0
 */
@Module
public class Security implements IModule, ISecurity {

    private static final Log _LOG = LogFactory.getLog(Security.class);

    public static final Version VERSION = new Version(1, 0, 0, Security.class.getPackage().getImplementationVersion(), Version.VersionType.Alpha);

    private static volatile ISecurity __instance;

    private YMP __owner;

    private ISecurityModuleCfg __moduleCfg;

    private boolean __inited;

    public static ISecurity get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Security.class);
                }
            }
        }
        return __instance;
    }

    @Override
    public String getName() {
        return ISecurity.MODULE_NAME;
    }

    @Override
    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-module-security-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultSecurityModuleCfg(owner);
            __owner.registerHandler(net.ymate.module.security.annotation.Security.class, new SecurityHandler());
            //
            if (__moduleCfg.getAuthenticatorFactory() != null) {
                __moduleCfg.getAuthenticatorFactory().init(this);
            }
            //
            __inited = true;
        }
    }

    @Override
    public boolean isInited() {
        return __inited;
    }

    @Override
    public boolean isFiltered(PermissionMeta permissionMeta) {
        String _groupName = StringUtils.lowerCase(permissionMeta.getGroupName());
        Set<String> _permissions = __moduleCfg.getPermissionFilters().get(_groupName);
        return _permissions != null && (_permissions.contains(ISecurityModuleCfg.PERMISSIONS_ALL) || _permissions.contains(permissionMeta.getName()));
    }

    @Override
    public YMP getOwner() {
        return __owner;
    }

    @Override
    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            if (__moduleCfg.getAuthenticatorFactory() != null) {
                __moduleCfg.getAuthenticatorFactory().destroy();
            }
            //
            __moduleCfg = null;
            __owner = null;
        }
    }

    @Override
    public ISecurityModuleCfg getModuleCfg() {
        return __moduleCfg;
    }
}
