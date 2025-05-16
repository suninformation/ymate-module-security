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

import net.ymate.module.security.base.IDeptInfo;
import net.ymate.module.security.base.IPostInfo;
import net.ymate.module.security.base.IUserInfo;
import net.ymate.platform.commons.ext.AbstractAttributeExt;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class UserInfo extends AbstractAttributeExt implements IUserInfo {

    private String id;

    private String username;

    private String nickname;

    private String avatarUrl;

    private Type type;

    private IDeptInfo dept;

    private Collection<IPostInfo> posts;

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
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public IDeptInfo getDept() {
        return dept;
    }

    public void setDept(IDeptInfo dept) {
        this.dept = dept;
    }

    @Override
    public Collection<IPostInfo> getPosts() {
        return posts;
    }

    public void setPosts(Collection<IPostInfo> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return new EqualsBuilder().append(id, userInfo.id).isEquals();
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

        public Type type() {
            return target.getType();
        }

        public Builder type(Type type) {
            target.setType(type);
            return this;
        }

        public IDeptInfo dept() {
            return target.getDept();
        }

        public Builder dept(IDeptInfo dept) {
            target.setDept(dept);
            return this;
        }

        public Collection<IPostInfo> posts() {
            return target.getPosts();
        }

        public Builder posts(Collection<IPostInfo> posts) {
            target.setPosts(posts);
            return this;
        }
    }
}
