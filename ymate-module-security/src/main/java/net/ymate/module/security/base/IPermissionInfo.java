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
 * 授权项接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2022/3/6 2:13 AM
 * @since 1.0.0
 */
public interface IPermissionInfo extends IAttributeExt {

    /**
     * 权限唯一标识
     */
    String getId();

    /**
     * 权限名称
     */
    String getName();

    /**
     * 权限唯一代码
     */
    String getCode();

    /**
     * 图标
     */
    String getIcon();

    /**
     * URL地址
     */
    String getUrl();

    /**
     * 模块或组件URL地址
     */
    String getComponentUrl();

    /**
     * 父级权限唯一标识
     */
    String getParentId();

    /**
     * 根权限唯一标识
     */
    String getRootId();

    /**
     * 层级路径
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
     * 是否全屏显示
     */
    boolean isFullScreen();

    /**
     * 是否外部链接
     */
    boolean isOutsideUrl();

    /**
     * 类型：{0-模块或组 1-菜单 2-动作}
     */
    Type getType();

    /**
     * 权限 类型枚举
     */
    enum Type {

        MODULE(0), MENU(1), ACTION(2);

        private final int type;

        Type(int type) {
            this.type = type;
        }

        public static Type valueOf(Integer type) {
            if (type != null) {
                if (type == 1) {
                    return Type.MENU;
                } else if (type == 2) {
                    return Type.ACTION;
                }
            }
            return Type.MODULE;
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
