FROM maven:3.8.6-openjdk-11

ADD . /pickembackend

WORKDIR /pickembackend
RUN mvn install

WORKDIR target
COPY ./Properties/pickembackend/application.properties .

EXPOSE 8080
CMD ["java", "-jar", "pickembackend.jar"]