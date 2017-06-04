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

import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/27 上午10:56
 * @version 1.0
 */
public interface ISecurityStorageAdapter {

    /**
     * 初始化存储适配器
     *
     * @param owner 所属模块管理器实例
     * @throws Exception 可能产生的任何异常
     */
    void init(ISecurity owner) throws Exception;

    void destroy() throws Exception;

    /**
     * 读取用户认证数据
     *
     * @param siteId 站点唯一标识
     * @param uid    用户标识ID
     * @return 返回用户认证接口对象, 若不存在则返回null
     * @throws Exception 可能产生的任何异常
     */
    IUserAuthenticator getUserAuthenticator(String siteId, String uid) throws Exception;

    /**
     * @param siteId 站点唯一标识
     * @param uid    用户标识ID
     * @return 返回指定站点的用户所在组集合
     * @throws Exception 可能产生的任何异常
     */
    List<ISecurity.IGroup> getUserGroups(String siteId, String uid) throws Exception;

    /**
     * @param siteId 站点唯一标识
     * @return 返回指定站点的组集合
     * @throws Exception 可能产生的任何异常
     */
    List<ISecurity.IGroup> getGroups(String siteId) throws Exception;

    /**
     * @param siteId    站点唯一标识
     * @param groupName 组名称
     * @return 添加组若已存在或添加成功则返回组接口对象
     * @throws Exception 可能产生的任何异常
     */
    ISecurity.IGroup addGroup(String siteId, String groupName) throws Exception;

    /**
     * 保存或更新组用户认证数据
     *
     * @param groupId       组唯一标识
     * @param authenticator 用户认证接口对象
     * @throws Exception 可能产生的任何异常
     */
    void saveOrUpdateGroup(String groupId, IUserAuthenticator authenticator) throws Exception;

    /**
     * @param groupId  组唯一标识
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 返回指定组下用户集合
     * @throws Exception 可能产生的任何异常
     */
    List<ISecurity.IGroupUser> getGroupUsers(String groupId, int page, int pageSize) throws Exception;

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
