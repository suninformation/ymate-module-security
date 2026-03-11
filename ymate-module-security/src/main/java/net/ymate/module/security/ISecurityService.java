/*
 * Copyright 2007-2025 the original author or authors.
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

import net.ymate.module.security.base.*;
import net.ymate.platform.core.beans.annotation.Ignored;
import net.ymate.platform.core.support.IDestroyable;
import net.ymate.platform.core.support.IInitialization;

import java.util.Collection;

/**
 * @author 刘镇 (suninformation@163.com) on 2023/2/9 00:24
 * @since 1.0.0
 */
@Ignored
public interface ISecurityService extends IInitialization<ISecurity>, IDestroyable {

    /**
     * 获取所属模块
     *
     * @return 返回所属模块实例
     */
    ISecurity getOwner();

    /**
     * 刷新数据
     */
    void refresh();

    /**
     * 刷新指定用户的数据
     *
     * @param uid 用户唯一标识
     */
    void refresh(String uid);

    /**
     * 获取当前用户
     *
     * @return 返回用户
     */
    IUserInfo getCurrentUser();

    /**
     * 获取用户认证接口实例
     *
     * @param uid 用户唯一标识
     * @return 返回用户认证接口实例
     */
    IUserAuthenticator getUserAuthenticator(String uid);

    /**
     * 获取用户认证接口实例
     *
     * @param user 用户
     * @return 返回用户认证接口实例
     */
    IUserAuthenticator getUserAuthenticator(IUserInfo user);

    /**
     * 获取部门树（组织架构）
     *
     * @param deptIds 部门唯一标识集合
     * @return 返回部门树集合
     */
    Collection<IDeptInfo> getDepartmentTreeView(String... deptIds);

    /**
     * 获取权限树
     *
     * @param permissionIds 权限唯一标识集合
     * @return 返回权限树集合
     */
    Collection<IPermissionInfo> getPermissionTreeView(String... permissionIds);

    /**
     * 获取权限树
     *
     * @param permissionCodes 权限码集合
     * @return 返回权限树集合
     */
    Collection<IPermissionInfo> getPermissionTreeViewByCode(String... permissionCodes);

    /**
     * 获取指定用户
     *
     * @param uid 用户唯一标识
     * @return 返回用户
     */
    IUserInfo getUser(String uid);

    /**
     * 获取指定用户允许访问的部门
     *
     * @param uid 用户唯一标识
     * @return 返回部门集合
     */
    Collection<IDeptInfo> getUserDataScopes(String uid);

    /**
     * 获取指定用户的岗位
     *
     * @param uid 用户唯一标识
     * @return 返回岗位集合
     */
    Collection<IPostInfo> getUserPosts(String uid);

    /**
     * 获取指定用户所在组
     *
     * @param uid 用户唯一标识
     * @return 返回组集合
     */
    Collection<IGroupInfo> getUserGroups(String uid);

    /**
     * 获取指定用户拥有的权限
     *
     * @param uid 用户唯一标识
     * @return 返回权限集合
     */
    Collection<IPermissionInfo> getUserPermissions(String uid);

    // --- POST

    /**
     * 获取全部或指定唯一标识的岗位
     *
     * @param postIds 岗位唯一标识集合
     * @return 返回岗位合
     */
    Collection<IPostInfo> getPosts(String... postIds);

    /**
     * 获取全部或指定唯一代码的岗位
     *
     * @param postCodes 岗位唯一代码集合
     * @return 返回岗位合
     */
    Collection<IPostInfo> getPostsByCode(String... postCodes);

    /**
     * 获取指定岗位
     *
     * @param postId 岗位唯一标识
     * @return 返回岗位
     */
    IPostInfo getPost(String postId);

    /**
     * 获取指定岗位
     *
     * @param postCode 岗位唯一代码
     * @return 返回岗位
     */
    IPostInfo getPostByCode(String postCode);

    /**
     * 获取指定岗位用户
     *
     * @param postId 岗位唯一标识
     * @return 返回岗位用户集合
     */
    Collection<IUserInfo> getPostUsers(String postId);

    /**
     * 获取指定岗位用户
     *
     * @param postCode 岗位唯一代码
     * @return 返回岗位用户集合
     */
    Collection<IUserInfo> getPostUsersByCode(String postCode);

    // --- DEPT

    /**
     * 获取全部或指定唯一标识的部门
     *
     * @param deptIds 部门唯一标识集合
     * @return 返回部门集合
     */
    Collection<IDeptInfo> getDepartments(String... deptIds);

    /**
     * 获取指定部门
     *
     * @param deptId 部门唯一标识
     * @return 返回部门
     */
    IDeptInfo getDepartment(String deptId);

    /**
     * 获取指定部门用户
     *
     * @param deptId 部门唯一标识
     * @return 返回部门下的用户集合
     */
    Collection<IUserInfo> getDepartmentUsers(String deptId);

    // --- GROUP

    /**
     * 获取全部或指定唯一标识的组
     *
     * @param groupIds 组唯一标识集合
     * @return 返回组集合
     */
    Collection<IGroupInfo> getGroups(String... groupIds);

    /**
     * 获取指定组
     *
     * @param groupId 组唯一标识
     * @return 返回组
     */
    IGroupInfo getGroup(String groupId);

    /**
     * 获取指定组用户
     *
     * @param groupId 组唯一标识
     * @return 返回组用户集合
     */
    Collection<IUserInfo> getGroupUsers(String groupId);

    /**
     * 获取指定组角色
     *
     * @param groupId 组唯一标识
     * @return 返回组集合
     */
    Collection<IRoleInfo> getGroupRoles(String groupId);

    /**
     * 获取指定组允许访问的部门
     *
     * @param groupId 组唯一标识
     * @return 返回部门集合
     */
    Collection<IDeptInfo> getGroupDataScopes(String groupId);

    /**
     * 获取指定组拥有的权限
     *
     * @param groupId 组唯一标识
     * @return 返回权限集合
     */
    Collection<IPermissionInfo> getGroupPermissions(String groupId);

    // --- ROLE

    /**
     * 获取全部或指定唯一标识的角色
     *
     * @param roleIds 角色唯一标识集合
     * @return 返回角色集合
     */
    Collection<IRoleInfo> getRoles(String... roleIds);

    /**
     * 获取指定角色
     *
     * @param roleId 角色唯一标识
     * @return 返回角色
     */
    IRoleInfo getRole(String roleId);

    /**
     * 获取指定角色权限
     *
     * @param roleId 角色唯一标识
     * @return 返回角色权限集合
     */
    Collection<IPermissionInfo> getRolePermissions(String roleId);

    // --- PERMISSION

    /**
     * 获取全部或指定唯一标识的权限
     *
     * @param permissionIds 权限唯一标识集合
     * @return 返回权限集合
     */
    Collection<IPermissionInfo> getPermissions(String... permissionIds);

    /**
     * 获取全部或指定唯一代码的权限
     *
     * @param permissionCodes 权限码集合
     * @return 返回权限集合
     */
    Collection<IPermissionInfo> getPermissionsByCode(String... permissionCodes);
}
