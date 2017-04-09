# Ubuntu 16.04 LTS
# Oracle Java 1.8.0_91 64 bit
# Maven 3.3.9
# git latest
# docker
# Jenkins 2.32.3
# Vim

FROM hmborges/java-maven-git

MAINTAINER Surendra (bajracharya.surendra@gmail.com)

# this is a non-interactive automated build - avoid some warning messages
ENV DEBIAN_FRONTEND noninteractive

# update dpkg repositories
RUN apt-get update

# install wget
RUN apt-get install -y wget

RUN apt-get install -y  \
        ca-certificates \
		curl \
		openssl

# ENV Docker
ENV DOCKER_BUCKET get.docker.com
ENV DOCKER_VERSION 1.13.0
ENV DOCKER_SHA256 fc194bb95640b1396283e5b23b5ff9d1b69a5e418b5b3d774f303a7642162ad6

# docker
RUN curl -fSL "https://${DOCKER_BUCKET}/builds/Linux/x86_64/docker-${DOCKER_VERSION}.tgz" -o docker.tgz \
	&& echo "${DOCKER_SHA256} *docker.tgz" | sha256sum -c - \
	&& tar -xzvf docker.tgz \
	&& mv docker/* /usr/local/bin/ \
	&& rmdir docker \
	&& rm docker.tgz \
	&& docker -v

# docker-compose
RUN curl -L "https://github.com/docker/compose/releases/download/1.9.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose \
    && chmod +x /usr/local/bin/docker-compose

# install nano
RUN apt-get install -y vim

# remove download archive files
RUN apt-get clean

# copy jenkins war file to the container
ADD http://mirrors.jenkins.io/war-stable/2.32.3/jenkins.war /opt/jenkins.war
RUN chmod 644 /opt/jenkins.war
ENV JENKINS_HOME /jenkins

# configure the container to run jenkins, mapping container port 8080 to that host port
ENTRYPOINT ["java", "-jar", "/opt/jenkins.war"]
EXPOSE 8080

#VOLUME $JAVA_HOME $MAVEN_HOME $JENKINS_HOME

CMD [""]
