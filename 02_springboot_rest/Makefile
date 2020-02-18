# compile our project with maven
.PHONY: build
build: 
	cd rest && mvn compile &&	mvn package

.PHONY: docker-build
docker-build:
	docker build -t 02_springboot_rest .

.PHONY: docker-run
docker-run:
	docker run -i -t 02_springboot_rest