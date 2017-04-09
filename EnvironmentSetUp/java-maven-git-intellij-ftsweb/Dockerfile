# Ubuntu 16.04 LTS
# Oracle Java 1.8.0_91 64 bit
# Maven 3.3.9
# git latest
# Intellij
# Vim
# checkout ftsweb


FROM hmborges/java-maven-git

MAINTAINER Surendra (bajracharya.surendra@gmail.com)

# update dpkg repositories
RUN apt-get update

# install wget
RUN apt-get install -y wget

# install nano
RUN apt-get install -y vim

# install Intellij IDEA

# git clone ftsweb project
RUN cd /usr/src/ \
    && git clone https://github.com/gitsuren/ftsweb.git \
    && cd ftsweb \
    && mvn clean install
