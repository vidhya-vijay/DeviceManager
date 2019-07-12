FROM schoolofdevops/maven

WORKDIR /app

COPY .  .

RUN mvn package -DskipTests && \
    mv target/deviceManager-0.0.1-SNAPSHOT.jar /run/deviceManager-0.0.1-SNAPSHOT.jar && \
    rm -rf /app/*

CMD java  -jar /run/deviceManager-0.0.1-SNAPSHOT.jar
