FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Seoul

#RUN apk update && apk add build-base

#RUN adduser -D bgpark
#USER bgpark
#ENV HOME=/home/bgpark
#WORKDIR $HOME/workspace

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]


