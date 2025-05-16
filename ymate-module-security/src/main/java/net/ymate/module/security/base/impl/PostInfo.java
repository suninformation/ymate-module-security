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

import net.ymate.module.security.base.IPostInfo;
import net.ymate.platform.commons.ext.AbstractAttributeExt;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class PostInfo extends AbstractAttributeExt implements IPostInfo {

    private String id;

    private String name;

    private String code;

    private Integer level;

    public PostInfo() {
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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostInfo postInfo = (PostInfo) o;
        return new EqualsBuilder().append(id, postInfo.id).isEquals();
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

    public static class Builder extends AbstractBuilder<Builder, PostInfo> {

        public Builder() {
            super(new PostInfo());
        }

        public Builder(PostInfo target) {
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

        public String code() {
            return target.getCode();
        }

        public Builder code(String code) {
            target.setCode(code);
            return this;
        }

        public Integer level() {
            return target.getLevel();
        }

        public Builder level(Integer level) {
            target.setLevel(level);
            return this;
        }
    }
}
