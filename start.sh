#!/bin/sh
# shellcheck disable=SC2039
if [[ $# != 1 ]]; then
   echo "USAGE $0 option<deploy | run>"
   exit 1
fi
option=$1
echo $1
zip_file_name="application.zip"
app_file_name="chartservice-0.0.1-SNAPSHOT.jar"

# shellcheck disable=SC2120
# shellcheck disable=SC2112
function deploy() {
  cd /tmp || exit
  unzip -o "${zip_file_name}" -d ~/
  cd ~ || exit
  # +x and execute
  chmod +x "${app_file_name}"
}

# shellcheck disable=SC2120
# shellcheck disable=SC2112
function run() {
  cd ~ || exit
  sleep 20s
  # 请务必使用后台运行的方式
  java -Xms3096m -Xmx3096m -XX:+UseG1GC -jar "${app_file_name}" > run.log &
  sleep 20s
}

# shellcheck disable=SC2166
if [ "$option" == 'deploy' ]
then
  deploy
elif [ "$option" == 'run' ]
then
  run
else
  deploy
  run
fi



