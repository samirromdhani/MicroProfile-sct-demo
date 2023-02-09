#FROM open-liberty:kernel-slim-java17-openj9
#FROM adoptopenjdk/openjdk11-openj9:latest
FROM openjdk:17-alpine

#ENTRYPOINT ["mvn", "clean", "install"]
COPY target/sct-demo.jar /

CMD ["java", "-jar", "sct-demo.jar" ]

EXPOSE 9081