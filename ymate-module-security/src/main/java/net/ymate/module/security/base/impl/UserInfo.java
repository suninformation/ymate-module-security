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
import net.ymate.module.security.base.IUserInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class UserInfo extends AbstractAttributeExt implements IUserInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String deptId;

    private String username;

    private String nickname;

    private String avatarUrl;

    private Integer type;

    private Integer status;

    public UserInfo() {
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public Builder bind() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractBuilder<Builder, UserInfo> {

        public Builder() {
            super(new UserInfo());
        }

        public Builder(UserInfo target) {
            super(target);
        }

        public String id() {
            return target.getId();
        }

        public Builder id(String id) {
            target.setId(id);
            return this;
        }

        public String deptId() {
            return target.getDeptId();
        }

        public Builder deptId(String deptId) {
            target.setDeptId(deptId);
            return this;
        }

        public String username() {
            return target.getUsername();
        }

        public Builder username(String username) {
            target.setUsername(username);
            return this;
        }

        public String nickname() {
            return target.getNickname();
        }

        public Builder nickname(String nickname) {
            target.setNickname(nickname);
            return this;
        }

        public String avatarUrl() {
            return target.getAvatarUrl();
        }

        public Builder avatarUrl(String avatarUrl) {
            target.setAvatarUrl(avatarUrl);
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
    }
}
