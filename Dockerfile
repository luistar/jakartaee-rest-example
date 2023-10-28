FROM maven:3.8.5-openjdk-17

# application placed into /opt/app
RUN mkdir -p /opt/app
WORKDIR /opt/app

# copy the POM file
COPY pom.xml /opt/app/

# rest of the project
COPY src /opt/app/src
RUN mvn package

EXPOSE 8080

CMD ["mvn", "exec:java"]