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

/**
 * 资源未授权或无权限异常
 *
 * @author 刘镇 (suninformation@163.com) on 17/2/26 上午1:33
 * @version 1.0
 */
public class SecurityPermissionException extends RuntimeException {

    public SecurityPermissionException() {
        super();
    }

    public SecurityPermissionException(String message) {
        super(message);
    }

    public SecurityPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityPermissionException(Throwable cause) {
        super(cause);
    }
}
