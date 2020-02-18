# compile our project with maven
.PHONY: build
build: 
	cd jmeter && mvn compile && mvn package

.PHONY: docker-build
docker-build:
	docker build -t 03_springboot_rest_stress_testing .

.PHONY: docker-run
docker-run:
	docker run -p 8080:8080 03_springboot_rest_stress_testing