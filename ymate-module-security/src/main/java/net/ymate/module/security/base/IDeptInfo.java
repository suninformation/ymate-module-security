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

/**
 * 部门接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:14 AM
 * @since 1.0.0
 */
public interface IDeptInfo extends IAttributeExt {

    /**
     * 部门唯一标识
     */
    String getId();

    /**
     * 根部门唯一标识
     */
    String getRootId();

    /**
     * 父级部门唯一标识
     */
    String getParentId();

    /**
     * 负责人唯一标识
     */
    String getManagerUid();

    /**
     * 部门名称
     */
    String getName();

    /**
     * 部门描述
     */
    String getDescription();

    /**
     * 部门层级路径
     */
    String getPath();

    /**
     * 层级深度
     */
    long getDepth();

    /**
     * 排序
     */
    float getSort();

    /**
     * 类型：{0-节点 1-根}
     */
    Type getType();

    /**
     * 部门 类型枚举
     */
    enum Type {

        ROOT(1), NODE(0);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public static Type valueOf(Integer type) {
            if (type != null && type == 1) {
                return Type.ROOT;
            }
            return Type.NODE;
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
