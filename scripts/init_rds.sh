
#!/bin/sh

export POSTGRES_PASSWORD= ""
export POSTGRES_HOST_AUTH_METHOD= trust
export RDS_HOST= localhost 
export RDS_PORT= 5432
export SCHEMA=$1
export USER=$2
export PASS=$3
 [ -z "$SCHEMA" ] && export SCHEMA="svc_political"
 echo ${SCHEMA}
 [ -z "$USER" ] && export USER="test12"
 echo ${USER}
 [ -z "$PASS" ] && export PASS="test"
 echo ${PASS}

export PGUSER=postgres
psql --host=${RDS_HOST} --port=${RDS_PORT} --dbname=template1 --username=${RDS_USERNAME}<<-EOSQL
     CREATE USER ${USER} WITH PASSWORD '${PASS}';
     ALTER USER ${USER} WITH SUPERUSER;
     DROP DATABASE customerportal;
     CREATE DATABASE customerportal;
     GRANT ALL PRIVILEGES ON DATABASE customerportal to ${USER};
     \connect customerportal;
     CREATE SCHEMA IF NOT EXISTS ${SCHEMA} AUTHORIZATION ${USER};
EOSQL
