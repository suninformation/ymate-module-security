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
package net.ymate.module.security.handle;

import net.ymate.module.security.PermissionMeta;
import net.ymate.platform.core.beans.IBeanHandler;

import java.lang.reflect.Method;

/**
 * 权限控制对象处理器
 *
 * @author 刘镇 (suninformation@163.com) on 17/2/20 下午5:31
 * @version 1.0
 */
public class SecurityHandler implements IBeanHandler {

    public Object handle(Class<?> targetClass) throws Exception {
        for (Method _method : targetClass.getDeclaredMethods()) {
            PermissionMeta.createIfNeed(_method);
        }
        return null;
    }
}
