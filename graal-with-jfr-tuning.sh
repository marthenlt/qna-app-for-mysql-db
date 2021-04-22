#!/bin/bash

echo "Run with GraalVM Enterprise (JFR enabled, and tuned with \"-Dgraal.TuneInlinerExploration=-1\")"

rm -f graal-tuning.jfr

java -Dgraal.TuneInlinerExploration=-1 -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=2m,delay=30s,name=GraalVMTuningJFR,settings=profile,filename=./graal-tuning.jfr -XX:FlightRecorderOptions=loglevel=info -jar target/qna-app-for-mysql-db-0.1.jar
