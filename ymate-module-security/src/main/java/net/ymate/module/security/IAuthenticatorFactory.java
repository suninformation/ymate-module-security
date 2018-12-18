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

import net.ymate.platform.core.support.IInitializable;

/**
 * 用户认证工厂接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 17/6/1 上午11:18
 * @version 1.0
 */
public interface IAuthenticatorFactory extends IInitializable<ISecurity> {

    /**
     * @return 返回用户认证接口实例对象
     */
    IUserAuthenticator createUserAuthenticatorIfNeed();

//    /**
//     * @return 返回用户数据访问认证器
//     */
//    IDataAccessAuthenticator createDataAccessAuthenticatorIfNeed();

    /**
     * @param uid 当前用户唯一标识ID
     * @return 判断当前用户是否为该站点创建者(超级用户), 默认值: false, 子类可根据实际需求重写该方法
     */
    boolean checkUserIsFounder(String uid);
}
