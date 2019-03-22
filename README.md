# Big Data Analysis of Mobile Service
## Vision and Goals
The project is the practice of big data analysis, we first come up with this idea because we are curious about today's new technology trend like cloud computing and big data, and we want to master these piece of knowledge, so we create this project.  

Our goal is to analyze stream data from mobile service. Every time you call someone, it will generate call log including call duration, call frequency, etc. We want to use big data technology like Hadoop to analyze this raw data and transfer to what we want and visualize the result.  
## Users of the project and User Stories
Users of the project
- Telecom operators
- Government sector
- data analyst  

User Stories

- 
- 


## Architecture and Design
Picture 1 is the Architecture Diagram
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/framework.png" height="600" width="600"></div>

Picture 2 is the consume part, which is what we will do in Sprint 2
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Consume.png" height="400" width="400"></div>

Picture 3 is analysis part, which is what we will do in Sprint 3
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Analysis.png" height="400" width="400"></div>

Picture 4 is visualization part, which is what we will do in Sprint 4
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Visualization.png" height="400" width="400"></div>
But right now, we haven't decided which web framework to use in visualization part, maybe Java SSM or maybe React, we will discuss in the future.

## Solution Concept
- Flume
- Kafka, Zookeeper
- HBase, HDFS
- MapReduce
- Hive

## Acceptance criteria

## Release Planning
- Sprint 1: Write project framework, architecture and design, user stories and form high level picture.
- Sprint 2: Start to conduct data part, which contains generate stream data, deploy environment, using Flume as Kafka producer to collect data.
- Sprint 3: Store data into Hbase and analyze data using MapReduce and store analyzed data into MySQL.
- Sprint 4: Visualize data result and show them in Java-based framework.

zookeeper start: bin/zookeeper-server-start.sh config/zookeeper.properties  
kafka server start: bin/kafka-server-start.sh config/server.properties  
kafka topic: bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic mytopic  
kafka topic list: bin/kafka-topics.sh --list --zookeeper localhost:2181  
kafka consumer start: bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mytopic --from-beginning  
kafka producer start: bin/kafka-console-producer.sh --broker-list localhost:9092 --topic mytopic
flume start: bin/flume-ng agent --conf conf/ --name a1 --conf-file /Users/kobale/Desktop/bigdata/source/apache-flume-1.9.0-bin/conf/flume-exec-kafka.conf
sudo bin/flume-ng agent --conf conf --conf-file conf/flume-exec-kafka.conf --name a1 -Dflume.root.logger=INFO,console