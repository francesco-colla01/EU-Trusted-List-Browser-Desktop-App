# EU Trusted List Browser-Desktop-App
Browse the list of all the different trusted services and providers from your desktop
**This program requires internet connectivity to work**.
The app communicates with the European Union APIs to retrieve the data it needs to work properly.
This app is written in Java and needs the Java Runtime Environment to run, refer to the installation guide and documentations under the code section for more informations.
## Installation and usage
### Base install
1) Check if you have the Java Runtime Environment is installed in your system.In Windows by typing “Java” in your search bar. If a “Configure Java” app pops up your pc is ready, in linux typing the command java -version

2) This step is required only if you don’t have Java installed in your system. Download the installer from [here](https://www.java.com/en/) and follow the instructions provided.

3) Install the latest version of the JDK needed to run the jar file at [this link](https://www.oracle.com/java/technologies/downloads/) choosing your os.

4) Download the .jar application file from [here](https://github.com/thelion154/EU-Trusted-List-Browser-Desktop-App/raw/main/EU%20Trust%20Service%20Dashboard.jar).

5) Double click the EU Trust Service Dashboard icon to launch the application.

If you're still experiencing toubles please refer to this short [video tutorial](https://www.youtube.com/watch?v=dQw4w9WgXcQ&feature=emb_logo) covering the major operative systems.

### Advanced install
All of our app is open source and browsable. Another way to launch the program is to compile it from scratch even though this method is not reccomended for users that are not interested in the source code. For this kind of launch refer to the same steps as the base install to step 5.

1) Open an IDE of your choice. IntelliJ and Eclipse are two examples

2) Open the extracted folder as a project in your IDE

3) Follow the steps to build and run in your IDE

This steps aren’t very detailed as every development environment is different and may have different steps inorder to run the source code.

### Usage
At launch the app presents four lists with titles telling the user what the elements refer to. To select a search parameter simply click the checkbox at it's left.
Once you're done adding search parameters click the "Search" button to perform the search. If the selected filters confict with eachother the program will highlight the trouble generating selection prompting the user to uncheck it and preventing a search with zero results.
![alt-text](https://i.imgur.com/4txP8Jj.png)


At this point the app changes scene presenting a list containing the counties and providers that match the selected criteria, at this point when a service is selected it's details will show at the left of the window. When done consulting click the "go back" button to continue searching with your current parameters or "perform another search" to fetch new data from the APIs and reset all the filters.
If the selected filters confict with eachother the program will highlight the trouble generating selection prompting the user to uncheck it and preventing a search with zero results.
![alt-text](https://i.imgur.com/dZFvkV8.png)

When a service is double clicked its details will appear on the right side of this window like shown here
![alt-text](https://i.imgur.com/LHyl1f1.png)
