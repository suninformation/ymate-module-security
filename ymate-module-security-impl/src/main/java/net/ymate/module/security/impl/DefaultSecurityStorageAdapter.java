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
import net.ymate.module.security.model.SecurityGroup;
import net.ymate.module.security.model.SecurityGroupUser;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.jdbc.ISession;
import net.ymate.platform.persistence.jdbc.ISessionExecutor;
import net.ymate.platform.persistence.jdbc.JDBC;
import net.ymate.platform.persistence.jdbc.base.impl.EntityResultSetHandler;
import net.ymate.platform.persistence.jdbc.query.Cond;
import net.ymate.platform.persistence.jdbc.query.Delete;
import net.ymate.platform.persistence.jdbc.query.Select;
import net.ymate.platform.persistence.jdbc.query.Where;
import net.ymate.platform.persistence.jdbc.transaction.ITrade;
import net.ymate.platform.persistence.jdbc.transaction.Trade;
import net.ymate.platform.persistence.jdbc.transaction.Transactions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import java.util.*;

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
        final Set<ISecurity.Role> _roles = new HashSet<ISecurity.Role>();
        final Set<String> _permissions = new HashSet<String>();
        //
        List<ISecurity.IGroup> _groups = getUserGroups(siteId, uid);
        if (!_groups.isEmpty()) {
            for (ISecurity.IGroup _item : _groups) {
                _roles.addAll(Arrays.asList(_item.getAuthenticator().getUserRoles()));
                _permissions.addAll(Arrays.asList(_item.getAuthenticator().getUserPermissions()));
            }
        }
        return new IUserAuthenticator() {

            public boolean isFounder() {
                return false;
            }

            public ISecurity.Role[] getUserRoles() {
                return _roles.toArray(new ISecurity.Role[_roles.size()]);
            }

            public String[] getUserPermissions() {
                return _permissions.toArray(new String[_permissions.size()]);
            }
        };
    }

    public List<ISecurity.IGroup> getUserGroups(final String siteId, final String uid) throws Exception {
        if (StringUtils.isBlank(siteId)) {
            throw new NullArgumentException("siteId");
        }
        if (StringUtils.isBlank(uid)) {
            throw new NullArgumentException("uid");
        }
        IResultSet<SecurityGroup> _results = JDBC.get().openSession(new ISessionExecutor<IResultSet<SecurityGroup>>() {
            public IResultSet<SecurityGroup> execute(ISession session) throws Exception {
                String _prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
                Select _subQuery = Select.create(_prefix, SecurityGroupUser.class)
                        .field(Fields.create(SecurityGroupUser.FIELDS.GROUP_ID))
                        .where(Where.create(Cond.create()
                                .eq(SecurityGroupUser.FIELDS.SITE_ID).param(siteId)
                                .and()
                                .eq(SecurityGroupUser.FIELDS.UID).param(uid)));
                return session.find(Select.create(_prefix, SecurityGroup.class)
                        .where(Where.create(Cond.create().in(SecurityGroup.FIELDS.ID, _subQuery))).toSQL(), new EntityResultSetHandler<SecurityGroup>(SecurityGroup.class));
            }
        });
        List<ISecurity.IGroup> _returnValues = new ArrayList<ISecurity.IGroup>();
        if (_results.isResultsAvailable()) {
            for (SecurityGroup _item : _results.getResultData()) {
                _returnValues.add(new DefaultSecurityGroup(_item));
            }
        }
        return _returnValues;
    }

    public List<ISecurity.IGroup> getGroups(String siteId) throws Exception {
        if (StringUtils.isBlank(siteId)) {
            throw new NullArgumentException("siteId");
        }
        List<ISecurity.IGroup> _returnValues = new ArrayList<ISecurity.IGroup>();
        IResultSet<SecurityGroup> _results = SecurityGroup.builder().siteId(siteId).build()
                .find(Fields.create(SecurityGroup.FIELDS.CREATE_TIME,
                        SecurityGroup.FIELDS.LAST_MODIFY_TIME,
                        SecurityGroup.FIELDS.STATUS,
                        SecurityGroup.FIELDS.TYPE).excluded(true));
        if (_results.isResultsAvailable()) {
            for (SecurityGroup _item : _results.getResultData()) {
                _returnValues.add(new DefaultSecurityGroup(_item));
            }
        }
        return _returnValues;
    }

    public ISecurity.IGroup addGroup(final String siteId, final String groupName) throws Exception {
        if (StringUtils.isBlank(siteId)) {
            throw new NullArgumentException("siteId");
        }
        if (StringUtils.isBlank(groupName)) {
            throw new NullArgumentException("groupName");
        }
        final String _id = DigestUtils.md5Hex(siteId + groupName);
        SecurityGroup _group = SecurityGroup.builder().id(_id).build().load(Fields.create(SecurityGroup.FIELDS.CREATE_TIME,
                SecurityGroup.FIELDS.LAST_MODIFY_TIME,
                SecurityGroup.FIELDS.STATUS,
                SecurityGroup.FIELDS.TYPE).excluded(true));
        if (_group == null) {
            _group = Transactions.execute(new Trade<SecurityGroup>() {
                public void deal() throws Throwable {
                    SecurityGroup _targetGroup = new SecurityGroup(_id, groupName, siteId, System.currentTimeMillis()).save(Fields.create(SecurityGroup.FIELDS.ID,
                            SecurityGroup.FIELDS.NAME,
                            SecurityGroup.FIELDS.SITE_ID,
                            SecurityGroup.FIELDS.CREATE_TIME));
                    this.setReturns(_targetGroup);
                }
            });
        }
        return new DefaultSecurityGroup(_group);
    }

    public void saveOrUpdateGroup(String groupId, final IUserAuthenticator authenticator) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        final SecurityGroup _group = SecurityGroup.builder().id(groupId).build().load();
        if (_group == null) {
            throw new IllegalArgumentException("groupId");
        }
        Transactions.execute(new ITrade() {
            public void deal() throws Throwable {
                _group.setIsAdminRole(ArrayUtils.contains(authenticator.getUserRoles(), ISecurity.Role.ADMIN) ? 1 : 0);
                _group.setIsOperatorRole(ArrayUtils.contains(authenticator.getUserRoles(), ISecurity.Role.OPERATOR) ? 1 : 0);
                _group.setIsUserRole(ArrayUtils.contains(authenticator.getUserRoles(), ISecurity.Role.USER) ? 1 : 0);
                _group.setPermission(StringUtils.join(authenticator.getUserPermissions(), "|"));
                _group.setLastModifyTime(System.currentTimeMillis());
                _group.update(Fields.create(SecurityGroup.FIELDS.IS_ADMIN_ROLE, SecurityGroup.FIELDS.IS_OPERATOR_ROLE, SecurityGroup.FIELDS.IS_USER_ROLE, SecurityGroup.FIELDS.PERMISSION, SecurityGroup.FIELDS.LAST_MODIFY_TIME));
            }
        });
    }

    public List<ISecurity.IGroupUser> getGroupUsers(String groupId, int page, int pageSize) throws Exception {
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
        List<ISecurity.IGroupUser> _returnValues = new ArrayList<ISecurity.IGroupUser>();
        IResultSet<SecurityGroupUser> _results = SecurityGroupUser.builder().groupId(groupId).build()
                .find(Fields.create(SecurityGroupUser.FIELDS.UID, SecurityGroupUser.FIELDS.GROUP_ID, SecurityGroupUser.FIELDS.SITE_ID), _page);
        if (_results.isResultsAvailable()) {
            for (SecurityGroupUser _item : _results.getResultData()) {
                _returnValues.add(new DefaultSecurityGroupUser(_item));
            }
        }
        return _returnValues;
    }

    public void addGroupUser(final String groupId, final String uid) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        if (StringUtils.isBlank(uid)) {
            throw new NullArgumentException("uid");
        }
        final SecurityGroup _group = SecurityGroup.builder().id(groupId).build().load(Fields.create(SecurityGroup.FIELDS.ID, SecurityGroup.FIELDS.SITE_ID));
        if (_group == null) {
            throw new IllegalArgumentException("groupId");
        }
        final String _id = DigestUtils.md5Hex(_group.getId() + _group.getSiteId() + uid);
        SecurityGroupUser _groupUser = SecurityGroupUser.builder().id(_id).build().load(Fields.create(SecurityGroupUser.FIELDS.ID));
        if (_groupUser == null) {
            Transactions.execute(new ITrade() {
                public void deal() throws Throwable {
                    SecurityGroupUser.builder()
                            .id(_id)
                            .groupId(groupId)
                            .siteId(_group.getSiteId())
                            .uid(uid)
                            .createTime(System.currentTimeMillis()).build()
                            .save(Fields.create(SecurityGroupUser.FIELDS.ID,
                                    SecurityGroupUser.FIELDS.UID,
                                    SecurityGroupUser.FIELDS.GROUP_ID,
                                    SecurityGroupUser.FIELDS.SITE_ID,
                                    SecurityGroupUser.FIELDS.CREATE_TIME));
                }
            });
        }
    }

    public void removeGroupUser(final String groupId, final String uid) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        if (StringUtils.isBlank(uid)) {
            throw new NullArgumentException("uid");
        }
        Transactions.execute(new ITrade() {
            public void deal() throws Throwable {
                JDBC.get().openSession(new ISessionExecutor<Integer>() {
                    public Integer execute(ISession session) throws Exception {
                        String _prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
                        return session.executeForUpdate(Delete.create(_prefix, SecurityGroupUser.class)
                                .where(Where.create(Cond.create()
                                        .eq(SecurityGroupUser.FIELDS.GROUP_ID).param(groupId)
                                        .and()
                                        .eq(SecurityGroupUser.FIELDS.UID).param(uid))).toSQL());
                    }
                });
            }
        });
    }

    public void removeGroup(final String groupId) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        Transactions.execute(new ITrade() {
            public void deal() throws Throwable {
                if (SecurityGroup.builder().id(groupId).build().delete() != null) {
                    cleanupGroup(groupId);
                }
            }
        });
    }

    public void cleanupGroup(final String groupId) throws Exception {
        if (StringUtils.isBlank(groupId)) {
            throw new NullArgumentException("groupId");
        }
        Transactions.execute(new ITrade() {
            public void deal() throws Throwable {
                JDBC.get().openSession(new ISessionExecutor<Integer>() {
                    public Integer execute(ISession session) throws Exception {
                        String _prefix = session.getConnectionHolder().getDataSourceCfgMeta().getTablePrefix();
                        return session.executeForUpdate(Delete.create(_prefix, SecurityGroupUser.class)
                                .where(Where.create(Cond.create()
                                        .eq(SecurityGroupUser.FIELDS.GROUP_ID).param(groupId))).toSQL());
                    }
                });
            }
        });
    }
}
