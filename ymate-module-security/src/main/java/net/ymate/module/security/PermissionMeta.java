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

import net.ymate.module.security.annotation.LogicType;
import net.ymate.module.security.annotation.Permission;
import net.ymate.module.security.annotation.RoleType;
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

    private static final RoleType[] __ROLE_ALL;

    private static Map<Method, PermissionMeta> __PERMISSION_CACHES;

    static {
        __ROLE_ALL = new RoleType[]{RoleType.ADMIN, RoleType.OPERATOR, RoleType.USER};
        //
        __PERMISSION_CACHES = new ConcurrentHashMap<Method, PermissionMeta>();
    }

    private String __groupName;

    private String __name;

    private RoleType[] __roles;

    private String[] __permissions;

    private LogicType __logicType;

    public static PermissionMeta createIfNeed(Method targetMethod) throws Exception {
        PermissionMeta _meta = null;
        //
        Permission _method = targetMethod.getAnnotation(Permission.class);
        //
        if (_method != null) {
            Set<RoleType> _roles = new HashSet<RoleType>();
            Set<String> _permissions = new HashSet<String>();
            //
            String _groupName = "default";
            LogicType _logicType = LogicType.OR;
            //
            Permission _parent = targetMethod.getDeclaringClass().getAnnotation(Permission.class);
            if (_parent != null) {
                // 首先处理类级注解
                if (StringUtils.isNotBlank(_parent.name())) {
                    _groupName = _parent.name();
                }
                if (!LogicType.INHERIT.equals(_parent.logicType())) {
                    _logicType = _parent.logicType();
                }
                _roles.addAll(Arrays.asList(_parent.roles()));
                _permissions.addAll(Arrays.asList(_parent.value()));
                //
                // 然后处理方法级注解
                if (!LogicType.INHERIT.equals(_parent.logicType())) {
                    _logicType = _method.logicType();
                }
                if (ArrayUtils.contains(_method.roles(), RoleType.ALL)) {
                    _roles.add(RoleType.INHERIT);
                } else if (ArrayUtils.contains(_method.roles(), RoleType.INHERIT)) {
                    for (RoleType _item : _method.roles()) {
                        if (!RoleType.INHERIT.equals(_item)) {
                            _roles.add(_item);
                        }
                    }
                } else {
                    _roles.addAll(Arrays.asList(_method.roles()));
                }
                _permissions.addAll(Arrays.asList(_method.value()));
            } else {
                // 处理方法级注解
                if (!LogicType.INHERIT.equals(_method.logicType())) {
                    _logicType = _method.logicType();
                }
                _roles.addAll(Arrays.asList(_method.roles()));
                _permissions.addAll(Arrays.asList(_method.value()));
            }
            //
            _meta = new PermissionMeta();
            _meta.__name = StringUtils.defaultIfBlank(_method.name(), "default").toUpperCase();
            _meta.__groupName = StringUtils.lowerCase(_groupName);
            _meta.__logicType = _logicType;
            _meta.__permissions = _permissions.toArray(new String[0]);
            //
            if (_roles.contains(RoleType.INHERIT) || _roles.contains(RoleType.ALL)) {
                _meta.__roles = __ROLE_ALL;
            } else {
                _meta.__roles = _roles.toArray(new RoleType[0]);
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

    /**
     * @return 按分组的方式返回所有权限元数据对象映射
     */
    public static Map<String, List<PermissionMeta>> getPermissionMetasWithGroup() {
        Map<String, List<PermissionMeta>> _permissionMetas = new HashMap<String, List<PermissionMeta>>();
        for (PermissionMeta _meta : PermissionMeta.getPermissionMetas()) {
            List<PermissionMeta> _values = _permissionMetas.get(_meta.getGroupName());
            if (_values == null) {
                _values = new ArrayList<PermissionMeta>();
                _values.add(_meta);
                _permissionMetas.put(_meta.getGroupName(), _values);
            } else {
                _values.add(_meta);
            }
        }
        return Collections.unmodifiableMap(_permissionMetas);
    }

    private PermissionMeta() {
    }

    public String getName() {
        return __name;
    }

    public String getGroupName() {
        return __groupName;
    }

    public LogicType getLogicType() {
        return __logicType;
    }

    public RoleType[] getRoles() {
        return __roles;
    }

    public String[] getPermissions() {
        return __permissions;
    }
}
