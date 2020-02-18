Proyectos de prueba:
1. Hardware:
CPU (/proc/cpuinfo): core i5-8250U CPU @1.60GHz
Memory (/proc/meminfo): 8GB
SDD M.2: NVME Samsung 970 pro 520GB

2. Software:
mvn --version
 - Apache Maven 3.6.3
java -v
 - openjdk version "1.8.0_242"
 - OpenJDK Runtime Environment (build 1.8.0_242-b08)
 - OpenJDK 64-Bit Server VM (build 25.242-b08, mixed mode)
docker -v
 - Docker version 19.03.5-ce, build 633a0ea83


---
A continuacion se describen los proyectos  a analizar, los proyectos seran contenerizados y posteriormente analizado con las siguientes herramientas:

1. docker stats
2. Prometheus: http://www.prometheusanalytics.net
prometheus consume:
 - ImageSize: 134 MB
 - CPU: Min: 0.31%
 - RAM: 31.99MB
grafana consume:
 - ImageSize: 233MB
 - CPU: Min: 0.05%
 - RAM: 18.68MB


Nomenclatura de los proyectos:

0. La imagen base:
- openjdk-8-alpine, Tamano: 105M

1. 01_empty: Proyecto basico que imprime hello world en consola y termina cuando se presiona enter.
 - docker image size: 105MB
 - consumo de recursos: 
  - RAM: 10.63 MB
  - CPU: 0.9%
  - NET I/O: 4.44kB

  [PEGAR IMAGEN: Sura-benchmark-01_empty.png]

2. 02_springboot_rest: Proyecto APIRest con springboot, un solo recurso y verbo GET: / -> devuelve un json de la forma: { "res" : "helloWorld"} 
 - springboot 2.1.12 
 - spring-boot-start-web
 - spring-boot-devtools
 - spring-boot-starter-test
 - docker image size: 122MB
 - consumo de recursos:
  - RAM: 238.5 MB
  - CPU: 0.20% -> alertar a carlos q sube demasiado cuando se le hace consultas
  - NET I/O: 10.2kB
  
  [PEGAR IMAGEN: Sura-benchmark-02_springboot_rest_.png]

3. 03_springboot_rest_stress_testing: Proyecto springboot rest, con diferentes algoritmos para medir el tipo de carga via JMeter
Link de Algoritmos: https://benchmarksgame-team.pages.debian.net/benchmarksgame/fastest/java-csharpcore.html
 - springboot 2.1.12
 - spring-boot-starter-parent
 - /test1 -> Algoritmo n-body
 - /test2 -> algoritmos binary-trees
 - /test3 -> regex-redux
 - docker image size: 122MB
 - consumo de recursos:
  - RAM: 255 MB
  - CPU: 0.24%
  - NET I/O: 8.19kb
 

https://www.youtube.com/watch?v=5mpHejytgFE


### Useful Commands
check stats: docker stats
docker build -t [Image_tag_name] .
docker run -i -t [Image_tag_name]
docker run -p 8080:8080 [Image_tag_name]
docker ps -a
docker container stop [container_id]
docker container rm [container_id]

### Installing Prometheus
1. First modify docker daemon config:
touch /etc/docker/daemon.json
{
  "metrics-addr" : "[docker0-ip]:9323",
  "experimental" : true
}
2. Create prometheus config file and save it -> /etc/prometheus/prometheus.yml
# my global config
global:
  scrape_interval:     15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

  # Attach these labels to any time series or alerts when communicating with
  # external systems (federation, remote storage, Alertmanager).
  external_labels:
      monitor: 'codelab-monitor'

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first.rules"
  # - "second.rules"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
      - targets: ['127.0.0.1:9090']

  - job_name: 'docker'
         # metrics_path defaults to '/metrics'
         # scheme defaults to 'http'.

    static_configs:
      - targets: ['docker0-ip:9323']

3. cd /etc/prometheus
3. Run prometheus: docker run -d --name prometheus -p 9090:9090 -v $PWD/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
4. Check if prometheus is running, go to -> http://localhost:9090
5. Run grafana: docker run -d --name grafana --link prometheus -p 3000:3000 grafana/grafana
6. Check if grafana is running, go to -> http://localhost:3000, login with admin admin, after that u can update your password or skip, then add DataSource and choose Prometheus, default config, url: http://prometeus:9090 then Save & Test
7. On The Drawer panel, hover over plus icon, then click import, type 1229 and click Load, select from Prometheus DropDown of Prometheus, then click Import.
8. 