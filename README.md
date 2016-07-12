# FTS
Test Feature Toggle system with MongoDB

Requires:
-   Java 1.7 +
-   Maven
-   MongoDB as backend
-   Spring 4+


If you want to remote debug the code while running through jetty:
$ export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8889,server=y,suspend=n"

and then add the remote debugger to port 8889
