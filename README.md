(This README.md instructs on how to install the project on Ubuntu 16.10)

Most of it is copy-pasted from the official documentation here:
http://kafka.apache.org/documentation/#quickstart

//Download and ungzip
wget http://apache.javapipe.com/kafka/0.10.1.0/kafka_2.11-0.10.1.0.tgz
tar -xzf kafka_2.11-0.10.1.0.tgz\

//Start zookeeper and Kafka
cd kafka_2.11-0.10.1.0
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &

//Create a new topci and check if all went well
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic trafic_sim_test
(Note, if you wish to change the topic name you must change it in application.conf as well)
bin/kafka-topics.sh --list --zookeeper localhost:2181
//Should output --> trafic_sim_test (and possibly some debug info)

Now install a graphhopper server locally:

wget https://oss.sonatype.org/content/groups/public/com/graphhopper/graphhopper-web/0.9-SNAPSHOT/graphhopper-web-0.9-20161215.085755-70-bin.zip
unzip graph...
wget http://download.geofabrik.de/europe/germany-latest.osm.pbf
java -jar *.jar jetty.resourcebase=webapp config=config-example.properties datareader.file=berlin-latest.osm.pbf

Setup the project:

Install Scala and sbt with a package manager of your choice.

git clone