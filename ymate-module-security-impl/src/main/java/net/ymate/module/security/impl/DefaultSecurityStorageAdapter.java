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
import net.ymate.module.security.ISecurityStorageAdapter;
import net.ymate.module.security.IUserAuthenticator;

/**
 * @author 刘镇 (suninformation@163.com) on 17/5/27 下午3:58
 * @version 1.0
 */
public class DefaultSecurityStorageAdapter implements ISecurityStorageAdapter {

    public void init(ISecurity owner) throws Exception {
    }

    public void destroy() throws Exception {
    }

    public IUserAuthenticator getUserAuthenticator(String siteId, String uid) throws Exception {
        return null;
    }

    public void saveOrUpdate(String siteId, String groupId, IUserAuthenticator authenticator) throws Exception {
    }

    public void removeGroup(String siteId, String groupId) throws Exception {
    }

    public void removeGroupUser(String siteId, String groupId, String uid) throws Exception {
    }

    public void cleanup(String uid) throws Exception {
    }
}
