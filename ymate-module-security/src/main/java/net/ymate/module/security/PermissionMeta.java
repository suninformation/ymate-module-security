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

import net.ymate.module.security.annotation.Permission;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 权限元数据
 *
 * @author 刘镇 (suninformation@163.com) on 17/5/18 下午1:45
 * @version 1.0
 */
public class PermissionMeta {

    private static final ISecurity.Role[] __ROLE_ALL;

    private static Map<Method, PermissionMeta> __PERMISSION_CACHES;

    static {
        __ROLE_ALL = new ISecurity.Role[]{ISecurity.Role.ADMIN, ISecurity.Role.OPERATOR, ISecurity.Role.USER};
        //
        __PERMISSION_CACHES = new ConcurrentHashMap<Method, PermissionMeta>();
    }

    private String __groupName;

    private String __name;

    private ISecurity.Role[] __roles;

    private String[] __permissions;

    private ISecurity.LogicType __logicType;

    public static PermissionMeta createIfNeed(Method targetMethod) throws Exception {
        PermissionMeta _meta = null;
        //
        Permission _method = targetMethod.getAnnotation(Permission.class);
        //
        if (_method != null) {
            Set<ISecurity.Role> _roles = new HashSet<ISecurity.Role>();
            Set<String> _permissions = new HashSet<String>();
            //
            String _groupName = "default";
            ISecurity.LogicType _logicType = ISecurity.LogicType.OR;
            //
            Permission _parent = targetMethod.getDeclaringClass().getAnnotation(Permission.class);
            if (_parent != null) {
                // 首先处理类级注解
                if (StringUtils.isNotBlank(_parent.name())) {
                    _groupName = _parent.name();
                }
                if (!ISecurity.LogicType.INHERIT.equals(_parent.logicType())) {
                    _logicType = _parent.logicType();
                }
                _roles.addAll(Arrays.asList(_parent.roles()));
                _permissions.addAll(Arrays.asList(_parent.value()));
                //
                // 然后处理方法级注解
                if (!ISecurity.LogicType.INHERIT.equals(_parent.logicType())) {
                    _logicType = _method.logicType();
                }
                if (ArrayUtils.contains(_method.roles(), ISecurity.Role.ALL)) {
                    _roles.add(ISecurity.Role.INHERIT);
                } else if (ArrayUtils.contains(_method.roles(), ISecurity.Role.INHERIT)) {
                    for (ISecurity.Role _item : _method.roles()) {
                        if (!ISecurity.Role.INHERIT.equals(_item)) {
                            _roles.add(_item);
                        }
                    }
                } else {
                    _roles.addAll(Arrays.asList(_method.roles()));
                }
                _permissions.addAll(Arrays.asList(_method.value()));
            } else {
                // 处理方法级注解
                if (!ISecurity.LogicType.INHERIT.equals(_method.logicType())) {
                    _logicType = _method.logicType();
                }
                _roles.addAll(Arrays.asList(_method.roles()));
                _permissions.addAll(Arrays.asList(_method.value()));
            }
            //
            _meta = new PermissionMeta();
            _meta.__name = StringUtils.defaultIfBlank(_method.name(), "default");
            _meta.__groupName = _groupName;
            _meta.__logicType = _logicType;
            _meta.__permissions = _permissions.toArray(new String[_permissions.size()]);
            //
            if (_roles.contains(ISecurity.Role.INHERIT) || _roles.contains(ISecurity.Role.ALL)) {
                _meta.__roles = __ROLE_ALL;
            } else {
                _meta.__roles = _roles.toArray(new ISecurity.Role[_roles.size()]);
            }
            //
            __PERMISSION_CACHES.put(targetMethod, _meta);
        }
        return _meta;
    }

    public static PermissionMeta bind(Method targetMethod) throws Exception {
        PermissionMeta _meta = null;
        if (targetMethod != null) {
            _meta = __PERMISSION_CACHES.get(targetMethod);
        }
        return _meta;
    }

    public static Collection<PermissionMeta> getPermissionMetas() {
        return Collections.unmodifiableCollection(__PERMISSION_CACHES.values());
    }

    private PermissionMeta() {
    }

    public String getName() {
        return __name;
    }

    public String getGroupName() {
        return __groupName;
    }

    public ISecurity.LogicType getLogicType() {
        return __logicType;
    }

    public ISecurity.Role[] getRoles() {
        return __roles;
    }

    public String[] getPermissions() {
        return __permissions;
    }
}
