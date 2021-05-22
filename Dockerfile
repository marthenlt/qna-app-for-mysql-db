# GraalVM CE - from Docker hub
FROM oracle/graalvm-ce:20.1.0-java8

# GraalVM EE - from local registry
# FROM localhost:5000/marthen-oracle-graalvmee20.3.2-jdk8--alpine

ENV MYSQL_HOST=mysql \
#ENV MYSQL_HOST=mysql-external-service \
    POLYGLOT_JS_DIR=/polyglot

RUN mkdir /polyglot

COPY target/qna-app-for-mysql-db-0.1.jar .
COPY polyglot/calculator.js /polyglot/.
COPY polyglot/hello.js /polyglot/.

EXPOSE 8080

# GraalVM
CMD ["java", "-jar", "qna-app-for-mysql-db-0.1.jar"]

# C2
# CMD ["java","-XX:-UseJVMCICompiler", "-jar", "qna-app-for-mysql-db-0.1.jar"]
