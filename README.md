# slipper framework

slipper4j-framework 是一个方便快速集成的 SpringBoot Starter组件，它提供了简单而强大的功能，帮助你轻松地集成到你的 Spring Boot 应用中。

每个子包，代表一个组件。每个组件包括两部分：
1. core 包：是该组件的核心封装
2. config 包：是该组件基于 Spring 的配置

组件，也分成两类：
1. 框架组件：和我们熟悉的 MyBatis、Redis 等等的拓展
2. 业务组件：和业务相关的组件的封装，例如说数据字典、操作日志等等。
如果是业务组件，Maven 名字会包含 biz