FROM maven:3.6.1-jdk-8-alpine
RUN apk add --update \
    curl \
    jq
COPY pom.xml /app/
COPY src /app/src/
COPY healthcheck.sh /app/
WORKDIR /app/
ENTRYPOINT ["/bin/sh"]
CMD ["healthcheck.sh"]