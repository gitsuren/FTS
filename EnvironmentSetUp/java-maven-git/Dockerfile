FROM cogniteev/oracle-java

MAINTAINER hmborges <henriquemborges8@gmail.com>

RUN rm /bin/sh && ln -s /bin/bash /bin/sh

# Update
RUN apt-get update

# Install wget
RUN apt-get install -y wget

# Get maven 3.3.9
RUN wget --no-verbose -O /tmp/apache-maven-3.3.9.tar.gz http://archive.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz

# install maven
RUN tar xzf /tmp/apache-maven-3.3.9.tar.gz -C /opt/
RUN ln -s /opt/apache-maven-3.3.9 /opt/maven
RUN ln -s /opt/maven/bin/mvn /usr/local/bin
RUN rm -f /tmp/apache-maven-3.3.9.tar.gz
RUN mkdir /root/.m2/
ENV MAVEN_HOME /opt/maven

# Install Git
RUN apt-get install -y git

# Make ssh dir
RUN mkdir /root/.ssh/

# Create known_hosts
RUN touch /root/.ssh/known_hosts
