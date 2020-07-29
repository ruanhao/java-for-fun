#!/usr/bin/env bash
# -*- coding: utf-8 -*-
#
# Description:

# ruan: USER
# hao: ADMIN

code=$( curl -sS -D- -o /dev/null -u ruan:123 \
     'http://localhost:8080/jwt-auth/oauth/authorize?client_id=my-app&grant_type=authorization_code&scope=webclient&client_id=my-app&redirect_uri=http://hao.com:48080&response_type=code' | grep Location | cut -d= -f2 )
code=$( echo $code | sed 's/[^[:print:]\t]//g' )

access_token=$(curl -Ss 'http://localhost:8080/jwt-auth/oauth/token' --user my-app:thisissecret -d grant_type=authorization_code -d code=$code -d redirect_uri=http://hao.com:48080 | jq -r '.access_token')

curl -Ss -H "Authorization: Bearer $access_token" http://localhost:8081/jwt-service/v1/user/hello # ok
echo
curl -Ss -H "Authorization: Bearer $access_token" http://localhost:8081/jwt-service/v1/admin/hello # access is denied
echo

echo '======================='

code=$( curl -sS -D- -o /dev/null -u hao:123 \
     'http://localhost:8080/jwt-auth/oauth/authorize?client_id=my-app&grant_type=authorization_code&scope=webclient&client_id=my-app&redirect_uri=http://hao.com:48080&response_type=code' | grep Location | cut -d= -f2 )
code=$( echo $code | sed 's/[^[:print:]\t]//g' )

access_token=$(curl -Ss 'http://localhost:8080/jwt-auth/oauth/token' --user my-app:thisissecret -d grant_type=authorization_code -d code=$code -d redirect_uri=http://hao.com:48080 | jq -r '.access_token')

curl -Ss -H "Authorization: Bearer $access_token" http://localhost:8081/jwt-service/v1/user/hello # access is denied
echo
curl -Ss -H "Authorization: Bearer $access_token" http://localhost:8081/jwt-service/v1/admin/hello # ok
echo
