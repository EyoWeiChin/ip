# Duke project template

This is a project template for a greenfield Java project. It's named after the Java mascot _Duke_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 11, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)
2. Set up the correct JDK version, as follows:

   i. Click `Configure` > `Structure for New Projects` and then `Project Settings` > `Project` > `Project SDK`
   
   ii. If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11
   
   iii. Click `OK`
   
3. Import the project into Intellij as follows:

   i. Click `Open or Import`.
   
   ii. Select the project directory, and click `OK`
   
   iii. If there are any further prompts, accept the defaults.
   
4. After the importing is complete, locate the `src/main/java/Duke.java` file, right-click it, and choose `Run Duke.main()`. If the setup is correct, you should see something like the below:

```
Error: Save file not found! New file created!
____________________________________________________________
 ____        _
|  _ \ _   _| | _____
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

Hello! I'm Duke!
What can I do for you?
____________________________________________________________
```
