#FROM openjdk:17
#COPY target/Eureka-0.0.1-SNAPSHOT.jar /temp
#WORKDIR /temp
#CMD java -jar Eureka-0.0.1-SNAPSHOT.jar
FROM openjdk:8
# 设置工作目录
WORKDIR /app

# 将本地的JAR包复制到容器的/app目录下
COPY Eureka-0.0.1-SNAPSHOT.jar /app/Eureka-0.0.1-SNAPSHOT.jar

# 设置环境变量，指定JAR包名
ENV JAR_FILE=Eureka-0.0.1-SNAPSHOT.jar

# 暴露应用的端口（根据你的应用需要修改）
EXPOSE 7001

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/Eureka-0.0.1-SNAPSHOT.jar"]
