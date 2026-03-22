/*
 * Copyright (C) 2007-present the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.ymate.module.security.base.impl;

import net.ymate.module.security.base.IDataScopeInfo;
import net.ymate.platform.commons.ext.AbstractAttributeExt;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2026/03/22 22:08
 * @since 1.0.0
 */
public class DataScopeInfo extends AbstractAttributeExt implements IDataScopeInfo {

    private String id;

    private String name;

    private Float sort;

    private String type;

    public DataScopeInfo() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Float getSort() {
        return sort;
    }

    public void setSort(Float sort) {
        this.sort = sort;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataScopeInfo that = (DataScopeInfo) o;
        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
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

    public static class Builder extends AbstractBuilder<Builder, DataScopeInfo> {

        public Builder() {
            super(new DataScopeInfo());
        }

        public Builder(DataScopeInfo target) {
            super(target);
        }

        public String id() {
            return target.getId();
        }

        public Builder id(String id) {
            target.setId(id);
            return this;
        }

        public String name() {
            return target.getName();
        }

        public Builder name(String name) {
            target.setName(name);
            return this;
        }

        public Float sort() {
            return target.getSort();
        }

        public Builder sort(Float sort) {
            target.setSort(sort);
            return this;
        }

        public String type() {
            return target.getType();
        }

        public Builder type(String type) {
            target.setType(type);
            return this;
        }
    }
}