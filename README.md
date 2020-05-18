# DS-Playground

DS-Playground is a Thyme leaf application that makes it easy to 
set an input and script value to be executed by Datasonnet. It works
a lot like the Intellij plugin but provides an html interface.

* Features:
  * Autocomplete for jsonnet, DS, and DW libraries
  * Json & Json5 formatting
  * Easily accessible documentation page
  * Fast execution time

![DS](img/DS.png)
## Getting Started

When getting started with DS-Playground you have two options, you can
either pull down the existing docker image and just boot up a container
or you can clone this repository to build and run manually.

### Prerequisites

* Local  
    * [Maven](https://maven.apache.org/install.html)  
    * [Datasonnet](https://github.com/modusbox/datasonnet-mapper)   
* Docker
    * [Docker](https://docs.docker.com/get-docker/)  

### Installing

#### Local
```bash
git clone https://github.com/JakeMHughes/datasonnet-mapper && cd datasonnet-mapper && git checkout dataweave
mvn clean install
git clone https://github.com/JakeMHughes/DS-Playground && cd DS-Playground
mvn spring-boot:run
```

#### Docker
```bash
docker pull jakeizundead/ds-playground:latest
docker run -d -p 8080:8080 --name=ds-playground jakeizundead/ds-playground:latest
```

## Built With

* [Datasonnet](https://github.com/modusbox/datasonnet-mapper) - The data transformation
* [Maven](https://maven.apache.org/) - Dependency Management
* [Ace](https://ace.c9.io/) - The code editors

## Contributing

Anyone is welcome to contribute, but it probably won't be an active project. I recommend creating a fork.

* TODO:
  * Add resizeable window for documentation reference
  * Allow editors to be resizeable
  * Add auto complete for function names
  
## Authors

* **Jacob Hughes** - *Initial work* - [Personal Website](https://hughesportal.com)

## License

This project is licensed under [GPLv3](https://choosealicense.com/licenses/gpl-3.0/)