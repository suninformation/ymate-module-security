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
import net.ymate.module.security.model.SecurityGroupUser;

/**
 * @author 刘镇 (suninformation@163.com) on 17/6/4 下午4:15
 * @version 1.0
 */
public class DefaultSecurityGroupUser implements ISecurity.IGroupUser {

    private String uid;

    private String groupId;

    private String siteId;

    public DefaultSecurityGroupUser(String uid, String groupId) {
        this.uid = uid;
        this.groupId = groupId;
    }

    public DefaultSecurityGroupUser(String uid, String groupId, String siteId) {
        this.uid = uid;
        this.groupId = groupId;
        this.siteId = siteId;
    }

    public DefaultSecurityGroupUser(SecurityGroupUser securityGroupUser) {
        this.uid = securityGroupUser.getUid();
        this.groupId = securityGroupUser.getGroupId();
        this.siteId = securityGroupUser.getSiteId();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
