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
import net.ymate.module.security.ISecurityModuleCfg;
import net.ymate.module.security.ISecurityStorageAdapter;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.util.ClassUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:10
 * @version 1.0
 */
public class DefaultModuleCfg implements ISecurityModuleCfg {

    private IAuthenticatorFactory __authFactory;

    private ISecurityStorageAdapter __storageAdapter;

    private Map<String, Set<String>> __permissionFilters;

    public DefaultModuleCfg(YMP owner) {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(ISecurity.MODULE_NAME);
        //
        __authFactory = ClassUtils.impl(_moduleCfgs.get("authenticator_factory_class"), IAuthenticatorFactory.class, this.getClass());
        //
        __storageAdapter = ClassUtils.impl(_moduleCfgs.get("storage_adapter_class"), ISecurityStorageAdapter.class, this.getClass());
        //
        __permissionFilters = new HashMap<String, Set<String>>();
        for (Map.Entry<String, String> _item : _moduleCfgs.entrySet()) {
            if (StringUtils.startsWith(_item.getKey(), "permissions.")) {
                String _groupName = StringUtils.substringAfter(_item.getKey(), "permissions.");
                if (StringUtils.equalsIgnoreCase(_item.getValue(), "all")) {
                    __permissionFilters.put(_groupName, new HashSet<String>(Collections.singletonList("all")));
                } else {
                    String[] _permissions = StringUtils.split(_item.getValue(), "|");
                    if (_permissions != null && _permissions.length > 0) {
                        if (ArrayUtils.contains(_permissions, "all")) {
                            __permissionFilters.put(_groupName, new HashSet<String>(Collections.singletonList("all")));
                        } else {
                            __permissionFilters.put(_groupName, new HashSet<String>(Arrays.asList(_permissions)));
                        }
                    }
                }
            }
        }
    }

    public IAuthenticatorFactory getAuthenticatorFactory() {
        return __authFactory;
    }

    public ISecurityStorageAdapter getStorageAdapter() {
        return __storageAdapter;
    }

    public Map<String, Set<String>> getPermissionFilters() {
        return Collections.unmodifiableMap(__permissionFilters);
    }
}
