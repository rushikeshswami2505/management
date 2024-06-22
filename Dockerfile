FROM openjdk:21-oracle
ADD target/springboot-mysql-docker.jar springboot-mysql-docker.jar
ENTRYPOINT ["java","-jar","/springboot-mysql-docker.jar"]
#ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
#ENTRYPOINT["java","-jar","/springboot-mysql-docker.jar"]

#WORKDIR /opt
#ENV PORT 1002
#EXPOSE 1002
#COPY target/*.jar /opt/app.jar
#ENTRYPOINT exec java $JAVA_OPTS -jar app.jar


