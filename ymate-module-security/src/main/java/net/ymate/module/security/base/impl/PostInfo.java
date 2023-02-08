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
import net.ymate.module.security.base.IPostInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class PostInfo extends AbstractAttributeExt implements IPostInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String code;

    private Integer level;

    private String description;

    private Integer type;

    private Integer status;

    private Long createTime;

    private Long lastModifyTime;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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

        public String description() {
            return target.getDescription();
        }

        public Builder description(String description) {
            target.setDescription(description);
            return this;
        }

        public Integer type() {
            return target.getType();
        }

        public Builder type(Integer type) {
            target.setType(type);
            return this;
        }

        public Integer status() {
            return target.getStatus();
        }

        public Builder status(Integer status) {
            target.setStatus(status);
            return this;
        }

        public Long createTime() {
            return target.getCreateTime();
        }

        public Builder createTime(Long createTime) {
            target.setCreateTime(createTime);
            return this;
        }

        public Long lastModifyTime() {
            return target.getLastModifyTime();
        }

        public Builder lastModifyTime(Long lastModifyTime) {
            target.setLastModifyTime(lastModifyTime);
            return this;
        }
    }
}
