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
package net.ymate.module.security;

import com.alibaba.fastjson.annotation.JSONField;
import net.ymate.module.security.annotation.Components;
import net.ymate.module.security.annotation.Permission;
import net.ymate.platform.core.i18n.I18N;
import org.apache.commons.lang.StringUtils;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/20 下午3:46
 * @version 1.0
 */
public abstract class AbstractComponents {

    private String id;

    private String icon;

    private String name;

    private String mapping;

    @JSONField(serialize = false, deserialize = false)
    private Permission permission;

    private boolean display;

    private int order;

    static String __doTryLoadI18N(Components comp, String message) throws Exception {
        return I18N.load(StringUtils.defaultIfBlank(comp.resourcesName(), ISecurity.MODULE_NAME), message, message);
    }

    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    protected void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getMapping() {
        return mapping;
    }

    protected void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Permission getPermission() {
        return permission;
    }

    protected void setPermission(Permission permission) {
        this.permission = permission;
    }

    public boolean isDisplay() {
        return display;
    }

    protected void setDisplay(boolean display) {
        this.display = display;
    }

    public int getOrder() {
        return order;
    }

    protected void setOrder(int order) {
        this.order = order;
    }
}
