# spring-cloud-dataflow
Spring Cloud Data Flow example integrated with Kafka as message broker

# NEEDED COMPONENTS
* We need a deployer. The full list can be found [here](https://github.com/spring-cloud/spring-cloud-deployer/). We'll use a local server.
* We need a shell to create pipeline components and deploy the stream
* We need a source pipeline, in charge of provide a data stream
* Optionally, we need a processor pipeline to transform and process data
* We need a Sink, to persist or output the data.
* We need a running Kafka broker. In the example we deploy Kafka as Docker container

# START KAFKA
Start Kafka as a Docker container. You can follow [this respository](https://github.com/sergioverde90/dockerfiles/tree/master/kafka).

> You must map _kafka_ and _zoo_ in your hosts file pointing to _localhost_

# START DEPLOYER AND SHELL
From local-server project:
```bash
mvn spring-boot:run
```

From shell project:
```bash
mvn spring-boot:run
```

# REGISTERING PIPELINE ELEMENTS
From shell (previously deployed):

```bash
dataflow:>app register --name time-source --type source --uri maven://com.sergio.example:spring-dataflow-local-source:1.0-SNAPSHOT
Successfully registered application 'source:time-source'

dataflow:>app register --name time-processor --type processor --uri maven://com.sergio.example:spring-dataflow-local-processor:1.0-SNAPSHOT
Successfully registered application 'processor:time-processor'

dataflow:>app register --name logging-sink --type sink --uri maven://com.sergio.example:spring-dataflow-local-sink:1.0-SNAPSHOT
Successfully registered application 'sink:logging-sink'
```

## OUTPUTS

```bash
2018-02-16 08:24:49.741  INFO 28898 --- [nio-9393-exec-5] .s.c.d.s.s.i.AppDeploymentRequestCreator : Creating resource with [maven://com.sergio.example:spring-dataflow-local-sink:1.0-SNAPSHOT] for application [logging-sink]

2018-02-16 08:24:50.009  INFO 28898 --- [nio-9393-exec-5] .s.c.d.s.s.i.AppDeploymentRequestCreator : Creating resource with [maven://com.sergio.example:spring-dataflow-local-processor:1.0-SNAPSHOT] for application [time-processor]

2018-02-16 08:24:50.150  INFO 28898 --- [nio-9393-exec-5] .s.c.d.s.s.i.AppDeploymentRequestCreator : Creating resource with [maven://com.sergio.example:spring-dataflow-local-source:1.0-SNAPSHOT] for application [time-source]
```

# CREATING STREAM
From shell:
```bash
dataflow:>stream create --name time-to-log --definition 'time-source | time-processor | logging-sink'
Created new stream 'time-to-log'

dataflow:>stream deploy --name time-to-log
Deployment request has been sent for stream 'time-to-log'
```

## OUTPUTS

```bash
2018-02-16 08:24:50.267  INFO 28898 --- [nio-9393-exec-5] o.s.c.d.s.s.AppDeployerStreamDeployer    : Deploying application named [logging-sink] as part of stream named [time-to-log] with resource URI [maven://com.sergio.example:spring-dataflow-local-sink:jar:1.0-SNAPSHOT]

2018-02-16 08:24:50.621  INFO 28898 --- [nio-9393-exec-5] o.s.c.d.spi.local.LocalAppDeployer       : Deploying app with deploymentId time-to-log.logging-sink instance 0.
   Logs will be in /var/folders/gd/js1v70wn1hx9m09hdvc74m9r0000gn/T/spring-cloud-dataflow-7903069627128289748/time-to-log-1518765890269/time-to-log.logging-sink

2018-02-16 08:24:50.623  INFO 28898 --- [nio-9393-exec-5] o.s.c.d.s.s.AppDeployerStreamDeployer    : Deploying application named [time-processor] as part of stream named [time-to-log] with resource URI [maven://com.sergio.example:spring-dataflow-local-processor:jar:1.0-SNAPSHOT]

2018-02-16 08:24:50.630  INFO 28898 --- [nio-9393-exec-5] o.s.c.d.spi.local.LocalAppDeployer       : Deploying app with deploymentId time-to-log.time-processor instance 0.
   Logs will be in /var/folders/gd/js1v70wn1hx9m09hdvc74m9r0000gn/T/spring-cloud-dataflow-7903069627128289748/time-to-log-1518765890624/time-to-log.time-processor

2018-02-16 08:24:50.631  INFO 28898 --- [nio-9393-exec-5] o.s.c.d.s.s.AppDeployerStreamDeployer    : Deploying application named [time-source] as part of stream named [time-to-log] with resource URI [maven://com.sergio.example:spring-dataflow-local-source:jar:1.0-SNAPSHOT]

2018-02-16 08:24:50.638  INFO 28898 --- [nio-9393-exec-5] o.s.c.d.spi.local.LocalAppDeployer       : Deploying app with deploymentId time-to-log.time-source instance 0.
   Logs will be in /var/folders/gd/js1v70wn1hx9m09hdvc74m9r0000gn/T/spring-cloud-dataflow-7903069627128289748/time-to-log-1518765890631/time-to-log.time-source

``` 

# RESOURCES

* https://docs.spring.io/spring-cloud-dataflow/docs/current/reference/htmlsingle/
* http://www.baeldung.com/spring-cloud-data-flow-stream-processing
* http://engineering.pivotal.io/post/spring-cloud-data-flow-sink/
http://start-scs.cfapps.io/
* https://github.com/altfatterz/spring-cloud-dataflow-streaming-example
