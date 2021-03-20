#!/bin/bash

export IMAGE=gcr.io/cdsframework/ice-test-manager:master

docker pull $IMAGE

docker run \
    --log-opt max-size=10m \
    --log-opt max-file=5 \
    --restart=always \
    -d \
    --name ice-test-manager \
    --net container-net \
    --add-host dbhost:172.100.0.1 \
    -h ice-test-manager \
    --ip 172.100.0.99 \
    -e CAT_CONTEXT=cat \
    -e MTS_DATA_SOURCE_PROPERTIES="DatabaseName=cdsfw:PortNumber=5432:User=dev_mts:Url=jdbc\\:postgresql\\://dbhost/cdsfw:Password=dev_mts:ServerName=dbhost" \
    -e CDS_DATA_SOURCE_PROPERTIES="DatabaseName=cdsfw:PortNumber=5432:User=dev_cds:Url=jdbc\\:postgresql\\://dbhost/cdsfw:Password=dev_cds:ServerName=dbhost" \
    -e MAX_HEAP=1024m \
    -e CAT_USER=cat \
    -e CAT_PASSWORD='vtyv$240vtigv%weUT943' \
    -e CAT_APP=CAT \
    -e TZ=America/New_York \
    $IMAGE
