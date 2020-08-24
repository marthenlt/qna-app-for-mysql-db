FROM oracle/graalvm-ce:20.1.0-java8

# ENV MYSQL_HOST mysql
ENV MYSQL_HOST=mysql-external-service \
    POLYGLOT_JS_DIR=/polyglot

RUN mkdir /polyglot

COPY target/qna-app-for-mysql-db-0.1.jar .
COPY polyglot/calculator.js /polyglot/.
COPY polyglot/hello.js /polyglot/.

EXPOSE 8080

CMD ["java", "-jar", "qna-app-for-mysql-db-0.1.jar"]
