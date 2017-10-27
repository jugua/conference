phase=$1;
if [[ -z $phase ]]; then
 phase="copy";
fi;

if [ $phase == "copy" ]; then

 rm -rd D:/Users/Downloads/apache-tomcat-7.0.82/webapps/ROOT;
 rm -rd D:/Users/Downloads/apache-tomcat-7.0.82/logs;
 mkdir D:/Users/Downloads/apache-tomcat-7.0.82/logs;
 unzip -q webapp/target/conference.war -d D:/Users/Downloads/apache-tomcat-7.0.82/webapps/ROOT;
 phase="run";
fi;

if [ $phase == "run" ]; then
 echo Running default build;
 (cd D:/Users/Downloads/apache-tomcat-7.0.82 && bin/catalina.sh jpda start && tail -f logs/catalina.out)
fi;
if [ $phase == "stop" ]; then
 echo Running default build;
 (cd D:/Users/Downloads/apache-tomcat-7.0.82 && bin/catalina.sh jpda stop)
fi;