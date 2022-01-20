FROM java:8
ARG JAR_FILE
ENV JAVA_OPTS=""
ADD target/${JAR_FILE} app.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8083
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=prod -jar app.jar