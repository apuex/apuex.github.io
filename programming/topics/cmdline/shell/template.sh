#! /bin/bash

# Web API test script template for Copy & Paste oriented programming ^_^||...

PRG_DIR=$(cd $(dirname "$0"); pwd)

SERVER_IP="192.168.0.197"
SERVER_PORT="9000"
MY_DATA_ID=""

OPTS=$(getopt -os:p:d: --long server-ip:,server-port:,my-data-id: -- $@)

if [[ $? -eq 0 ]]
then
  while true; do
    case "$1" in
      -s | --server-ip)
        SERVER_IP="$2"; shift 2;;
      -p | --server-port)
        SERVER_PORT="$2"; shift 2;;
      -d | --my-data-id)
        MY_DATA_ID="$2"; shift 2;;
      *) break;;
    esac
  done
else
  cat << EOF
Usage: <cmd> <options>
valid options are:
  -s, --server-ip <server-ip>      SERVER IP address
  -p, --server-port <server-port>  SERVER PORT
  -d, --my-data-id <my-data-id>    MY DATA ID, to be replaced with your own ^_^||...
EOF
  exit -1;
fi

REQUEST=\'$(cat << EOF
{
  "myDataId": "${MY_DATA_ID}"
}
EOF
)\'

echo SERVER_IP=${SERVER_IP}
echo SERVER_PORT=${SERVER_PORT}
echo MY_DATA_ID=${MY_DATA_ID}
echo REQUEST=${REQUEST}

CMD="curl -H 'Content-Type: application/json' -X POST -d ${REQUEST} http://${SERVER_IP}:${SERVER_PORT}/api/my-call"

echo ${CMD}
RESPONSE=$(echo ${CMD} | bash)

# implement your own code to check this ${RESPONSE}...

echo RESPONSE=${RESPONSE}


