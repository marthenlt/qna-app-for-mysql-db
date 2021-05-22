#!/usr/bin/env bash

echo "== start timestamp = " && date
time ab -n 5000 -c 1  http://localhost:30539/ask/benchmark
echo "== end timestamp = " && date

