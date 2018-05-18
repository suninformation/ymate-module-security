/*
 * Copyright 2007-2018 the original author or authors.
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
package net.ymate.module.security.repository.impl;

import net.ymate.module.security.IAuthenticatorFactory;
import net.ymate.module.security.ISecurity;
import net.ymate.module.security.IUserAuthenticator;
import net.ymate.module.security.Security;
import net.ymate.module.security.impl.DefaultUserAuthenticator;
import net.ymate.module.security.model.SecurityGroup;
import net.ymate.module.security.model.SecurityGroupUser;
import net.ymate.module.security.repository.ISecurityRepository;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.ISessionExecutor;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.annotation.Transaction;
import net.ymate.platform.persistence.jdbc.base.impl.EntityResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.Cond;
import net.ymate.platform.persistence.jdbc.query.Select;
import net.ymate.platform.persistence.jdbc.query.Where;
import net.ymate.platform.persistence.jdbc.repo.annotation.Repository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 2018/2/10 下午5:10
 * @version 1.0
 */
@Repository
@Transaction
public class SecurityRepository implements ISecurityRepository {

    public IUserAuthenticator getUserAuthenticator(String uid) throws Exception {
        Set<ISecurity.Role> _roles = new HashSet<ISecurity.Role>();
        Set<String> _permissions = new HashSet<String>();
        //
        List<SecurityGroup> _groups = getUserGroups(uid);
        if (!_groups.isEmpty()) {
            for (SecurityGroup _group : _groups) {
                if (BlurObject.bind(_group.getIsAdminRole()).toBooleanValue()) {
                    _roles.add(ISecurity.Role.ADMIN);
                }
                if (BlurObject.bind(_group.getIsOperatorRole()).toBooleanValue()) {
                    _roles.add(ISecurity.Role.OPERATOR);
                }
                if (BlurObject.bind(_group.getIsUserRole()).toBooleanValue()) {
                    _roles.add(ISecurity.Role.USER);
                }
                String[] _permissionArr = StringUtils.split(StringUtils.trimToEmpty(_group.getPermission()), "|");
                if (_permissionArr != null && _permissionArr.length > 0) {
                    _permissions.addAll(Arrays.asList(_permissionArr));
                }
            }
        }
        IAuthenticatorFactory _authenticatorFactory = Security.get().getModuleCfg().getAuthenticatorFactory();
        return new DefaultUserAuthenticator(_authenticatorFactory != null && _authenticatorFactory.checkUserIsFounder(uid), _roles.toArray(new ISecurity.Role[0]), _permissions.toArray(new String[0]));
    }

    public List<SecurityGroup> getUserGroups(final String uid) throws Exception {
        if (StringUtils.isBlank(uid)) {
            throw new NullArgumentException("uid");
        }
        IResultSet<SecurityGroup> _results = JDBC.get().openSession(new ISessionExecutor<IResultSet<SecurityGroup>>() {
            public IResultSet<SecurityGroup> execute(ISession session) throws Exception {
                String _prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
                Select _subQuery = Select.create(_prefix, SecurityGroupUser.class)
                        .field(Fields.create(SecurityGroupUser.FIELDS.GROUP_ID))
                        .where(Where.create(Cond.create().eq(SecurityGroupUser.FIELDS.UID).param(uid)));
                return session.find(Select.create(_prefix, SecurityGroup.class)
                        .where(Where.create(Cond.create().in(SecurityGroup.FIELDS.ID, _subQuery).and().eq(SecurityGroup.FIELDS.STATUS).param(0))).toSQL(), new EntityResultSetHandler<SecurityGroup>(SecurityGroup.class));
            }
        });
        return _results.getResultData();
    }

    public IResultSet<SecurityGroup> getGroups(int page, int pageSize) throws Exception {
        Page _page = null;
        if (page > 0) {
            _page = Page.create(page).count(false);
            if (pageSize > 0) {
                _page.pageSize(pageSize);
            }
        }
        return SecurityGroup.builder().build().find(_page);
    }

    public SecurityGroup addGroup(String groupName) throws Exception {
        if (StringUtils.isBlank(groupName)) {
            throw new NullArgumentException("groupName");
        }
        final String _id = DigestUtils.md5Hex(groupName);
        SecurityGroup _group = SecurityGroup.builder().id(_id).build().load(Fields.create(SecurityGroup.FIELDS.CREATE_TIME,
                SecurityGroup.FIELDS.LAST_MODIFY_TIME,
                SecurityGroup.FIELDS.STATUS,
                SecurityGroup.FIELDS.TYPE).excluded(true));
        if (_group == null) {
            _group = new SecurityGroup(_id, groupName, System.currentTimeMillis())
                    .save(Fields.create(SecurityGroup.FIELDS.ID,
                            SecurityGroup.FIELDS.NAME,
                            SecurityGroup.FIELDS.CREATE_TIME));
        }
        return _group;
    }

    public void saveOrUpdateGroup(String groupId, boolean isAdminRole, boolean isOperatorRole, boolean isUserRole, String[] permissions) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        final SecurityGroup _group = SecurityGroup.builder().id(groupId).build().load();
        if (_group == null) {
            throw new IllegalArgumentException("groupId");
        }
        _group.setIsAdminRole(isAdminRole ? 1 : 0);
        _group.setIsOperatorRole(isOperatorRole ? 1 : 0);
        _group.setIsUserRole(isUserRole ? 1 : 0);
        _group.setPermission(StringUtils.join(permissions, "|"));
        _group.setLastModifyTime(System.currentTimeMillis());
        _group.update(Fields.create(SecurityGroup.FIELDS.IS_ADMIN_ROLE,
                SecurityGroup.FIELDS.IS_OPERATOR_ROLE,
                SecurityGroup.FIELDS.IS_USER_ROLE,
                SecurityGroup.FIELDS.PERMISSION,
                SecurityGroup.FIELDS.LAST_MODIFY_TIME));
    }

    public IResultSet<SecurityGroupUser> getGroupUsers(String groupId, int page, int pageSize) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        Page _page = null;
        if (page > 0) {
            _page = Page.create(page).count(false);
            if (pageSize > 0) {
                _page.pageSize(pageSize);
            }
        }
        return SecurityGroupUser.builder().groupId(groupId).build()
                .find(Fields.create(SecurityGroupUser.FIELDS.UID, SecurityGroupUser.FIELDS.GROUP_ID), _page);
    }

    public void addGroupUser(final String groupId, final String uid) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        if (StringUtils.isBlank(uid)) {
            throw new NullArgumentException("uid");
        }
        final SecurityGroup _group = SecurityGroup.builder().id(groupId).build().load(Fields.create(SecurityGroup.FIELDS.ID));
        if (_group == null) {
            throw new IllegalArgumentException("groupId");
        }
        final String _id = DigestUtils.md5Hex(_group.getId() + uid);
        SecurityGroupUser _groupUser = SecurityGroupUser.builder().id(_id).build().load(Fields.create(SecurityGroupUser.FIELDS.ID));
        if (_groupUser == null) {
            SecurityGroupUser.builder()
                    .id(_id)
                    .groupId(groupId)
                    .uid(uid)
                    .createTime(System.currentTimeMillis()).build()
                    .save(Fields.create(SecurityGroupUser.FIELDS.ID,
                            SecurityGroupUser.FIELDS.UID,
                            SecurityGroupUser.FIELDS.GROUP_ID,
                            SecurityGroupUser.FIELDS.CREATE_TIME));
        }
    }

    @Transaction
    public void removeGroupUser(final String groupId, final String uid) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        if (StringUtils.isBlank(uid)) {
            throw new NullArgumentException("uid");
        }
        SecurityGroupUser.builder().groupId(groupId).uid(uid).build().delete();
    }

    @Transaction
    public void removeGroup(final String groupId) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        if (SecurityGroup.builder().id(groupId).build().delete() != null) {
            SecurityGroupUser.builder().groupId(groupId).build().delete();
        }
    }

    @Transaction
    public void cleanupGroup(final String groupId) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        SecurityGroupUser.builder().groupId(groupId).build().delete();
    }
}
