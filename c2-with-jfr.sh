#!/bin/bash

echo "Run with Java"

rm -f c2.jfr

java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=JavaJFR,settings=profile,filename=./c2.jfr -XX:FlightRecorderOptions=loglevel=info -XX:-UseJVMCICompiler -jar target/qna-app-for-mysql-db-0.1.jar
