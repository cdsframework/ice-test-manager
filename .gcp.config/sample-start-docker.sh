#!/bin/bash

export IMAGE=gcr.io/cdsframework/ice-test-manager:develop

docker pull $IMAGE

docker run \
    --log-opt max-size=10m \
    --log-opt max-file=5 \
    --restart=always \
    -d \
    --name mts \
    --net container-net \
    -h mts \
    --ip 172.100.0.50 \
    -e CAT_CONTEXT=cat \
    -e MTS_DATA_SOURCE_PROPERTIES="DatabaseName=cdsfw:PortNumber=5432:User=rckms_mts_dev:Url=jdbc\\:postgresql\\://dbhost/cdsfw:Password=rckms_mts_dev:ServerName=dbhost" \
    -e CDS_DATA_SOURCE_PROPERTIES="DatabaseName=cdsfw:PortNumber=5432:User=rckms_cds_dev:Url=jdbc\\:postgresql\\://dbhost/cdsfw:Password=rckms_cds_dev:ServerName=dbhost" \
    -e MAX_HEAP=1024m \
    -e CAT_USER=cat \
    -e CAT_PASSWORD=password \
    -e CAT_APP=CAT \
    -e TZ=America/New_York \
    $IMAGE
