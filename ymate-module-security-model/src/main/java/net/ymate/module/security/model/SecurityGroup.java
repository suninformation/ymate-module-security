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
package net.ymate.module.security.model;

import net.ymate.platform.core.beans.annotation.PropertyState;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.annotation.*;
import net.ymate.platform.persistence.jdbc.IConnectionHolder;
import net.ymate.platform.persistence.jdbc.support.BaseEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/05/26 下午 16:59:41
 * @version 1.0
 */
@Entity(SecurityGroup.TABLE_NAME)
public class SecurityGroup extends BaseEntity<SecurityGroup, java.lang.String> {

    private static final long serialVersionUID = 1L;

    /**
     * 根据分组名称生成记录主键字符串
     *
     * @param groupName 分组名称
     * @param createBy  创建者标识
     * @param owner     拥有者标识
     * @return 返回生成的主键字符串
     */
    public static String buildPrimaryKey(String groupName, String createBy, String owner) {
        if (StringUtils.isBlank(groupName)) {
            throw new NullArgumentException("groupName");
        }
        return DigestUtils.md5Hex(groupName + StringUtils.trimToEmpty(createBy) + StringUtils.trimToEmpty(owner));
    }

    @Id
    @Property(name = FIELDS.ID, nullable = false, length = 32)
    @PropertyState(propertyName = FIELDS.ID)
    private java.lang.String id;

    @Property(name = FIELDS.NAME, nullable = false, length = 32)
    @PropertyState(propertyName = FIELDS.NAME)
    private java.lang.String name;

    @Property(name = FIELDS.PERMISSION, length = 16383)
    @PropertyState(propertyName = FIELDS.PERMISSION)
    private java.lang.String permission;

    @Property(name = FIELDS.IS_ADMIN_ROLE, unsigned = true, length = 1)
    @Default("0")
    @PropertyState(propertyName = FIELDS.IS_ADMIN_ROLE)
    private java.lang.Integer isAdminRole;

    @Property(name = FIELDS.IS_OPERATOR_ROLE, unsigned = true, length = 1)
    @Default("0")
    @PropertyState(propertyName = FIELDS.IS_OPERATOR_ROLE)
    private java.lang.Integer isOperatorRole;

    @Property(name = FIELDS.IS_USER_ROLE, length = 1)
    @Default("0")
    @PropertyState(propertyName = FIELDS.IS_USER_ROLE)
    private java.lang.Integer isUserRole;

    @Property(name = FIELDS.TYPE, unsigned = true, length = 2)
    @Default("0")
    @PropertyState(propertyName = FIELDS.TYPE)
    private java.lang.Integer type;

    @Property(name = FIELDS.STATUS, unsigned = true, length = 2)
    @Default("0")
    @PropertyState(propertyName = FIELDS.STATUS)
    private java.lang.Integer status;

    @Property(name = FIELDS.CREATE_BY, length = 32)
    @PropertyState(propertyName = FIELDS.CREATE_BY)
    private java.lang.String createBy;

    @Property(name = FIELDS.OWNER, length = 32)
    @PropertyState(propertyName = FIELDS.OWNER)
    private java.lang.String owner;

    @Property(name = FIELDS.CREATE_TIME, nullable = false, length = 13)
    @PropertyState(propertyName = FIELDS.CREATE_TIME)
    @Readonly
    private java.lang.Long createTime;

    @Property(name = FIELDS.LAST_MODIFY_TIME, length = 13)
    @Default("0")
    @PropertyState(propertyName = FIELDS.LAST_MODIFY_TIME)
    private java.lang.Long lastModifyTime;

    /**
     * 构造器
     */
    public SecurityGroup() {
    }

    /**
     * 构造器
     *
     * @param id
     * @param name
     * @param createTime
     */
    public SecurityGroup(java.lang.String id, java.lang.String name, java.lang.Long createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
    }

    /**
     * 构造器
     *
     * @param id
     * @param name
     * @param permission
     * @param isAdminRole
     * @param isOperatorRole
     * @param isUserRole
     * @param type
     * @param status
     * @param createBy
     * @param owner
     * @param createTime
     * @param lastModifyTime
     */
    public SecurityGroup(java.lang.String id, java.lang.String name, java.lang.String permission, java.lang.Integer isAdminRole, java.lang.Integer isOperatorRole, java.lang.Integer isUserRole, java.lang.Integer type, java.lang.Integer status, java.lang.String createBy, java.lang.String owner, java.lang.Long createTime, java.lang.Long lastModifyTime) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.isAdminRole = isAdminRole;
        this.isOperatorRole = isOperatorRole;
        this.isUserRole = isUserRole;
        this.type = type;
        this.status = status;
        this.createBy = createBy;
        this.owner = owner;
        this.createTime = createTime;
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public java.lang.String getId() {
        return id;
    }

    @Override
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * @return the name
     */
    public java.lang.String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * @return the permission
     */
    public java.lang.String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(java.lang.String permission) {
        this.permission = permission;
    }

    /**
     * @return the isAdminRole
     */
    public java.lang.Integer getIsAdminRole() {
        return isAdminRole;
    }

    /**
     * @param isAdminRole the isAdminRole to set
     */
    public void setIsAdminRole(java.lang.Integer isAdminRole) {
        this.isAdminRole = isAdminRole;
    }

    /**
     * @return the isOperatorRole
     */
    public java.lang.Integer getIsOperatorRole() {
        return isOperatorRole;
    }

    /**
     * @param isOperatorRole the isOperatorRole to set
     */
    public void setIsOperatorRole(java.lang.Integer isOperatorRole) {
        this.isOperatorRole = isOperatorRole;
    }

    /**
     * @return the isUserRole
     */
    public java.lang.Integer getIsUserRole() {
        return isUserRole;
    }

    /**
     * @param isUserRole the isUserRole to set
     */
    public void setIsUserRole(java.lang.Integer isUserRole) {
        this.isUserRole = isUserRole;
    }

    /**
     * @return the type
     */
    public java.lang.Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(java.lang.Integer type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public java.lang.Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }

    /**
     * @return the createBy
     */
    public java.lang.String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy the createBy to set
     */
    public void setCreateBy(java.lang.String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return the owner
     */
    public java.lang.String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(java.lang.String owner) {
        this.owner = owner;
    }

    /**
     * @return the createTime
     */
    public java.lang.Long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(java.lang.Long createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the lastModifyTime
     */
    public java.lang.Long getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * @param lastModifyTime the lastModifyTime to set
     */
    public void setLastModifyTime(java.lang.Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    //
    // Chain
    //

    public static SecurityGroupBuilder builder() {
        return new SecurityGroupBuilder();
    }

    public SecurityGroupBuilder bind() {
        return new SecurityGroupBuilder(this);
    }

    public static class SecurityGroupBuilder {

        private SecurityGroup _model;

        public SecurityGroupBuilder() {
            _model = new SecurityGroup();
        }

        public SecurityGroupBuilder(SecurityGroup model) {
            _model = model;
        }

        public SecurityGroup build() {
            return _model;
        }


        public IConnectionHolder connectionHolder() {
            return _model.getConnectionHolder();
        }

        public SecurityGroupBuilder connectionHolder(IConnectionHolder connectionHolder) {
            _model.setConnectionHolder(connectionHolder);
            return this;
        }

        public String dataSourceName() {
            return _model.getDataSourceName();
        }

        public SecurityGroupBuilder dataSourceName(String dsName) {
            _model.setDataSourceName(dsName);
            return this;
        }

        public IShardingable shardingable() {
            return _model.getShardingable();
        }

        public SecurityGroupBuilder shardingable(IShardingable shardingable) {
            _model.setShardingable(shardingable);
            return this;
        }

        public java.lang.String id() {
            return _model.getId();
        }

        public SecurityGroupBuilder id(java.lang.String id) {
            _model.setId(id);
            return this;
        }

        public java.lang.String name() {
            return _model.getName();
        }

        public SecurityGroupBuilder name(java.lang.String name) {
            _model.setName(name);
            return this;
        }

        public java.lang.String permission() {
            return _model.getPermission();
        }

        public SecurityGroupBuilder permission(java.lang.String permission) {
            _model.setPermission(permission);
            return this;
        }

        public java.lang.Integer isAdminRole() {
            return _model.getIsAdminRole();
        }

        public SecurityGroupBuilder isAdminRole(java.lang.Integer isAdminRole) {
            _model.setIsAdminRole(isAdminRole);
            return this;
        }

        public java.lang.Integer isOperatorRole() {
            return _model.getIsOperatorRole();
        }

        public SecurityGroupBuilder isOperatorRole(java.lang.Integer isOperatorRole) {
            _model.setIsOperatorRole(isOperatorRole);
            return this;
        }

        public java.lang.Integer isUserRole() {
            return _model.getIsUserRole();
        }

        public SecurityGroupBuilder isUserRole(java.lang.Integer isUserRole) {
            _model.setIsUserRole(isUserRole);
            return this;
        }

        public java.lang.Integer type() {
            return _model.getType();
        }

        public SecurityGroupBuilder type(java.lang.Integer type) {
            _model.setType(type);
            return this;
        }

        public java.lang.Integer status() {
            return _model.getStatus();
        }

        public SecurityGroupBuilder status(java.lang.Integer status) {
            _model.setStatus(status);
            return this;
        }

        public java.lang.String createBy() {
            return _model.getCreateBy();
        }

        public SecurityGroupBuilder createBy(java.lang.String createBy) {
            _model.setCreateBy(createBy);
            return this;
        }

        public java.lang.String owner() {
            return _model.getOwner();
        }

        public SecurityGroupBuilder owner(java.lang.String owner) {
            _model.setOwner(owner);
            return this;
        }

        public java.lang.Long createTime() {
            return _model.getCreateTime();
        }

        public SecurityGroupBuilder createTime(java.lang.Long createTime) {
            _model.setCreateTime(createTime);
            return this;
        }

        public java.lang.Long lastModifyTime() {
            return _model.getLastModifyTime();
        }

        public SecurityGroupBuilder lastModifyTime(java.lang.Long lastModifyTime) {
            _model.setLastModifyTime(lastModifyTime);
            return this;
        }

    }

    /**
     * SecurityGroup 字段常量表
     */
    public class FIELDS {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String PERMISSION = "permission";
        public static final String IS_ADMIN_ROLE = "is_admin_role";
        public static final String IS_OPERATOR_ROLE = "is_operator_role";
        public static final String IS_USER_ROLE = "is_user_role";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String CREATE_BY = "create_by";
        public static final String OWNER = "owner";
        public static final String CREATE_TIME = "create_time";
        public static final String LAST_MODIFY_TIME = "last_modify_time";
    }

    public static final String TABLE_NAME = "security_group";

}
