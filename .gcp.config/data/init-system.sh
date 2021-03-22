#!/bin/bash

#### TIMEZONE
if [[ -v TZ ]]
then
    echo "TZ is set to: $TZ"
    sed -i "/>-Xmx.*</ a <jvm-options>-Duser.timezone=$TZ</jvm-options>" ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/config/domain.xml
fi

#### GLASSFISH MTS DATA SOURCE

if [[ ! -v MTS_DATA_SOURCE_RES_TYPE ]]
then
    export MTS_DATA_SOURCE_RES_TYPE=javax.sql.XADataSource
    echo "setting default for MTS_DATA_SOURCE_RES_TYPE"
fi
echo "MTS_DATA_SOURCE_RES_TYPE is set to: $MTS_DATA_SOURCE_RES_TYPE"

if [[ ! -v MTS_DATA_SOURCE_CLASS_NAME ]]
then
    export MTS_DATA_SOURCE_CLASS_NAME=org.postgresql.xa.PGXADataSource
    echo "setting default for MTS_DATA_SOURCE_CLASS_NAME"
fi
echo "MTS_DATA_SOURCE_CLASS_NAME is set to: $MTS_DATA_SOURCE_CLASS_NAME"

if [[ ! -v MTS_DATA_SOURCE_PROPERTIES ]]
then
    echo "ERROR!!! MTS_DATA_SOURCE_PROPERTIES is not set - this value is required!"
#    exit -1
fi
echo "MTS_DATA_SOURCE_PROPERTIES is set to: $MTS_DATA_SOURCE_PROPERTIES"

#### GLASSFISH CDS DATA SOURCE

if [[ ! -v CDS_DATA_SOURCE_RES_TYPE ]]
then
    export CDS_DATA_SOURCE_RES_TYPE=javax.sql.XADataSource
    echo "setting default for CDS_DATA_SOURCE_RES_TYPE"
fi
echo "CDS_DATA_SOURCE_RES_TYPE is set to: $CDS_DATA_SOURCE_RES_TYPE"

if [[ ! -v CDS_DATA_SOURCE_CLASS_NAME ]]
then
    export CDS_DATA_SOURCE_CLASS_NAME=org.postgresql.xa.PGXADataSource
    echo "setting default for CDS_DATA_SOURCE_CLASS_NAME"
fi
echo "CDS_DATA_SOURCE_CLASS_NAME is set to: $CDS_DATA_SOURCE_CLASS_NAME"

if [[ ! -v CDS_DATA_SOURCE_PROPERTIES ]]
then
    echo "ERROR!!! CDS_DATA_SOURCE_PROPERTIES is not set - this value is required!"
#    exit -1
fi
echo "CDS_DATA_SOURCE_PROPERTIES is set to: $CDS_DATA_SOURCE_PROPERTIES"

#### GLASSFISH HEAP

if [[ ! -v MAX_HEAP ]]
then
    export MAX_HEAP=1024m
    echo "setting default for $MAX_HEAP"
fi
echo "MAX_HEAP is set to: $MAX_HEAP"

#### cat.properties

if [[ ! -v CAT_USER ]]
then
    export CAT_USER=cat
    echo "setting default for CAT_USER"
fi
echo "CAT_USER is set to: $CAT_USER"

if [[ ! -v CAT_PASSWORD ]]
then
    export CAT_PASSWORD=password
    echo "setting default for CAT_PASSWORD"
fi
echo "CAT_PASSWORD is set to: ********"

if [[ ! -v CAT_APP ]]
then
    export CAT_APP=CAT
    echo "setting default for CAT_APP"
fi
echo "CAT_APP is set to: $CAT_APP"

export CAT_USER=$(echo "${CAT_USER}" | sed -e 's/[]$.*\/{}[\^]/\\&/g')
export CAT_PASSWORD=$(echo "${CAT_PASSWORD}" | sed -e 's/[]$.*\/{}[\^]/\\&/g')
export CAT_APP=$(echo "${CAT_APP}" | sed -e 's/[]$.*\/{}[\^]/\\&/g')

sed -i "s/CAT_USER/${CAT_USER}/g" /data/cat.properties
sed -i "s/CAT_PASSWORD/${CAT_PASSWORD}/g" /data/cat.properties
sed -i "s/CAT_APP/${CAT_APP}/g" /data/cat.properties

#### ejb-core.properties

if [[ ! -v MTS_MASTER ]]
then
    export MTS_MASTER=true
    echo "setting default for MTS_MASTER"
fi
echo "MTS_MASTER is set to: $MTS_MASTER"

if [[ ! -v CDS_ENDPOINT ]]
then
    export CDS_ENDPOINT=http://dss:8080/opencds-decision-support-service/evaluate
    echo "setting default for CDS_ENDPOINT"
fi
echo "CDS_ENDPOINT is set to: $CDS_ENDPOINT"

if [[ ! -v CAT_BS_URI ]]
then
    export CAT_BS_URI=http://cat:8080/cat-rckms
    echo "setting default for CAT_BS_URI"
fi
echo "CAT_BS_URI is set to: $CAT_BS_URI"

if [[ ! -v RCKMS_SHARED_SERVICE_URI ]]
then
    export RCKMS_SHARED_SERVICE_URI=http://ss:3000
    echo "setting default for RCKMS_SHARED_SERVICE_URI"
fi
echo "RCKMS_SHARED_SERVICE_URI is set to: $RCKMS_SHARED_SERVICE_URI"

export CDS_ENDPOINT=$(echo "${CDS_ENDPOINT}" | sed -e 's/[]$.*\/{}[\^:]/\\&/g')
echo "CDS_ENDPOINT is set to: $CDS_ENDPOINT"
export CAT_BS_URI=$(echo "${CAT_BS_URI}" | sed -e 's/[]$.*\/{}[\^:]/\\&/g')
echo "CAT_BS_URI is set to: $CAT_BS_URI"
export RCKMS_SHARED_SERVICE_URI=$(echo "${RCKMS_SHARED_SERVICE_URI}" | sed -e 's/[]$.*\/{}[\^:]/\\&/g')
echo "RCKMS_SHARED_SERVICE_URI is set to: $RCKMS_SHARED_SERVICE_URI"

sed -i "s/=MTS_MASTER/=${MTS_MASTER}/g" /data/ejb-core.properties
sed -i "s/=CDS_ENDPOINT/=${CDS_ENDPOINT}/g" /data/ejb-core.properties
sed -i "s/=CAT_BS_URI/=${CAT_BS_URI}/g" /data/ejb-core.properties
sed -i "s/=RCKMS_SHARED_SERVICE_URI/=${RCKMS_SHARED_SERVICE_URI}/g" /data/ejb-core.properties

####

export ADMIN_PASSWORD=password

sed -i '/EOF/d' /opt/pwdfile
sed -i 's/admin/password/g' /opt/pwdfile
sed -i "s/Xmx512m/Xmx${MAX_HEAP}/g" ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/config/domain.xml

mv $DEPLOY_DIR/cat.war $DEPLOY_DIR/$CAT_CONTEXT.war

cp /data/cat.properties ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/config/
cp /data/ejb-core.properties ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/config/
cp /data/logging.properties ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/config/
cp -rf /data/iceweb ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/docroot
cp -rf /data/favicon.ico ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/docroot
cp -rf /data/favicon.ico ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/docroot/iceweb
cp -rf /data/cdsframework ${PAYARA_PATH}/glassfish/domains/${PAYARA_DOMAIN}/docroot

echo "POSTBOOT_COMMANDS is set to: ${POSTBOOT_COMMANDS}"
echo "PAYARA_DOMAIN is set to: ${PAYARA_DOMAIN}"

cat ${POSTBOOT_COMMANDS}

${PAYARA_PATH}/generate_deploy_commands.sh

cat ${POSTBOOT_COMMANDS}

${PAYARA_PATH}/bin/startInForeground.sh --passwordfile=/opt/pwdfile --postbootcommandfile ${POSTBOOT_COMMANDS} $DEBUG_OPTS ${PAYARA_DOMAIN}
