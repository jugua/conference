phase=$1;
if [[ -z $phase ]]; then
 phase="copy";
fi;

if [ $phase == "copy" ]; then
 rm -rd D:/tomcat-apache/webapps/ROOT;
 rm -rd D:/tomcat-apache/logs;
 mkdir D:/tomcat-apache/logs;
 unzip -q webapp/target/conference.war -d D:/tomcat-apache/webapps/ROOT;
 phase="run";
fi;

if [ $phase == "run" ]; then
 echo Running default build;
 (cd D:/tomcat-apache && bin/catalina.sh jpda start && tail -f logs/catalina.out)
fi;
if [ $phase == "stop" ]; then
 echo Running default build;
 (cd D:/tomcat-apache && bin/catalina.sh jpda stop)
fi;