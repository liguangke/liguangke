#FROM openjdk:8-jdk
#COPY target/Producer-0.0.1-SNAPSHOT.jar producer.jar
#ENTRYPOINT ["java", "-jar", "/producer.jar"]
FROM openjdk:8
# 设置工作目录
WORKDIR /app

# 将本地的JAR包复制到容器的/app目录下
COPY Producer-0.0.1-SNAPSHOT.jar /app/Producer-0.0.1-SNAPSHOT.jar

# 设置环境变量，指定JAR包名
ENV JAR_FILE=Producer-0.0.1-SNAPSHOT.jar

# 暴露应用的端口（根据你的应用需要修改）
EXPOSE 8081

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/Producer-0.0.1-SNAPSHOT.jar"]
