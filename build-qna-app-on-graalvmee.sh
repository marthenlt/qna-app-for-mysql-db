#!/usr/bin/env bash

mvn clean package
sudo docker build -f Dockerfile_graalvmee . -t localhost:5000/marthen-qna-app-on-oracle-graalvmee-20.3.2-jdk8--alpine:4.0
sudo docker push localhost:5000/marthen-qna-app-on-oracle-graalvmee-20.3.2-jdk8--alpine:4.0

