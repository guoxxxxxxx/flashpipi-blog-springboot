FROM ubuntu:22.04
MAINTAINER flashpipi
RUN apt-get update && apt install -y openjdk-17-jdk
ENV JAVA_HOME /usr/lib/jvm/java-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin
COPY jar/flashpipi-blog-springboot-0.0.1-SNAPSHOT.jar /app/flashpipi-server.jar
WORKDIR /app
EXPOSE 8080
VOLUME /conf/flashpipi-blog