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

/**
 * @author 刘镇 (suninformation@163.com) on 2017/05/26 下午 16:59:41
 * @version 1.0
 */
@Entity("security_group_user")
public class SecurityGroupUser extends BaseEntity<SecurityGroupUser, java.lang.String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Property(name = "id", nullable = false, length = 32)
    @PropertyState(propertyName = "id")
    private java.lang.String id;

    @Property(name = "group_id", nullable = false, length = 32)
    @PropertyState(propertyName = "group_id")
    private java.lang.String groupId;

    @Property(name = "uid", nullable = false, length = 32)
    @PropertyState(propertyName = "uid")
    private java.lang.String uid;

    @Property(name = "type", unsigned = true, length = 2)
    @Default("0")
    @PropertyState(propertyName = "type")
    private java.lang.Integer type;

    @Property(name = "status", unsigned = true, length = 2)
    @Default("0")
    @PropertyState(propertyName = "status")
    private java.lang.Integer status;

    @Property(name = "create_time", nullable = false, length = 13)
    @PropertyState(propertyName = "create_time")
    @Readonly
    private java.lang.Long createTime;

    @Property(name = "last_modify_time", length = 13)
    @Default("0")
    @PropertyState(propertyName = "last_modify_time")
    private java.lang.Long lastModifyTime;

    /**
     * 构造器
     */
    public SecurityGroupUser() {
    }

    /**
     * 构造器
     *
     * @param id
     * @param groupId
     * @param uid
     * @param createTime
     */
    public SecurityGroupUser(java.lang.String id, java.lang.String groupId, java.lang.String uid, java.lang.Long createTime) {
        this.id = id;
        this.groupId = groupId;
        this.uid = uid;
        this.createTime = createTime;
    }

    /**
     * 构造器
     *
     * @param id
     * @param groupId
     * @param uid
     * @param type
     * @param status
     * @param createTime
     * @param lastModifyTime
     */
    public SecurityGroupUser(java.lang.String id, java.lang.String groupId, java.lang.String uid, java.lang.Integer type, java.lang.Integer status, java.lang.Long createTime, java.lang.Long lastModifyTime) {
        this.id = id;
        this.groupId = groupId;
        this.uid = uid;
        this.type = type;
        this.status = status;
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
     * @return the groupId
     */
    public java.lang.String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the uid
     */
    public java.lang.String getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(java.lang.String uid) {
        this.uid = uid;
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

    public static SecurityGroupUserBuilder builder() {
        return new SecurityGroupUserBuilder();
    }

    public SecurityGroupUserBuilder bind() {
        return new SecurityGroupUserBuilder(this);
    }

    public static class SecurityGroupUserBuilder {

        private SecurityGroupUser _model;

        public SecurityGroupUserBuilder() {
            _model = new SecurityGroupUser();
        }

        public SecurityGroupUserBuilder(SecurityGroupUser model) {
            _model = model;
        }

        public SecurityGroupUser build() {
            return _model;
        }


        public IConnectionHolder connectionHolder() {
            return _model.getConnectionHolder();
        }

        public SecurityGroupUserBuilder connectionHolder(IConnectionHolder connectionHolder) {
            _model.setConnectionHolder(connectionHolder);
            return this;
        }

        public String dataSourceName() {
            return _model.getDataSourceName();
        }

        public SecurityGroupUserBuilder dataSourceName(String dsName) {
            _model.setDataSourceName(dsName);
            return this;
        }

        public IShardingable shardingable() {
            return _model.getShardingable();
        }

        public SecurityGroupUserBuilder shardingable(IShardingable shardingable) {
            _model.setShardingable(shardingable);
            return this;
        }

        public java.lang.String id() {
            return _model.getId();
        }

        public SecurityGroupUserBuilder id(java.lang.String id) {
            _model.setId(id);
            return this;
        }

        public java.lang.String groupId() {
            return _model.getGroupId();
        }

        public SecurityGroupUserBuilder groupId(java.lang.String groupId) {
            _model.setGroupId(groupId);
            return this;
        }

        public java.lang.String uid() {
            return _model.getUid();
        }

        public SecurityGroupUserBuilder uid(java.lang.String uid) {
            _model.setUid(uid);
            return this;
        }

        public java.lang.Integer type() {
            return _model.getType();
        }

        public SecurityGroupUserBuilder type(java.lang.Integer type) {
            _model.setType(type);
            return this;
        }

        public java.lang.Integer status() {
            return _model.getStatus();
        }

        public SecurityGroupUserBuilder status(java.lang.Integer status) {
            _model.setStatus(status);
            return this;
        }

        public java.lang.Long createTime() {
            return _model.getCreateTime();
        }

        public SecurityGroupUserBuilder createTime(java.lang.Long createTime) {
            _model.setCreateTime(createTime);
            return this;
        }

        public java.lang.Long lastModifyTime() {
            return _model.getLastModifyTime();
        }

        public SecurityGroupUserBuilder lastModifyTime(java.lang.Long lastModifyTime) {
            _model.setLastModifyTime(lastModifyTime);
            return this;
        }

    }

    /**
     * SecurityGroupUser 字段常量表
     */
    public class FIELDS {
        public static final String ID = "id";
        public static final String GROUP_ID = "group_id";
        public static final String UID = "uid";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String CREATE_TIME = "create_time";
        public static final String LAST_MODIFY_TIME = "last_modify_time";
    }

    public static final String TABLE_NAME = "security_group_user";

}
