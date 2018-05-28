# Setting Up ELK with Docker

# Index
1. [Pre-requisites](#pre-requisites)
2. [Installation](#installation)
3. [Start your ELK containers](#start-your-elk-containers)
4. [How to access ELK?](#how-to-access-elk)
5. [How to check all indices in Elasticsearch?](#how-to-check-all-indices-in-elasticsearch)
6. [How to delete the index in Elasticsearch?](#how-to-delete-the-index-in-elasticsearch)

# Pre-requisites

1. **Docker:**
    - Install [Docker](https://docker.com/), either using a native package (Linux) or wrapped in a virtual machine (Windows, OS X � e.g. using [Boot2Docker](http://boot2docker.io/) or [Vagrant](https://www.vagrantup.com/)).


2. **A minimum of 4GB RAM assigned to Docker**
    - Elasticsearch alone needs at least 2GB of RAM to run.
    - With Docker for Mac, the amount of RAM dedicated to Docker can be set using the UI: see [How to increase docker-machine memory Mac](http://stackoverflow.com/questions/32834082/how-to-increase-docker-machine-memory-mac/39720010#39720010) (Stack Overflow).


3. **A limit on mmap counts equal to 262,144 or more (****IMPORTANT****)**
    - On Linux: use sysctl vm.max\_map\_count n the host to view the current value, and see [Elasticsearch documentation on virtual memory](https://www.elastic.co/guide/en/elasticsearch/reference/5.0/vm-max-map-count.html#vm-max-map-count) for guidance on how to change this value. Note that the limits **must be changed on the host **; they cannot be changed from within a container.   
Command:  
        ```sudo sysctl -w vm.max\_map\_count=262144```
    - On windows: start the Docker quickstart terminal and enter following commands:  
              ```docker-machine ssh```  
              ```sudo sysctl -w vm.max\_map\_count=262144```

# Installation

### Pull official images of Elasticsearch, Logstash, and Kibana from [https://www.docker.elastic.co](https://www.docker.elastic.co/)

1. ```docker pull docker.elastic.co/elasticsearch/elasticsearch:6.2.2```
2. ```docker pull docker.elastic.co/kibana/kibana:6.2.2```
3. ```docker pull docker.elastic.co/logstash/logstash:6.2.2```

# Start your ELK containers

1. Start Elasticsearch container

   -```docker run --rm -d -it --name es -p 9200:9200 -p 9300:9300 docker.elastic.co/elasticsearch/elasticsearch:6.2.2```

   -If you want to mount the volumes then use following command:  
    ```docker run --rm -d -it --name kibana -p 9200:9200 -p 9300:9300 -v "$PWD/your/dir/path":/usr/share/elasticsearch/data docker.elastic.co/elasticsearch/elasticsearch:6.2.2```

2. Start Kibana container  
```docker run --rm -d -it --name kibana --link es:elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:6.2.2```

3. Start Logstash Container  
     - ```docker run -d -it --name logstash -p 5000:5000 docker.elastic.co/logstash/logstash:6.2.2 -e 'input { tcp { port => 5000 codec => "json" } } output { elasticsearch { hosts => ["localhost"] index => "indexName"} }'```

     - You can also provide the pipeline syntax from logstash.conf file:
```docker run --rm -d -it --name logstash -p 5000:5000 -v "$PWD/path/to/config/dir":/config-dir docker.elastic.co/logstash/logstash:6.2.2 -f /config-dir/logback.conf```

# How to access ELK?

1. Kibana:
    1. Linux: [http://localhost:5601](https://localhost:5601)
    2. Windows: [http://192.168.99.100:5601](http://192.168.99.100:5601) (Virtual Box&#39;s IP)
2. Elasticsearch:
    - To check if elasticsearch is running
      1. Linux: [http://localhost:9200](https://localhost:9200)
      2. Windows: [http://192.168.99.100:9200](http://192.168.99.100:9200)    
    - To check all the indices in elasticsearch
      1. Linux: [ http://localhost:9200/_cat/indices](http://localhost:9200/_cat/indices)
      2. Windows: [http://192.168.99.100:9200/_cat/indices](http://192.168.99.100:9200)

# How to check all indices in Elasticsearch?

1. Linux: [ http://localhost:9200/_cat/indices](http://localhost:9200/_cat/indices)
2. Windows: [http://192.168.99.100:9200/_cat/indices](http://192.168.99.100:9200)

# How to delete the index in Elasticsearch?

1. Linux:  ``curl -XDELETE "http://localhost:9200/"indexname""``
2. Windows (In docker quickstart terminal): ``curl -XDELETE "http://192.168.99.100:9200/"indexName""``

# How to send logs to Elasticsearch?

1. Creat a file ```src/test/resource/logback.xml```.( Refer ```src/test/resource/logback-elk.xml```)
2. Add an ```net.logstash.logback.appender.LogstashTcpSocketAppender``` appender in it and under that appender enter the  logstash IP in destination.
3. When you will execute your code all the log will go in elasticsearch.
4. You can provide the logback.conf file at runtime also just set the environment variable ```logback.configurationFile=path\to\logback.xml```. If no configuration file provided then it will take ```src/test/resource/logback.xml``` as default.
5. You can add fields using ```org.slf4j.MDC.put("key", "value")``` in elasticsearch
6. For more information refer ```src/test/resource/logback-elk.xml```