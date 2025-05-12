# YMATE-MODULE-SECURITY

[![Maven Central status](https://img.shields.io/maven-central/v/net.ymate.module/ymate-module-security.svg)](https://search.maven.org/artifact/net.ymate.module/ymate-module-security)
[![LICENSE](https://img.shields.io/github/license/suninformation/ymate-module-security.svg)](https://gitee.com/suninformation/ymate-module-security/blob/master/LICENSE)


基于 YMP 框架实现的邮件发送服务模块，支持多账号配置。



## Maven包依赖

```xml
<dependency>
    <groupId>net.ymate.module</groupId>
    <artifactId>ymate-module-security</artifactId>
    <version>1.0.0</version>
</dependency>
```



## 模块配置参数说明

```properties
#-------------------------------------
# module.security 模块初始化参数
#-------------------------------------

# 模块是否已启用, 默认值: true
#ymp.configs.module.security.enabled=false

# 缓存名称前缀, 默认值: ""
ymp.configs.module.security.cache_name_prefix=

# 缓存数据超时时间, 可选参数, 数值必须大于等于0, 否则将采用默认
ymp.configs.module.security.cache_timeout=

# 用户认证工厂, 默认值: net.ymate.module.security.impl.DefaultAuthenticatorFactory
ymp.configs.module.security.authenticator_factory_class=
```



## One More Thing

YMP 不仅提供便捷的 Web 及其它 Java 项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入官方 QQ 群：[480374360](https://qm.qq.com/cgi-bin/qm/qr?k=3KSXbRoridGeFxTVA8HZzyhwU_btZQJ2)，一起交流学习，帮助 YMP 成长！

如果喜欢 YMP，希望得到你的支持和鼓励！

![Donation Code](https://ymate.net/img/donation_code.png)

了解更多有关 YMP 框架的内容，请访问官网：[https://ymate.net](https://ymate.net)