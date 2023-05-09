FROM payara/server-full:174 AS build

# Metadata
LABEL organization="HLN Consulting, LLC"
LABEL maintainer="Sam Nicolary <sdn@hln.com>"

ENV POSTGRESQL_JDBC_VERSION=42.2.6

# Payara Server
EXPOSE 8080/tcp

COPY .gcp.config/init-payara.sh ${PAYARA_PATH}/
COPY .gcp.config/data /data
COPY iceweb /data/iceweb

USER root

# switch to buster
RUN sed -i 's/stretch/buster/g' /etc/apt/sources.list

# get buster signing keys
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 112695A0E562B32A 648ACFD622F3D138

# check buster fingerprints
RUN apt-key finger 112695A0E562B32A 2>&1 | grep "5E61 B217 265D A980 7A23  C5FF 4DFA B270 CAA9 6DFA" && \
    apt-key finger 648ACFD622F3D138 2>&1 | grep "80D1 5823 B7FD 1561 F9F7  BCDD DC30 D7C2 3CBB ABEE"


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
