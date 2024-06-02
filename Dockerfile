FROM openjdk

WORKDIR /ps-spring

COPY target/ps-spring-1.0.jar .

ENV DATABASE_USERNAME=usuario
ENV DATABASE_PASSWORD=senha
ENV DATABASE_URL=url
ENV PORT=port
ENV JWT_KEY=jwt_key
ENV MAPS_KEY=maps_key

ENTRYPOINT ["sh", "-c", "java -jar ps-spring-1.0.jar"]