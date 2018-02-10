### YMP-Security

> 用户权限认证及安全模块，特性如下：
> 

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.module</groupId>
        <artifactId>ymate-module-security</artifactId>
        <version>1.0.0</version>
    </dependency>

#### 模块配置参数说明

    #-------------------------------------
    # module.security 模块初始化参数
    #-------------------------------------
    
    # 用户认证工厂接口实现类, 必须参数, 默认值: 空
    ymp.configs.module.security.authenticator_factory_class=
    
    # 权限控制开关过滤集合, 默认值：空
    # - <group_name>: 权限组名称, 未指定分组的权限默认组为: default
    # - <name_1|name_N>: 权限名称集合, 未指定名称的权限默认名称为: default, 多个名称间用'|'分隔, 若希望过滤权限组下全部权限可设置值为all
    ymp.configs.module.security.permissions.<group_name>=<name_1|name_N>

#### 示例代码：

#### One More Thing

YMP不仅提供便捷的Web及其它Java项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入 官方QQ群480374360，一起交流学习，帮助YMP成长！

了解更多有关YMP框架的内容，请访问官网：http://www.ymate.net/