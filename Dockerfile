FROM ubuntu:latest AS build

RUN apt-get update \
 && apt-get install -y --no-install-recommends \
      openjdk-17-jdk \
      maven \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /workspace

# copia pom e código-fonte
COPY pom.xml .
COPY src ./src

# empacota o Spring Boot (gera trab_republica-0.0.1-SNAPSHOT.jar em target/)
RUN mvn clean package -DskipTests


FROM ubuntu:latest AS runtime

RUN apt-get update \
 && apt-get install -y --no-install-recommends openjdk-17-jre \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# copia somente o fat-jar do build anterior
COPY --from=build /workspace/target/trab_republica-0.0.1-SNAPSHOT.jar .

# caso você queira renomear para facilitar:
# RUN mv trab_republica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "trab_republica-0.0.1-SNAPSHOT.jar"]
