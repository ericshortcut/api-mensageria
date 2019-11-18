# API Mensageria

API tem como objetivo receber mensagens no formato json, através de uma fila kafka, e enviá-las via SMS.

Apresentação: https://slides.com/ericshortcut/spring-sms/fullscreen

## Ambiente de Desenvolvimento

> Para rodar localmente você precisará de ter instalado o Docker.
> Passo1: Executar o docker do kafka
> Passo2: Entrar no Docker e criar o tópico
> Passo3: Clonar e executar o projeto
> Passo4: Executar chamada do tópico.


## Passo1: Executar o docker do kafka
docker-compose.yml
```
---
version: '2'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - 9092:9092
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```


## Passo2: Entrar no Docker e criar o tópico

Verifique os docker que estão sendo executados `docker ps`. Acesse o docker que está rodando o kafka, nesse momento `docker exec -it b3dbc`. Crie o tópico através do comando:
 - kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic sms_message_send
O `b3dbc` deve ser encontrado através do `docker ps`.


## Passo3: Clonar e executar o projeto

Execute `git clone https://github.com/ericshortcut/api-mensageria/`. Entre na pasta `cd api-mensageria` e execute o projeto `mvn install -DskipTests && mvn spring-boot:run`.


## Passo4: Executar chamada do tópico

Executar a chamada, enviando o json:
```
docker exec -it b3db kafka-console-producer --broker-list localhost:9092 --topic sms_message_send
>{ "to":"+123", "body":"Some Message"}
```
OBS: O `b3dbc` deve ser encontrado através do `docker ps`. O número `+123` deve ser trocado por um número válido.



# Comandos Úteis

Comandos Kafka:
```

# Create topic
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic topic_name

# Delete topic
kafka-topics  --bootstrap-server localhost:9092 --delete --topic topic_name

# Publish Message
kafka-console-producer --broker-list localhost:9092 --topic topic_name
> { "to":"+123", "body": "Some Message"}

# Get all topic messages from begining
kafka-console-consumer --bootstrap-server localhost:9092 --topic topic_name --from-beginning

# Delete topic
kafka-topics  --bootstrap-server localhost:9092 --delete --topic topic_name
```
