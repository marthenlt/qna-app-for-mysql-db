#!/usr/bin/env bash

echo "== start timestamp = " && date
time ab -n 50000 -c 1  http://localhost:8080/ask/benchmark
echo "== end timestamp = " && date

