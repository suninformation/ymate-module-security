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
import net.ymate.module.security.base.IGroupDataScopeInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class GroupDataScopeInfo extends AbstractAttributeExt implements IGroupDataScopeInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String groupId;

    private String deptId;

    private Long createTime;

    public GroupDataScopeInfo() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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

    public static class Builder extends AbstractBuilder<Builder, GroupDataScopeInfo> {

        public Builder() {
            super(new GroupDataScopeInfo());
        }

        public Builder(GroupDataScopeInfo target) {
            super(target);
        }

        public String id() {
            return target.getId();
        }

        public Builder id(String id) {
            target.setId(id);
            return this;
        }

        public String groupId() {
            return target.getGroupId();
        }

        public Builder groupId(String groupId) {
            target.setGroupId(groupId);
            return this;
        }

        public String deptId() {
            return target.getDeptId();
        }

        public Builder deptId(String deptId) {
            target.setDeptId(deptId);
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
