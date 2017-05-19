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
package net.ymate.module.security;

import java.util.Map;
import java.util.Set;

/**
 * @author 刘镇 (suninformation@163.com) on 17/2/18 下午6:02
 * @version 1.0
 */
public interface ISecurityModuleCfg {

    /**
     * @return 返回用户认证接口实例对象
     */
    IUserAuthenticator getUserAuthenticator();

    /**
     * @return 返回权限控制开关过滤集合
     */
    Map<String, Set<String>> getPermissionFilters();
}
