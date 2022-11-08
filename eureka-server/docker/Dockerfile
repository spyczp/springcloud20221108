FROM openjdk:8
ENV workdir=/root/atom/eureka-server
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8761
CMD ["java", "-jar", "eureka-server-1.0.jar"]
