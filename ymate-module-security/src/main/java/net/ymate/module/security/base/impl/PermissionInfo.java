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
import net.ymate.module.security.base.IPermissionInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author 刘镇 (suninformation@163.com) on 2025/04/28 16:53:51
 * @since 1.0.0
 */
public class PermissionInfo extends AbstractAttributeExt implements IPermissionInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String code;

    private String icon;

    private String url;

    private String componentUrl;

    private String parentId;

    private String rootId;

    private String path;

    private long depth;

    private float sort;

    private boolean fullScreen;

    private boolean outsideUrl;

    private Type type;

    public PermissionInfo() {
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
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getComponentUrl() {
        return componentUrl;
    }

    public void setComponentUrl(String componentUrl) {
        this.componentUrl = componentUrl;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
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
    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    @Override
    public boolean isOutsideUrl() {
        return outsideUrl;
    }

    public void setOutsideUrl(boolean outsideUrl) {
        this.outsideUrl = outsideUrl;
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public static class Builder extends AbstractBuilder<Builder, PermissionInfo> {

        public Builder() {
            super(new PermissionInfo());
        }

        public Builder(PermissionInfo target) {
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

        public String icon() {
            return target.getIcon();
        }

        public Builder icon(String icon) {
            target.setIcon(icon);
            return this;
        }

        public String url() {
            return target.getUrl();
        }

        public Builder url(String url) {
            target.setUrl(url);
            return this;
        }

        public String componentUrl() {
            return target.getComponentUrl();
        }

        public Builder componentUrl(String componentUrl) {
            target.setComponentUrl(componentUrl);
            return this;
        }

        public String parentId() {
            return target.getParentId();
        }

        public Builder parentId(String parentId) {
            target.setParentId(parentId);
            return this;
        }

        public String rootId() {
            return target.getRootId();
        }

        public Builder rootId(String rootId) {
            target.setRootId(rootId);
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

        public boolean fullScreen() {
            return target.isFullScreen();
        }

        public Builder fullScreen(boolean fullScreen) {
            target.setFullScreen(fullScreen);
            return this;
        }

        public boolean outsideUrl() {
            return target.isOutsideUrl();
        }

        public Builder outsideUrl(boolean outsideUrl) {
            target.setOutsideUrl(outsideUrl);
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
