FROM gradle:7.4.1-jdk11 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test -x integrationTest

FROM openjdk:11.0.14-jdk-oracle

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/phorest-app-0.0.1-SNAPSHOT.jar /app/phorest-app-0.0.1-SNAPSHOT.jar
WORKDIR /app

CMD ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILE_ACTIVE} -jar /app/phorest-app-0.0.1-SNAPSHOT.jar"]
