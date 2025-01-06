FROM openjdk:8-jdk
COPY target/Consumer-0.0.1-SNAPSHOT.jar /temp
WORKDIR /temp
CMD java -jar Consumer-0.0.1-SNAPSHOT.jar

#FROM openjdk:8-jdk-alpine
#COPY target/Consumer-0.0.1-SNAPSHOT.jar consumer.jar
#ENTRYPOINT ["java", "-jar", "/consumer.jar"]
# 基础镜像
#FROM openjdk:8-jdk-alpine
#
## 指定维护者信息
#LABEL maintainer="yourname@example.com"
#
## 添加文件到容器
#ADD target/Consumer-0.0.1-SNAPSHOT.jar.jar /app.jar
#
## 暴露端口
#EXPOSE 8080
#
## 运行时执行的命令
#CMD ["java", "-jar", "/app.jar"]