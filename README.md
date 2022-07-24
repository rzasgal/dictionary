
# Dictionary

App for saving entries and searching among them.




## Deploy

To Deploy this project

```bash
  minikube start
  
  mkdir -p /var/lib/minikube/data/elasticsearch 
  && mkdir -p /var/lib/minikube/data/kafka 
  && mkdir -p /var/lib/minikube/data/zookeeper/data 
  && mkdir -p /var/lib/minikube/data/zookeeper/log
  && mkdir -p /var/lib/minikube/data/mongodb
  
  kubectl apply -f ./minikube/elasticsearch/persistencevolumes.yml
  kubectl apply -f ./minikube/elasticsearch/elasticsearch.yml
  kubectl apply -f ./minikube/kafka/persistencevolumes.yml
  kubectl apply -f ./minikube/kafka/kafka.yml
  kubectl apply -f ./minikube/mongodb/persistencevolumes.yml
  kubectl apply -f ./minikube/mongodb/mongodb.yml
  
  minikube tunnel &
  
  ./gradlew clean build
  
  docker build -t dictionary .
```

## Tech Stack

- Java 17
- Spring Boot 2.6.5
- MongoDb 4.4.13
- Elasticsearch 7.16.3
- Kafka 3.2.0

## Sample Requests

Sample Requests are in the "dictionary.har" file

## Security

Simple http basic security was implemented
```bash
username: user
password: xynnQowM0K8stjfjltQq
```

  