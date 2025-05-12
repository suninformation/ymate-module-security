/*
 * Copyright 2025 the original author or authors.
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
import net.ymate.module.security.base.IDeptInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 00:23:30
 * @since 1.0.0
 */
public class DeptInfo extends AbstractAttributeExt implements IDeptInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String rootId;

    private String parentId;

    private String managerUid;

    private String name;

    private String description;

    private String path;

    private long depth;

    private float sort;

    private Type type;

    public DeptInfo() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getManagerUid() {
        return managerUid;
    }

    public void setManagerUid(String managerUid) {
        this.managerUid = managerUid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public long getDepth() {
        return depth;
    }

    public void setDepth(long depth) {
        this.depth = depth;
    }

    @Override
    public float getSort() {
        return sort;
    }

    public void setSort(float sort) {
        this.sort = sort;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public Builder bind() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractBuilder<Builder, DeptInfo> {

        public Builder() {
            super(new DeptInfo());
        }

        public Builder(DeptInfo target) {
            super(target);
        }

        public String id() {
            return target.getId();
        }

        public Builder id(String id) {
            target.setId(id);
            return this;
        }

        public String rootId() {
            return target.getRootId();
        }

        public Builder rootId(String rootId) {
            target.setRootId(rootId);
            return this;
        }

        public String parentId() {
            return target.getParentId();
        }

        public Builder parentId(String parentId) {
            target.setParentId(parentId);
            return this;
        }

        public String managerUid() {
            return target.getManagerUid();
        }

        public Builder managerUid(String managerUid) {
            target.setManagerUid(managerUid);
            return this;
        }

        public String name() {
            return target.getName();
        }

        public Builder name(String name) {
            target.setName(name);
            return this;
        }

        public String description() {
            return target.getDescription();
        }

        public Builder description(String description) {
            target.setDescription(description);
            return this;
        }

        public String path() {
            return target.getPath();
        }

        public Builder path(String path) {
            target.setPath(path);
            return this;
        }

        public long depth() {
            return target.getDepth();
        }

        public Builder depth(long depth) {
            target.setDepth(depth);
            return this;
        }

        public float sort() {
            return target.getSort();
        }

        public Builder sort(float sort) {
            target.setSort(sort);
            return this;
        }

        public Type type() {
            return target.getType();
        }

        public Builder type(Type type) {
            target.setType(type);
            return this;
        }
    }
}
