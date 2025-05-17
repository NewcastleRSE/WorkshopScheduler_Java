# Workshop Scheduler

## GitHub Repository

[https://github.com/NewcastleRSE/WorkshopScheduler_Java](https://github.com/NewcastleRSE/WorkshopScheduler_Java)

## About

When organising a Carpentries workshop a website is created for the workshop using the Carpentries workshop template (https://github.com/carpentries/workshop-template). The workshop template includes a schedule which shows the start and end times of each episode for every day including break times. This program allows a user to capture the start time of a session and the length (in minutes) of all the episodes. The program also allows break and lunch times to be inserted at any point. The program provides for initial keybard input or input from a csv file. Once the user is happy with the schedule it can be exported as an html table which can then be included in the workshop website repository saved as a file called schedule.html.

### Click for [USER MANUAL](UserManual.md)

### Project Team
- Dr Jannetta Steyn, jannetta.steyn@newcastle.ac.uk 
- Ruxandra Neatu

### RSE Contact
Jannetta Steyn 
RSE Team  
Newcastle University  
([jannetta.steyn@newcastle.ac.uk](mailto:jannetta.steyn@newcastle.ac.uk))  

## Built With

- Java 17
- [SparkJava](https://sparkjava.com/)  
- [Apache Log4J](https://logging.apache.org/log4j/2.x/)  
- [Apache Maven](https://maven.apache.org/)  
- [Gson](https://github.com/google/gson)
- [Junit](https://junit.org/junit5/)
- [MigLayout](https://www.miglayout.com/) 

## Getting Started

### Links with tutorials
- [YouTube MigLayout tutorial](https://www.youtube.com/watch?v=Efjl5uSDrPk)
- [Maven Repository](https://mvnrepository.com/artifact/com.miglayout/miglayout-swing/11.0)

### Prerequisites

Java 17

### Installation

`mvn package`

### Running Locally

`java -cp <path-to>jws.jar com.jannetta.workshopscheduler.Main`

### Running Tests

How to run tests on your local system.

## Deployment

### Local

Deploying to a production style setup but on the local system. Examples of this would include `venv`, `anaconda`, `Docker` or `minikube`. 

### Production

Deploying to the production system. Examples of this would include cloud, HPC or virtual machine. 

## License

## Citiation

Please cite the associated papers for this work if you use this code:

```
@article{xxx2021paper,
  title={Title},
  author={Author},
  journal={arXiv},
  year={2021}
}
```


## Acknowledgements
This work was funded by a grant from the UK Research Councils, EPSRC grant ref. EP/L012345/1, “Example project title, please update”.
