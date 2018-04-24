/*
 * Copyright 2007-2018 the original author or authors.
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
package net.ymate.module.security.repository;

import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.model.SecurityGroup;
import net.ymate.module.security.model.SecurityGroupUser;
import net.ymate.platform.persistence.IResultSet;

import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 2018/2/10 下午5:08
 * @version 1.0
 */
public interface ISecurityRepository {

    /**
     * 读取用户认证数据
     *
     * @param uid 用户标识ID
     * @return 返回用户认证接口对象, 若不存在则返回null
     * @throws Exception 可能产生的任何异常
     */
    IUserAuthenticator getUserAuthenticator(String uid) throws Exception;

    /**
     * @param uid 用户标识ID
     * @return 返回指定用户所在组集合
     * @throws Exception 可能产生的任何异常
     */
    List<SecurityGroup> getUserGroups(String uid) throws Exception;

    /**
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 返回组集合
     * @throws Exception 可能产生的任何异常
     */
    IResultSet<SecurityGroup> getGroups(int page, int pageSize) throws Exception;

    /**
     * @param groupName 组名称
     * @return 添加组若已存在或添加成功则返回组接口对象
     * @throws Exception 可能产生的任何异常
     */
    SecurityGroup addGroup(String groupName) throws Exception;

    /**
     * 保存或更新组用户认证数据
     *
     * @param groupId        组唯一标识
     * @param isAdminRole    是否管理员角色
     * @param isOperatorRole 是否操作员角色
     * @param isUserRole     是否普通用户角色
     * @param permissions    权限集合
     * @throws Exception 可能产生的任何异常
     */
    void saveOrUpdateGroup(String groupId, boolean isAdminRole, boolean isOperatorRole, boolean isUserRole, String[] permissions) throws Exception;

    /**
     * @param groupId  组唯一标识
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 返回指定组下用户集合
     * @throws Exception 可能产生的任何异常
     */
    IResultSet<SecurityGroupUser> getGroupUsers(String groupId, int page, int pageSize) throws Exception;

    /**
     * 添加组用户
     *
     * @param groupId 组唯一标识
     * @param uid     用户标识ID
     * @throws Exception 可能产生的任何异常
     */
    void addGroupUser(String groupId, String uid) throws Exception;

    /**
     * 删除组成员
     *
     * @param groupId 组唯一标识
     * @param uid     用户标识ID
     * @throws Exception 可能产生的任何异常
     */
    void removeGroupUser(String groupId, String uid) throws Exception;

    /**
     * 删除组
     *
     * @param groupId 组唯一标识
     * @throws Exception 可能产生的任何异常
     */
    void removeGroup(String groupId) throws Exception;

    /**
     * 清理组用户成员
     *
     * @param groupId 组唯一标识
     * @throws Exception 可能产生的任何异常
     */
    void cleanupGroup(String groupId) throws Exception;
}
