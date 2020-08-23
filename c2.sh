#!/bin/bash

echo "Run with Java"

java -XX:-UseJVMCICompiler -jar target/qna-app-for-mysql-db-0.1.jar
