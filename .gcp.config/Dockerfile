FROM payara/server-full:174 AS build

# Metadata
LABEL organization="HLN Consulting, LLC"
LABEL maintainer="Sam Nicolary <sdn@hln.com>"

ENV POSTGRESQL_JDBC_VERSION=42.2.6

# Payara Server
EXPOSE 8080/tcp

COPY .gcp.config/init-payara.sh ${PAYARA_PATH}/
COPY .gcp.config/data /data

USER root

RUN apt -qq update && \
    apt -qq -y dist-upgrade && \
    apt -qq -y install postgresql-client && \
    curl -s -L -o ${PAYARA_PATH}/glassfish/lib/postgresql-${POSTGRESQL_JDBC_VERSION}.jar https://jdbc.postgresql.org/download/postgresql-${POSTGRESQL_JDBC_VERSION}.jar && \
    chown -R payara:payara ${PAYARA_PATH}/init-payara.sh ${PAYARA_PATH}/glassfish/lib/postgresql-${POSTGRESQL_JDBC_VERSION}.jar && \
    chown payara: /data && \
    chmod 755 /data/init-system.sh

ENV POSTBOOT_COMMANDS ${PAYARA_PATH}/init-payara.sh

USER payara

COPY mts-ear-ice/target/mts.ear $DEPLOY_DIR
COPY cat-ice-primefaces/target/cat.war $DEPLOY_DIR

ENTRYPOINT /data/init-system.sh
