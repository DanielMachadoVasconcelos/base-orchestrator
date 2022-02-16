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

bt: #Build & docker-compose test
	$(GW_PARALLEL) clean build shadowJar && docker-compose up --abort-on-container-exit -V --build
