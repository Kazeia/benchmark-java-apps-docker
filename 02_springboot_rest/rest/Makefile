# compile our project with maven
.PHONY: build
build: 
	mvn compile
	mvn package

.PHONY: docker-build
docker-build:
	docker build -t 01_empty .

.PHONY: docker-run
docker-run:
	docker run -i -t 01_empty