GW=./gradlew $(FLAGS)
GW_PARALLEL=$(GW) --parallel
GW_FAST=$(GW_PARALLEL) -x test

all: build

clean:
	$(GW_PARALLEL) clean

build:
	$(GW_PARALLEL) build

build-fast:
	$(GW_FAST) build

swagger:
	$(GW_PARALLEL) generateSwaggerDocumentation --no-daemon

avro:
	$(GW_PARALLEL) :avro-generator:run --no-daemon

publish-to-maven-local:
	$(GW_PARALLEL) publishToMavenLocal

shadowJar: #Build & docker-compose test
	$(GW_PARALLEL) clean build shadowJar

infra: #Build & docker-compose teste
	 docker-compose up --abort-on-container-exit -V zookeeper kafka elasticsearch

startSales: #Build & docker-compose teste
	docker-compose up sales --abort-on-container-exit -V --build

test: #Build & docker-compose teste
	docker-compose up integration-tests --abort-on-container-exit -V --build

bt: #Build & docker-compose test
	$(GW_PARALLEL) clean build shadowJar && docker-compose up --abort-on-container-exit -V --build --remove-orphans
