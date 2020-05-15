# DS-Playground

DS-Playground is a Thyme leaf application that makes it easy to 
set an input and script value to be executed by Datasonnet. It works
a lot like the Intellij plugin but provides an html interface.

## Getting Started

When getting started with DS-Playground you have two options, you can
either pull down the existing docker image and just boot up a container
or you can clone this repository to build and run manually.

### Prerequisites

* Local  
    * [Maven]("https://maven.apache.org/install.html")  
    * [Datasonnet]("https://github.com/modusbox/datasonnet-mapper")   
* Docker
    * [Docker]("https://docs.docker.com/get-docker/")  

### Installing

#### Local
```bash
git clone https://github.com/JakeMHughes/datasonnet-mapper/tree/dataweave && cd datasonnet-mapper
mvn clean install
git clone https://github.com/JakeMHughes/DS-Playground && cd DS-Playground
mvn spring-boot:run
```

#### Docker
```bash
docker pull ${image}
docker run -d -p 8080:8080 --name=ds-playground ${image}
```

## Built With

* [Datasonnet](https://github.com/modusbox/datasonnet-mapper) - The data transformation
* [Maven](https://maven.apache.org/) - Dependency Management
* [Ace](https://ace.c9.io/) - The code editors

## Contributing

TODO

## Versioning

TODO

## Authors

* **Jacob Hughes** - *Initial work* - [Personal Website](https://hughesportal.com)

## License

This project is licensed under [GPLv3]("https://choosealicense.com/licenses/gpl-3.0/")