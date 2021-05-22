#!/usr/bin/env bash

mvn clean package
sudo docker build -f Dockerfile_c2 . -t localhost:5000/marthen-qna-app-on-c2-jdk8--alpine:4.0
sudo docker push localhost:5000/marthen-qna-app-on-c2-jdk8--alpine:4.0

