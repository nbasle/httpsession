@echo off

set JAVA=%JAVA_HOME%\bin\java
set RMIREGISTRY=%JAVA_HOME%\bin\rmiregistry

set DEPLOY_DIR=..\build
set LIB_DIR=..\lib

set XML_JAR=%LIB_DIR%\dom4j.jar;%LIB_DIR%\jaxen.jar

set CLASSPATH=%DEPLOY_DIR%\server.jar;%DEPLOY_DIR%\common.jar;%LIB_DIR%\mysql-connector.jar;%XML_JAR%

start %RMIREGISTRY%

%JAVA% -cp %CLASSPATH% com.yaps.petstore.server.RegisterServices

pause