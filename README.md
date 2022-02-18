# Sales Application Service
## Daniel Machado Vasconcelos

This repository serve the propose of ilustrating the Saga Pattern with Orchestration. 
It is a orchestration service who coordenate sales, payments, delivery, warehouse serivce flow for place, cancel and refund orders. 

### Basic requirements (that were implemented):
* Expose endpoints to place, cancel and refund orders;
* The service must cordenate between multiple services, like payments, shipment, warehouse, etc...;
* The services must cominucate using Saga Pattern with Orchestrations;
* All comunicaton must be done Asyncronous;

### Extra requirements to be done:
* Add security layer to permit only authenticated users to operate;
* Add swagger documentation to the Rest API;
* Make sure all exchanged messages are encrypted, authenticated and has schema.

---
Prerequisites
-------------

* Java JDK 17
* Gradle 
* Docker / Docker Compose

#### Resources
* Kafka
* Elasticsearch

### Tech Stack usage

**Kafka**

I used kafka as my message broker in order to coordenate between the services

**Elasticsearch**

This database saves the list of events in order, ensured by an optimistic lock.


## How to build?

Clone this repo into new project folder (e.g., `base-orchestrator`).

```bash
git clone https://github.com/DanielMachadoVasconcelos/base-orchestrator.git
cd base-orchestrato
```

Start the external resources by running the docker-compose file. (It may take a while to start all resources)
```bash
docker-compose up -d 
```
---

Use the following commands to test the whole saga: (e.g., `makefile build and test`).
```bash
make bt
```

This database saves the list of events in order, ensured by an optimistic lock.


## How to build?

Clone this repo into new project folder (e.g., `base-orchestrator`).

```bash
git clone https://github.com/DanielMachadoVasconcelos/base-orchestrator.git
cd base-orchestrato
```

Start the external resources by running the docker-compose file. (It may take a while to start all resources)
```bash
docker-compose up -d 
```
---

Use the following commands to test the whole saga: (e.g., `makefile build and test`).
```bash
make bt
```

#### Change /etc/hosts file
Since the tests depend on the available resources, to make it easier to test add this to your /etc/hosts file

```conf
# host mapping for the base orchestrator
127.0.0.1 kafka
127.0.0.1 sales
127.0.0.1 elasticsearch
127.0.0.1 zookeeper
```