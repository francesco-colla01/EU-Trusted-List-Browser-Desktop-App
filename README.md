# EU Trusted List Browser-Desktop-App
Browse the list of all the different trusted services and providers from your desktop
**This program requires internet connectivity to work**.
The app communicates with the European Union APIs to retrieve the data it needs to work properly.
This app is written in Java and needs the Java Runtime Environment to run, refer to tge installation guide and documentations under the code section for more informations.
## Installation and usage
### Base install
1)Check if you have the Java Runtime Environment installed in  your system by typing “Java” in your search bar. If a “Configure Java” app pops up your pc is ready

2)This step is required only if you don’t have Java installed in your system. Download the installer from [here](https://www.java.com/en/) and follow the instructions provided.

3)Clone this repository this will download the program in a compressed folder.

4)In order to be run the program must be estracted from the compressed folder it comes from. All the majours operative systems already handle this kind of files so most of the times you can right click the file and click “extract here”.

5)This step is only required if you can’t open the archive. Download a file manager of your choice for example [WinRAR](https://www.win-rar.com/predownload.html?&L=11) and follow the instructions in step 4

6)Double click the EU Trust Service Dashboard icon to launch the application

### Advanced install
All of our app is open source and browsable. Another way to launch the program is to compile it from scratch even though this method is not reccomended for users that are not interested in the source code. For this kind of launch refer to the same steps as the base install to step 5.

1)Open an IDE of your choice. IntelliJ and Eclipse are two examples

2)Open the extracted folder as a project in your IDE

3)Follow the steps to build and run in your IDE

This steps aren’t very detailed as every development environment is different and may have different steps inorder to run the source code.

### Usage
At launch the app presents four lists with titles telling the user what the elements refer to. To select a search parameter simply click the checkbox at it's left.
Once you're done adding search parameters click the "Search" button to perform the search. At this point the app changes scene presenting a list containing the counties and providers that match the selected criteria, at this point when a service is selected it's details will show at the left of the window. When done consulting click the "go back" button to reset all the filters or "perform new search" to add new filters.
If the selected filters confict with eachother the program will highlight the trouble generating selection prompting the user to uncheck it and preventing a search with zero results.
