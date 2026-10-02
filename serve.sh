#!/bin/bash
# Lance le site en local : http://localhost:8080
cd "$(dirname "$0")" && python3 -m http.server 8080 --directory site
