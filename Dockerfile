FROM openjdk

WORKDIR /ps-spring

COPY target/ps-spring-1.0.jar .

ENV DATABASE_USERNAME=usuario
ENV DATABASE_PASSWORD=senha
ENV DATABASE_URL=url
ENV PORT=8081
ENV JWT_KEY=KEY
ENV MAPS_KEY=KEY

ENTRYPOINT ["sh", "-c", "java -jar ps-spring-1.0.jar"]