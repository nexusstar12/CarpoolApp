# Application Folder

## Purpose
The purpose of this folder is to store all the source code and related files for your team's application. You are free 
to organize the contents of the prototypes' folder as you see fit. But remember your team is graded on how you use Git. 
This does include the structure of your application. Points will be deducted from poorly structured application folders.

## Please use the rest of the README.md to store important information for your team's application. 
## Local Setup for MacOS

## 1. Docker

Download Docker Desktop https://www.docker.com/products/docker-desktop/ 

Version 4.23.0

Create account for login after downloading 

## 2. IntelliJ Idea Ultimate

Download the IDE https://www.jetbrains.com/idea/download/?section=mac

Click on the .dmg file if your mac is Intel or Apple Silicon

Create account and login once downloaded

## 3. JAVA17

On mac https://download.oracle.com/java/17/archive/jdk-17.0.8_macos-aarch64_bin.dmg 

Inside the DMG file, you will likely see an icon labeled "JDK 17" or similar. Drag this icon to the "Applications" folder to install Java.

On the terminal, then navigate to the downloads folder and then run the following command: 	tar -xzf jdk-17.macosx-x64.tar.gz

Once extracted, you can set up your JAVA_HOME environment variable to point to the installation directory, and add the "bin" directory to your PATH in your shell profile file
In order to check if correctly installed run the command: 	java –version

## 4. Maven
 
## 5. Node & Node npm
   
Open the terminal

In order to have a Node Version Manager, one shall run the command
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.38.0/install.sh

Then close your terminal

Reopen the terminal and then you can install Node.js by running nvm install –lts

You can set the default Node.js version to use if you have multiple versions installed. To do this, run
nvm alias default <version> 

In order to check that node and npm are installed you run
node -v
npm -v

## 6. React

## 7. Google CLI

In order to connect Google CLI follow the instructions on https://cloud.google.com/sdk/docs/install-sdk 

Make sure that one follows the step number 1 in order to have the similar version of Python installed

Then, on instructions number 2, download the corresponding the tar.gz package 

Extract the archive to any location on your file system. On macOS, this can be achieved by opening the downloaded .tar.gz archive file in the preferred location.

**./google-cloud-sdk/install.sh** is the command once in the corresponding location

Once done, open a new terminal so the changes take effect and run command **./google-cloud-sdk/bin/gcloud init**

