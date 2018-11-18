# sell
springboot微信点餐

# 打jar包

跳过单元测试打包命令：
  mvn clean package -Dmaven.test.skip=true
 
自定义包名：
  在pom.xml中，build标签下定义标签<finalName>的值，再次打包即可。
  
  
# 启动
  在linux系统中，将打包好的jar包放到linux的目录下，在该目录中，运行命令：
    java  -jar  packageName.jar
  # 修改端口(-D后面的命令格式，是application.yml中配置的，可以在启动时直接使用命令行控制)
    java  -jar  -Dserver.port=8090 packageName.jar

