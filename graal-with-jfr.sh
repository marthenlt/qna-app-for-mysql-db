#!/bin/bash

echo "Run with GraalVM Enterprise (JFR enabled)"

rm -f graal.jfr

java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=GraalVMJFR,settings=profile,filename=./graal.jfr -XX:FlightRecorderOptions=loglevel=info -jar target/qna-app-for-mysql-db-0.1.jar
