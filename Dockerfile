FROM jboss/wildfly

ADD target/ftsweb.war /opt/jboss/wildfly/standalone/deployments/


# Dockerizing the project:
# FOR RUNNING FTSWEB on wildfly:
# A.  docker build -t gitsuren/ftsweb-wildfly .
# B.  docker run -it -p 8080:8080 gitsuren/ftsweb-wildfly

# Launch the app using the link: http://localhost:8080/ftsweb/admin
