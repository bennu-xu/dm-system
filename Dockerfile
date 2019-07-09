FROM java:8-jdk-alpine

ENV SERVER_PORT=8080 \
    JAR_FILE=data-mining-0.0.1-SNAPSHOT.jar \
    TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime && echo ${TZ} > /etc/timezone

COPY target/${JAR_FILE} /app.jar

EXPOSE ${SERVER_PORT}

CMD [ "/usr/bin/java" ,"-jar", "/app.jar"]