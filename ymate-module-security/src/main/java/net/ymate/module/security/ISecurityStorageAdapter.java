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
     * 保存或更新用户认证数据
     *
     * @param siteId        站点唯一标识
     * @param groupId       用户组唯一标识
     * @param authenticator 用户认证接口对象
     * @throws Exception 可能产生的任何异常
     */
    void saveOrUpdate(String siteId, String groupId, IUserAuthenticator authenticator) throws Exception;

    /**
     * 删除用户组
     *
     * @param siteId  站点唯一标识
     * @param groupId 用户组唯一标识
     * @throws Exception 可能产生的任何异常
     */
    void removeGroup(String siteId, String groupId) throws Exception;

    /**
     * 删除用户组成员
     *
     * @param siteId  站点唯一标识
     * @param groupId 用户组唯一标识
     * @param uid     用户标识ID
     * @throws Exception 可能产生的任何异常
     */
    void removeGroupUser(String siteId, String groupId, String uid) throws Exception;

    /**
     * 清理已过期的令牌
     *
     * @param uid 用户标识ID
     * @throws Exception 可能产生的任何异常
     */
    void cleanup(String uid) throws Exception;
}
