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
import net.ymate.platform.commons.ReentrantLockHelper;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 权限元数据
 *
 * @author 刘镇 (suninformation@163.com) on 17/5/18 下午1:45
 * @version 1.0
 */
public class PermissionMeta {

    private static final RoleType[] ROLE_TYPES_ALL = new RoleType[]{RoleType.ADMIN, RoleType.OPERATOR, RoleType.USER};

    //
    private static final Map<Method, PermissionMeta> PERMISSION_META_MAP = new ConcurrentHashMap<>();

    private RoleType[] roleTypes;

    private String[] permissions;

    private LogicType logicType;

    public static Set<String> getPermissionCodes() {
        Set<String> codes = PERMISSION_META_MAP.values()
                .stream()
                .flatMap(permissionMeta -> Arrays.stream(permissionMeta.getPermissions()))
                .collect(Collectors.toSet());
        return Collections.unmodifiableSet(codes);
    }

    public static PermissionMeta createAndGet(Method targetMethod) throws Exception {
        if (targetMethod == null) {
            throw new NullArgumentException("targetMethod");
        }
        Permission permissionAnn = targetMethod.getAnnotation(Permission.class);
        if (permissionAnn != null) {
            return ReentrantLockHelper.putIfAbsentAsync(PERMISSION_META_MAP, targetMethod, () -> {
                Set<RoleType> roleTypes = new HashSet<>();
                Set<String> permissions = new HashSet<>();
                LogicType logicType = LogicType.AND;
                Permission parentPermissionAnn = targetMethod.getDeclaringClass().getAnnotation(Permission.class);
                if (parentPermissionAnn != null) {
                    if (!LogicType.INHERIT.equals(parentPermissionAnn.logicType())) {
                        logicType = parentPermissionAnn.logicType();
                    }
                    roleTypes.addAll(Arrays.asList(parentPermissionAnn.roleTypes()));
                    permissions.addAll(Arrays.asList(parentPermissionAnn.value()));
                    //
                    if (!LogicType.INHERIT.equals(parentPermissionAnn.logicType())) {
                        logicType = permissionAnn.logicType();
                    }
                    if (ArrayUtils.contains(permissionAnn.roleTypes(), RoleType.ALL)) {
                        roleTypes.add(RoleType.INHERIT);
                    } else if (ArrayUtils.contains(permissionAnn.roleTypes(), RoleType.INHERIT)) {
                        Arrays.stream(permissionAnn.roleTypes())
                                .filter(roleType -> !RoleType.INHERIT.equals(roleType))
                                .forEach(roleTypes::add);
                    } else {
                        roleTypes.addAll(Arrays.asList(permissionAnn.roleTypes()));
                    }
                    permissions.addAll(Arrays.asList(permissionAnn.value()));
                } else {
                    if (!LogicType.INHERIT.equals(permissionAnn.logicType())) {
                        logicType = permissionAnn.logicType();
                    }
                    roleTypes.addAll(Arrays.asList(permissionAnn.roleTypes()));
                    permissions.addAll(Arrays.asList(permissionAnn.value()));
                }
                //
                PermissionMeta permissionMeta = new PermissionMeta();
                permissionMeta.logicType = logicType;
                permissionMeta.permissions = permissions.toArray(new String[0]);
                //
                if (roleTypes.contains(RoleType.INHERIT) || roleTypes.contains(RoleType.ALL)) {
                    permissionMeta.roleTypes = ROLE_TYPES_ALL;
                } else {
                    permissionMeta.roleTypes = roleTypes.toArray(new RoleType[0]);
                }
                return permissionMeta;
            });
        }
        return null;
    }

    private PermissionMeta() {
    }

    public LogicType getLogicType() {
        return logicType;
    }

    public RoleType[] getRoleTypes() {
        return roleTypes;
    }

    public String[] getPermissions() {
        return permissions;
    }
}
