#!/bin/bash

DIRNAME=$(dirname "$0")
if [ "$DIRNAME" == "" ]; then
  DIRNAME="."
fi
APP_BASE_NAME=$(basename "$0")
APP_HOME=$(cd "$DIRNAME/.." && pwd)

# Add default JVM options here. You can also use JAVA_OPTS and TRACKER_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

# Find java executable
if [ -n "$JAVA_HOME" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
else
  JAVA_EXE="java"
fi

"$JAVA_EXE" -version > /dev/null 2>&1
if [ $? -ne 0 ]; then
  echo "ERROR: 'java' command not found. Please set the JAVA_HOME variable to your Java installation."
  exit 1
fi

# Setup the classpath
CLASSPATH="$APP_HOME/lib/tracker.jar:$APP_HOME/lib/ipv8-jvm.jar:$APP_HOME/lib/ipv8.jar:$APP_HOME/lib/kotlin-reflect-1.7.10.jar:$APP_HOME/lib/kotlin-logging-1.7.7.jar:$APP_HOME/lib/sqlite-driver-1.5.2.jar:$APP_HOME/lib/jdbc-driver-1.5.2.jar:$APP_HOME/lib/runtime-jvm-1.5.2.jar:$APP_HOME/lib/kotlinx-coroutines-core-jvm-1.6.4.jar:$APP_HOME/lib/kotlin-stdlib-jdk8-1.7.10.jar:$APP_HOME/lib/kotlin-stdlib-jdk7-1.7.10.jar:$APP_HOME/lib/kotlin-stdlib-1.7.10.jar:$APP_HOME/lib/lazysodium-java-5.1.4.jar:$APP_HOME/lib/resource-loader-2.0.2.jar:$APP_HOME/lib/jna-5.12.1.jar:$APP_HOME/lib/slf4j-simple-2.0.6.jar:$APP_HOME/lib/kotlin-stdlib-common-1.7.10.jar:$APP_HOME/lib/annotations-13.0.jar:$APP_HOME/lib/slf4j-api-2.0.6.jar:$APP_HOME/lib/commons-net-3.6.jar:$APP_HOME/lib/json-20201115.jar:$APP_HOME/lib/bcprov-jdk15to18-1.63.jar:$APP_HOME/lib/sqlite-jdbc-3.34.0.jar"

# Execute tracker
"$JAVA_EXE" $DEFAULT_JVM_OPTS $JAVA_OPTS $TRACKER_OPTS -classpath "$CLASSPATH" nl.tudelft.ipv8.tracker.TrackerKt "$@"

exit $?
