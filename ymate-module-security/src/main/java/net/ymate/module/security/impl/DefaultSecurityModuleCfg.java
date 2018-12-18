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
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.support.IConfigReader;
import net.ymate.platform.core.support.impl.MapSafeConfigReader;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:10
 * @version 1.0
 */
public class DefaultSecurityModuleCfg implements ISecurityModuleCfg {

    private String __cacheNamePrefix;

    private IAuthenticatorFactory __authFactory;

    private Map<String, Set<String>> __permissionFilters;

    public DefaultSecurityModuleCfg(YMP owner) {
        IConfigReader _moduleCfg = MapSafeConfigReader.bind(owner.getConfig().getModuleConfigs(ISecurity.MODULE_NAME));
        //
        __cacheNamePrefix = _moduleCfg.getString(CACHE_NAME_PREFIX);
        //
        __authFactory = _moduleCfg.getClassImpl(AUTHENTICATOR_FACTORY_CLASS, IAuthenticatorFactory.class);
        //
        __permissionFilters = new HashMap<String, Set<String>>();
        for (Map.Entry<String, String> _item : _moduleCfg.getMap(PERMISSIONS_PREFIX).entrySet()) {
            if (StringUtils.startsWith(_item.getKey(), PERMISSIONS_PREFIX)) {
                String _groupName = StringUtils.lowerCase(_item.getKey());
                if (StringUtils.equalsIgnoreCase(_item.getValue(), PERMISSIONS_ALL)) {
                    __permissionFilters.put(_groupName, new HashSet<String>(Collections.singletonList(PERMISSIONS_ALL)));
                } else {
                    if (StringUtils.isNotBlank(_item.getValue())) {
                        if (StringUtils.equalsIgnoreCase(_item.getValue(), PERMISSIONS_ALL)) {
                            __permissionFilters.put(_groupName, new HashSet<String>(Collections.singletonList(PERMISSIONS_ALL)));
                        } else {
                            String[] _permissions = StringUtils.split(_item.getValue(), "|");
                            __permissionFilters.put(_groupName, new HashSet<String>(Arrays.asList(_permissions)));
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getCacheNamePrefix() {
        return __cacheNamePrefix;
    }

    @Override
    public IAuthenticatorFactory getAuthenticatorFactory() {
        return __authFactory;
    }

    @Override
    public Map<String, Set<String>> getPermissionFilters() {
        return Collections.unmodifiableMap(__permissionFilters);
    }
}
