#!/usr/bin/env bash

mvn clean package
sudo docker build . -t marthenl/qna-app-for-mysql-db:latest

