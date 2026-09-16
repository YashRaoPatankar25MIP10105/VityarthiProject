#!/usr/bin/env bash
set -e
ROOT="$(cd "$(dirname "$0")" && pwd)"
rm -rf "$ROOT/bin"
rm -f "$ROOT/data"/*.csv "$ROOT/out"/*.csv
mkdir -p "$ROOT/bin"
find "$ROOT/src" -name '*.java' > "$ROOT/.sources"
javac -encoding UTF-8 -d "$ROOT/bin" @"$ROOT/.sources"
java -cp "$ROOT/bin" campus.ValidationTest
