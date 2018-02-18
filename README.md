# spring-cloud-dataflow
Spring Cloud Data Flow example integrated with Kafka as message broker

# NEEDED COMPONENTS
* We need a deployer. The full list can be found [here](https://github.com/spring-cloud/spring-cloud-deployer/). We'll use a local server.
* We need a shell to create pipeline components and deploy the stream
* We need a source pipeline, in charge of provide a data stream
* Optionally, we need a processor pipeline to transform and process data
* We need a Sink, to persist or output the data.
* We need a running Kafka broker. In the example we deploy Kafka as Docker container

# START DEPLOYER AND SHELL
From local-server project:
* mvn spring-boot:run
From shell project:
* mvn spring-boot:run

# REGISTERING PIPELINE ELEMENTS
from shell (previously deployed):

```bash
dataflow:>app register --name time-source --type source --uri maven://com.sergio.example:spring-dataflow-local-source:1.0-SNAPSHOT
Successfully registered application 'source:time-source'

dataflow:>app register --name time-processor --type processor --uri maven://com.sergio.example:spring-dataflow-local-processor:1.0-SNAPSHOT
Successfully registered application 'processor:time-processor'

dataflow:>app register --name logging-sink --type sink --uri maven://com.sergio.example:spring-dataflow-local-sink:1.0-SNAPSHOT
Successfully registered application 'sink:logging-sink'
```

# CREATING STREAM
From shell:
```bash
dataflow:>stream create --name time-to-log --definition 'time-source | time-processor | logging-sink'
Created new stream 'time-to-log'

dataflow:>stream deploy --name time-to-log
Deployment request has been sent for stream 'time-to-log'
```

# RESOURCES

* https://docs.spring.io/spring-cloud-dataflow/docs/current/reference/htmlsingle/
* http://www.baeldung.com/spring-cloud-data-flow-stream-processing
* http://engineering.pivotal.io/post/spring-cloud-data-flow-sink/
http://start-scs.cfapps.io/
* https://github.com/altfatterz/spring-cloud-dataflow-streaming-example
