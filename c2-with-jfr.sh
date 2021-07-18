#!/bin/bash

echo "Run with Java"

rm -f c2.jfr

# Java 8
java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=JavaJFR,settings=profile,filename=./c2.jfr -XX:FlightRecorderOptions=loglevel=info -XX:-UseJVMCICompiler -jar target/qna-app-for-mysql-db-0.1.jar

# Java 11 (remove -XX:+UnlockCommercialFeatures and -XX:FlightRecorderOptions=loglevel=info ; add -Xlog:jfr+event+system=info)
#java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=JavaJFR,settings=profile,filename=./c2.jfr -Xlog:jfr+event+system=info -XX:-UseJVMCICompiler -jar target/qna-app-for-mysql-db-0.1.jar
