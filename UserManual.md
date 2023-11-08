# User Manual

To run this program you will need Java Runtime Environment 17 or later available on your computer. 
You will also need to download jws.jar or build it from this repository. 

You can run the program from the command line or, if your file associations are set 
correctly, you should be able to run it from a File Explorer by double clicking on
the jws.jar file.

To run the program from the command line:
- Navigate to the directory where your save jws.jar and type: 
- `java -cp jws.jar com.jannetta.workshopscheduler.Main`
- The first time the program is executed, it will create a sub-directory called `.workshopscheduler` in the user's home directory. A file called `system.properties` will be created in the `.workshopscheduler` directory.

A splash screen should open:

![Splash screen](images/screen01.png)

- Click on the **File** menu item and then on **New Schedule**

![New table](images/screen02.png)

- A few lesson templates are available in the GitHub repository in the workshopschedules directory: ([lesson templates](https://github.com/NewcastleRSE/WorkshopScheduler_Java/tree/main/workshopschedules)). You can download them and use them to kick-start your schedule.
- To open and existing schedule file, click on the **File** menu item and then on **Load CSV**. Navigate to where you saved the template files and click on one of them to open it. See image below:

![New table](images/screen03.png)



