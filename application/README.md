# Application Folder

## Purpose
The purpose of this folder is to store all the source code and related files for your team's application. You are free 
to organize the contents of the prototypes' folder as you see fit. But remember your team is graded on how you use Git. 
This does include the structure of your application. Points will be deducted from poorly structured application folders.

## Please use the rest of the README.md to store important information for your team's application. 
### Local Setup Instructions for MacOS

#### 1. Docker

Download Docker Desktop https://www.docker.com/products/docker-desktop/ 

Version 4.23.0

Create account for login after downloading 

#### 2. IntelliJ Idea Ultimate

Download the IDE https://www.jetbrains.com/idea/download/?section=mac

Click on the .dmg file if your mac is Intel or Apple Silicon

Create account and login once downloaded

#### 3. JAVA17

On mac https://download.oracle.com/java/17/archive/jdk-17.0.8_macos-aarch64_bin.dmg 

Inside the DMG file, you will likely see an icon labeled "JDK 17" or similar. Drag this icon to the "Applications" folder to install Java.

On the terminal, then navigate to the downloads folder and then run the following command: 	tar -xzf jdk-17.macosx-x64.tar.gz

Once extracted, you can set up your JAVA_HOME environment variable to point to the installation directory, and add the "bin" directory to your PATH in your shell profile file
In order to check if correctly installed run the command: 	java –version

#### 4. Maven
 
#### 5. Node & Node npm
   
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

#### 6. React

#### 7. Google CLI

In order to connect Google CLI follow the instructions on https://cloud.google.com/sdk/docs/install-sdk 

Make sure that one follows the step number 1 in order to have the similar version of Python installed

Then, on instructions number 2, download the corresponding the tar.gz package 

Extract the archive to any location on your file system. On macOS, this can be achieved by opening the downloaded .tar.gz archive file in the preferred location.

**./google-cloud-sdk/install.sh** is the command once in the corresponding location

Once done, open a new terminal so the changes take effect and run command **./google-cloud-sdk/bin/gcloud init**

### Data Breach Plan

This document outlines our application's response procedures in the event of a data breach. Our commitment is to maintain the highest standards of data security and to act swiftly and transparently should a breach occur.

1. Notification Procedures in Case of a Data Breach
   Upon discovering a data breach, we will:
* Conduct an Immediate Investigation: Identify the nature and scope of the breach, the type of data involved, and the potential impact on users.
* Notify Affected Users: Users will be notified via email and in-app notifications. This notification will include:
    * A description of what occurred.
    * The type of data that was compromised.
    * Actions taken to secure the data and prevent further unauthorized access.
    * Steps users can take to protect themselves.
    * Contact information for users to ask questions or obtain additional assistance.

2. Timeliness of Notification
* Timeline: Users will be notified within 72 hours of confirming the data breach. This complies with the General Data Protection Regulation (GDPR) and other relevant privacy laws.

3. User Support Post-Breach
* Guidance: Instructions on how to change account passwords and secure other personal information will be provided.
* Support Channels: A dedicated email address will be available for inquiries related to the breach.
* Credit Monitoring Services: If financial data is compromised, we will offer free credit monitoring services to the affected users for one year.

4. Data Recovery and Safeguarding Measures
* Data Recovery: Implement our pre-established data recovery plan, including restoring data from backups.
* Security Enhancement: Post-breach, we will strengthen our security measures, including implementing two-factor authentication and additional monitoring systems.

5. Preventive Measures and Data Protection
* Regular Security Audits: Periodic audits to identify vulnerabilities.
* Encryption: All sensitive data is encrypted to protect against unauthorized access.
* Employee Training: Ongoing training for staff on best practices in data security.
* Software Updates: Regular updates of security software and prompt application of security patches.

6. Amendment and Review
* Update Policy: This plan will be reviewed and updated annually, or in response to significant changes in technology, data protection laws, or our business operations.

7. Document Control
* Version History: Each update of this document will be recorded with version numbers.
* Responsible Team: Our cybersecurity team will be responsible for maintaining this document.

No Liability Clause
Limitation of Liability: In the event of any data breach or unauthorized access to user data, Pool shall take immediate steps to identify the cause, rectify the breach, and notify affected users. While Pool commits to maintaining the highest standards of data protection and will make every effort to safeguard user data, Pool shall not be liable for any indirect, incidental, special, consequential or punitive damages, including but not limited to loss of profits, data, use, goodwill, or other intangible losses, resulting from:
1. Your access to or use of or inability to access or use the services;
2. Any conduct or content of any third party on the services;
3. Unauthorized access, use, or alteration of your transmissions or content, whether based on warranty, contract, tort (including negligence) or any other legal theory, whether or not we have been informed of the possibility of such damage, and even if a remedy set forth herein is found to have failed of its essential purpose.
   Disclaimer: Pool does not guarantee that the services it provides will be secure or free from bugs or viruses, nor does Pool guarantee the continuous, uninterrupted or error-free operability of the services. While Pool takes all reasonable measures to safeguard the personal data of its users, it cannot guarantee the security of user data transmitted to our services; any transmission is at the user's own risk.
