SuperBPM平台基础

1. 配置application.properties的数据源连接,即可运行
2. 运行UserServiceTest的testCreateUser用例,创建用户,然后可以看到登陆效果
3. 如果要发布平台插件则: mvn clean install -Dmaven.test.skip=true

运行方式:
1. mvn clean package -Dmaven.test.skip=true spring-boot:run
2. 运行TestPlatformApplication即可(待解决:因为内嵌tomcat依赖问题,PlatformApplication不能直接运行)

---
20151008待解决问题:

1. 为了让插件项目使用自己的资源文件,平台需要考虑如何支持多个资源文件(通配符?)