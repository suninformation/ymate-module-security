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

/**
 * 角色接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:11 AM
 * @since 1.0.0
 */
public interface IRoleInfo extends IAttributeExt {

    /**
     * 角色唯一标识
     */
    String getId();

    /**
     * 角色名称
     */
    String getName();

    /**
     * 角色描述
     */
    String getDescription();

    /**
     * 类型：{0-用户 1-操作员 2-管理员}
     */
    Type getType();

    /**
     * 角色类型枚举
     */
    enum Type {

        ADMIN(2), OPERATOR(1), USER(0);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public static Type valueOf(Integer type) {
            if (type != null) {
                if (type == 2) {
                    return Type.ADMIN;
                } else if (type == 1) {
                    return Type.OPERATOR;
                }
            }
            return Type.USER;
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
                case 2:
                    return RoleType.ADMIN;
                case 1:
                    return RoleType.OPERATOR;
                default:
                    return RoleType.USER;
            }
        }
    }
}
