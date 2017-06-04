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

import net.ymate.platform.core.YMP;

import java.util.List;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:01
 * @version 1.0
 */
public interface ISecurity {

    String MODULE_NAME = "module.security";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    ISecurityModuleCfg getModuleCfg();

    /**
     * @return 返回模块是否已初始化
     */
    boolean isInited();

    /**
     * @return 返回所有分组权限元数据对象映射
     */
    Map<String, List<PermissionMeta>> getAllPermissions();

    /**
     * @param siteId 站点唯一标识Id
     * @return 返回指定siteId站点所拥有的分组权限元数据对象映射
     */
    Map<String, List<PermissionMeta>> getSitePermissions(String siteId);

    /**
     * @param siteId 站点唯一标识Id
     * @param uid    用户唯一标识uid
     * @return 返回指定UID用户所拥有的分组权限元数据对象映射
     */
    Map<String, List<PermissionMeta>> getUserPermissions(String siteId, String uid);

    /**
     * @param permissionMeta 权限元数据对象
     * @return 检查权限是否被过滤
     */
    boolean isFiltered(PermissionMeta permissionMeta);

    /**
     * @return 返回所有的组件元数据对象集合
     */
    ComponentsMeta[] getAllComponents();

    /**
     * @param siteId 站点唯一标识Id
     * @return 返回指定siteId站点所拥有访问权限的组件元数据对象集合
     */
    ComponentsMeta[] getSiteComponents(String siteId);

    /**
     * @param siteId 站点唯一标识Id
     * @param uid    用户唯一标识uid
     * @return 返回指定UID用户所拥有访问权限的组件元数据对象集合
     */
    ComponentsMeta[] getUserComponents(String siteId, String uid);

    /**
     * 角色
     */
    enum Role {
        INHERIT, ALL, ADMIN, OPERATOR, USER
    }

    /**
     * 逻辑类型
     */
    enum LogicType {
        INHERIT, AND, OR
    }

    /**
     * 显示状态
     */
    enum DisplayType {
        SHOW, HIDE
    }

    interface IGroup {

        /**
         * @return 组Id
         */
        String getId();

        /**
         * @return 组名称
         */
        String getName();

        /**
         * @return 所属站点Id
         */
        String getSiteId();

        /**
         * @return 所拥有的角色及权限信息
         */
        IUserAuthenticator getAuthenticator();
    }

    interface IGroupUser {

        /**
         * @return 用户Uid
         */
        String getUid();

        /**
         * @return 所属组Id
         */
        String getGroupId();

        /**
         * @return 所属站点Id
         */
        String getSiteId();
    }
}
