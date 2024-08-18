FROM openjdk:17-jdk
RUN apt-get update && apt-get install -y mysql-server
RUN service mysql start && mysql -e MYSQL_ROOT_PASSWORD=123456
VOLUME /var/lib/mysql
COPY target/flashpipi-blog-springboot-0.0.1-SNAPSHOT.jar /app/flashpipi-server.jar
WORKDIR /app
EXPOSE 8080 3306
CMD ["sh", "-c", "service mysql start && java -jar flashpipi-server.jar"]