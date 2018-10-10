from openjdk:8u181-jdk
volume /data
copy doc-eureka*.jar /data/doc-eureka.jar
entrypoint java -jar -Xms1G -Xmx1G /data/doc-eureka.jar --spring.profiles.active=node1
expose 9301