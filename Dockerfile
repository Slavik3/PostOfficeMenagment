FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/post-office-menagment-0.0.1-SNAPSHOT.jar PostOfficeMenagment.jar
ENTRYPOINT ["java","-jar","PostOfficeMenagment.jar"]
