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
package net.ymate.module.security.taglib;

import net.ymate.framework.core.taglib.AbstractTagSupport;
import net.ymate.module.security.ComponentsMeta;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;

/**
 * 加载权限菜单组件集合, 可以根据条件加载全部或指定唯一标识名称的菜单项
 *
 * @author 刘镇 (suninformation@163.com) on 17/5/22 上午11:51
 * @version 1.0
 */
public class ComponentsTag extends AbstractTagSupport {

    /**
     * 组件分组名称
     */
    private String name;

    protected Object doProcessTagData() throws JspException {
        if (StringUtils.isNotBlank(name)) {
            return ComponentsMeta.getComponents(name);
        }
        return ComponentsMeta.getComponents();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
