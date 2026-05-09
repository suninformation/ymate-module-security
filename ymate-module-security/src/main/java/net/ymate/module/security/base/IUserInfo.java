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
package net.ymate.module.security.base;

import net.ymate.platform.commons.ext.IAttributeExt;

import java.util.Collection;

/**
 * 用户接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:12 AM
 * @since 1.0.0
 */
public interface IUserInfo extends IAttributeExt {

    /**
     * 用户唯一标识
     */
    String getId();

    /**
     * 用户名称
     */
    String getUsername();

    /**
     * 昵称
     */
    String getNickname();

    /**
     * 真实姓名
     */
    String getRealName();

    /**
     * 头像URL地址
     */
    String getAvatarUrl();

    /**
     * 类型：{0-普通用户 1-内部用户}
     */
    Type getType();

    /**
     * 所属部门
     */
    IDeptInfo getDept();

    /**
     * 所属岗位
     */
    Collection<IPostInfo> getPosts();

    /**
     * 用户 类型枚举
     */
    enum Type {

        GENERAL(0), INTERNAL(1);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public static Type valueOf(Integer type) {
            if (type != null && type == 1) {
                return Type.INTERNAL;
            }
            return Type.GENERAL;
        }

        public int type() {
            return type;
        }

        @Override
        public String toString() {
            return String.valueOf(type);
        }
    }
}
