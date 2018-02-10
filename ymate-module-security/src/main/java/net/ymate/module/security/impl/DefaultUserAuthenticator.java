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
package net.ymate.module.security.impl;

import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/27 下午4:16
 * @version 1.0
 */
public class DefaultUserAuthenticator implements IUserAuthenticator {

    private boolean __founder;

    private ISecurity.Role[] __roles;

    private String[] __permissions;

    public DefaultUserAuthenticator() {
        __founder = false;
        __roles = new ISecurity.Role[0];
        __permissions = new String[0];
    }

    public DefaultUserAuthenticator(boolean founder, ISecurity.Role[] roles, String[] permissions) {
        __founder = founder;
        __roles = roles;
        __permissions = permissions;
    }

    public boolean isFounder() {
        return __founder;
    }

    public ISecurity.Role[] getUserRoles() {
        return __roles;
    }

    public String[] getUserPermissions() {
        return __permissions;
    }
}
