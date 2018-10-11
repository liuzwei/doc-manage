#jdk环境
from openjdk:8u181-jdk
label doc-eureka="node2-V1.0"
copy doc-eureka*.jar doc-eureka.jar
entrypoint java -jar -Xms1G -Xmx1G doc-eureka.jar --spring.profiles.active=node2
expose 9302