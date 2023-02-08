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
package net.ymate.module.security.base.impl;

import net.ymate.module.security.base.AbstractAttributeExt;
import net.ymate.module.security.base.IRolePermissionInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class RolePermissionInfo extends AbstractAttributeExt implements IRolePermissionInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String roleId;

    private String permissionId;

    private Long createTime;

    public RolePermissionInfo() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public Builder bind() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractBuilder<Builder, RolePermissionInfo> {

        public Builder() {
            super(new RolePermissionInfo());
        }

        public Builder(RolePermissionInfo target) {
            super(target);
        }

        public String id() {
            return target.getId();
        }

        public Builder id(String id) {
            target.setId(id);
            return this;
        }

        public String roleId() {
            return target.getRoleId();
        }

        public Builder roleId(String roleId) {
            target.setRoleId(roleId);
            return this;
        }

        public String permissionId() {
            return target.getPermissionId();
        }

        public Builder permissionId(String permissionId) {
            target.setPermissionId(permissionId);
            return this;
        }

        public Long createTime() {
            return target.getCreateTime();
        }

        public Builder createTime(Long createTime) {
            target.setCreateTime(createTime);
            return this;
        }
    }
}
