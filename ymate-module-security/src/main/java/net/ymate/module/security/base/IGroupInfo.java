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

import net.ymate.module.security.annotation.RoleType;
import net.ymate.platform.commons.ext.IAttributeExt;

/**
 * 安全组接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:10 AM
 * @since 1.0.0
 */
public interface IGroupInfo extends IAttributeExt {

    /**
     * 组唯一标识
     */
    String getId();

    /**
     * 组名称
     */
    String getName();

    /**
     * 类型：{0-默认 1-用户 2-操作员 3-管理员}
     */
    Type getType();

    /**
     * 角色类型枚举
     */
    enum Type {

        DEFAULT(0), USER(1), OPERATOR(2), ADMIN(3);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public static Type valueOf(Integer type) {
            if (type != null) {
                if (type == 3) {
                    return Type.ADMIN;
                } else if (type == 2) {
                    return Type.OPERATOR;
                } else if (type == 1) {
                    return Type.USER;
                }
            }
            return Type.DEFAULT;
        }

        public int type() {
            return type;
        }

        @Override
        public String toString() {
            return String.valueOf(type);
        }

        public RoleType toType() {
            switch (type) {
                case 3:
                    return RoleType.ADMIN;
                case 2:
                    return RoleType.OPERATOR;
                case 1:
                    return RoleType.USER;
                default:
                    return RoleType.INHERIT;
            }
        }
    }
}
