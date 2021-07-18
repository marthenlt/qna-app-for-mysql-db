#!/bin/bash

echo "Run with GraalVM Enterprise (JFR enabled)"

rm -f graal.jfr

# Java 8
java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=GraalVMJFR,settings=profile,filename=./graal.jfr -XX:FlightRecorderOptions=loglevel=info -jar target/qna-app-for-mysql-db-0.1.jar

# Java 11 (remove -XX:+UnlockCommercialFeatures and -XX:FlightRecorderOptions=loglevel=info ; add -Xlog:jfr+event+system=info)
# java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=GraalVMJFR,settings=profile,filename=./graal.jfr -Xlog:jfr+event+system=info -jar target/qna-app-for-mysql-db-0.1.jar
