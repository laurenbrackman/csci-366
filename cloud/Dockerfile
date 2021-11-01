FROM openjdk:11 

MAINTAINER Carson Gross <carson.gross@montana.edu>

COPY . /usr/src/archivecat
WORKDIR /usr/src/archivecat

CMD ["java", "-cp", "target/ArchiveCat-jar-with-dependencies.jar", "edu.montana.csci.csci366.archivecat.Server"]
