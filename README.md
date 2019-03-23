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
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/framework.png" height="550" width="750"></div>

Picture 2 is the consume part, which is what we will do in Sprint 2
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Consume.png" height="400" width="500"></div>

Picture 3 is analysis part, which is what we will do in Sprint 3
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Analysis.png" height="400" width="500"></div>

Picture 4 is visualization part, which is what we will do in Sprint 4
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Visualization.png" height="400" width="500"></div>

But right now, we haven't decided which web framework to use in visualization part, maybe Java SSM or maybe React, we will discuss in the future.  

Our design is quite straightforward. The whole system is primarily on Java-based big data analysis. First is **data producer** part, then is **data consumer** part, finally is **data shower** part. We follow the schedule, on each part, we can do unit test, continuous integration and anything else, it's quite easy for us to make some changes in the whole process using this kind of design.
## Solution Concept
### Flume
Flume is a distributed, reliable, and available service for efficiently collecting, aggregating, and moving large amounts of log data. It has a simple and flexible architecture based on streaming data flows. It is robust and fault tolerant with tunable reliability mechanisms and many failover and recovery mechanisms. Flume is often used in the condition with **small amount of consumer**, this is why we use Flume to connected to Hadoop ecosystem.
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Flume.png" height="400" width="600"></div>

### Kafka, Zookeeper
Apache Kafka is a distributed streaming platform. We use it to build **real-time streaming data pipelines** that reliably get data between systems or applications. We use kafka as consumer to receive data from Flume.

Apache ZooKeeper is a software project of the Apache Software Foundation. It is essentially a centralized service for distributed systems to a hierarchical key-value store, which is used to provide a distributed configuration service, synchronization service, and naming registry for large distributed systems.
Zookeeper
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/Kafka-Zookeeper.png" height="400" width="600"></div>

### HBase, HDFS
Apache HBase is an open-source, distributed, versioned, non-relational database modeled after [Google's Bigtable: A Distributed Storage System for Structured Data](https://static.googleusercontent.com/media/research.google.com/zh-CN//archive/gfs-sosp2003.pdf). Just as Bigtable leverages the distributed data storage provided by the Google File System, Apache HBase provides Bigtable-like capabilities **on top of Hadoop and HDFS**.
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/HBase-HDFS.png" height="400" width="600"></div>

### MapReduce
MapReduce is one of the most important component in Hadoop, which is a software framework for easily writing applications which process vast amounts of data (multi-terabyte data-sets) in-parallel on large clusters (thousands of nodes) of commodity hardware in a reliable, fault-tolerant manner.
<div align = center><img src = "https://github.com/ec500-software-engineering/project-08-bigdata_yq_hx/blob/master/img/MapReduce.png" height="300" width="600"></div>

## Acceptance criteria
Minimum Viable Product
- /
- 
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
