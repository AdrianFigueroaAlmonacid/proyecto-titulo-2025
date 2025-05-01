FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/backend_food_easy-0.0.1-SNAPSHOT.jar java-app.jar
RUN apt-get update && apt-get install -y curl \
    && curl -o /usr/local/bin/wait-for-it https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /usr/local/bin/wait-for-it