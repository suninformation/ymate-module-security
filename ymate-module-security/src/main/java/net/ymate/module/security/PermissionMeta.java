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
import net.ymate.platform.commons.util.ClassUtils;
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

    private static LogicType parseLogicType(Permission permissionAnn, Permission classPermissionAnn, Permission packagePermissionAnn) {
        LogicType logicType = LogicType.AND;
        if (!LogicType.INHERIT.equals(permissionAnn.logicType())) {
            logicType = permissionAnn.logicType();
        } else if (classPermissionAnn != null && !LogicType.INHERIT.equals(classPermissionAnn.logicType())) {
            logicType = classPermissionAnn.logicType();
        } else if (packagePermissionAnn != null && !LogicType.INHERIT.equals(packagePermissionAnn.logicType())) {
            logicType = packagePermissionAnn.logicType();
        }
        return logicType;
    }

    public static PermissionMeta createAndGet(Method targetMethod) throws Exception {
        if (targetMethod == null) {
            throw new NullArgumentException("targetMethod");
        }
        Permission permissionAnn = targetMethod.getAnnotation(Permission.class);
        if (permissionAnn != null) {
            return ReentrantLockHelper.putIfAbsentAsync(PERMISSION_META_MAP, targetMethod, () -> {
                Permission packagePermissionAnn = ClassUtils.getPackageAnnotation(targetMethod.getDeclaringClass(), Permission.class);
                Permission classPermissionAnn = targetMethod.getDeclaringClass().getAnnotation(Permission.class);
                // LogicType
                LogicType logicType = parseLogicType(permissionAnn, classPermissionAnn, packagePermissionAnn);
                // RoleType
                Set<RoleType> roleTypes = new HashSet<>();
                if (ArrayUtils.contains(permissionAnn.roleTypes(), RoleType.ALL)) {
                    roleTypes.add(RoleType.INHERIT);
                } else if (ArrayUtils.contains(permissionAnn.roleTypes(), RoleType.INHERIT)) {
                    if (classPermissionAnn != null) {
                        if (ArrayUtils.contains(classPermissionAnn.roleTypes(), RoleType.ALL)) {
                            roleTypes.add(RoleType.INHERIT);
                        } else if (ArrayUtils.contains(classPermissionAnn.roleTypes(), RoleType.INHERIT)) {
                            if (packagePermissionAnn != null) {
                                Arrays.stream(packagePermissionAnn.roleTypes())
                                        .filter(roleType -> !RoleType.INHERIT.equals(roleType))
                                        .forEach(roleTypes::add);
                            }
                        } else {
                            roleTypes.addAll(Arrays.asList(classPermissionAnn.roleTypes()));
                        }
                    } else if (packagePermissionAnn != null) {
                        if (ArrayUtils.contains(packagePermissionAnn.roleTypes(), RoleType.ALL)) {
                            roleTypes.add(RoleType.INHERIT);
                        } else {
                            Arrays.stream(packagePermissionAnn.roleTypes())
                                    .filter(roleType -> !RoleType.INHERIT.equals(roleType))
                                    .forEach(roleTypes::add);
                        }
                    }
                } else {
                    Arrays.stream(permissionAnn.roleTypes())
                            .filter(roleType -> !RoleType.INHERIT.equals(roleType))
                            .forEach(roleTypes::add);
                }
                // Permission
                Set<String> permissions = new HashSet<>(Arrays.asList(permissionAnn.value()));
                if (classPermissionAnn != null) {
                    permissions.addAll(Arrays.asList(classPermissionAnn.value()));
                }
                if (packagePermissionAnn != null) {
                    permissions.addAll(Arrays.asList(packagePermissionAnn.value()));
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
