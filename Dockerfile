FROM java:8-jdk-alpine
COPY ./PostOfficeMenagment/target/PostOfficeMenagment-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch PostOfficeMenagment-0.0.1-SNAPSHOT.jar'
ARG PORT=8081
EXPOSE ${PORT}
ENTRYPOINT ["java","-jar","PostOfficeMenagment-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081
