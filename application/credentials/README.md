In this file, write all the credentials needed to access to your application. For example, your app and database credentials.
This information will be used by your CTO and leads to have access some components of your app. 

In addition, you should provide short tutorials to teach people how to access to all the 
components of your application. For instance, if you created a MySQL instance, you must provide 
a short description of how to use the credentials provided here to access to your database instance via ssh or 
using MySQLWorkbench. 

Points will be deducted if teams do not add their credentials here once they have chosen their 
technology stack or if their step-by-step descriptions are not clear enough. You have been warned! 

<br>**Credentials**
<br><img src="credentials table.png">

**Access Instructions**</br>
<br>*SSH Instructions*
<br>1. Download the [.pem](guest_ssh_key.pem) file from this credentials folder.
<br>2. Open the command line on your device. (Use Git Bash if running on a Windows OS device)
<br>3. Run this command: `ssh-keygen -R 35.227.145.220`
<br>4. Then, run this command:
`ssh -i [path to pem file] guest@35.227.145.220`
<br>5. Enter `yes` if prompted. (You may not be prompted)
<br>6. The command was successful if you see something like this after running it:</br>
<br><img src="successful ssh.png"></br>
*Database Instructions*
<br> 1. Open MySQL Workbench
<br> 2. Click the + icon next to MySQL Connections
<br> 3. Provide a Connection Name (i.e. “pool-app”)
<br> 4. Keep `Standard (TCP/IP)` set as the Connection Method
<br> 5. Enter `35.230.60.237` as the Hostname
<br> 6. Enter `3306` for the Port if it is not pre-filled. 
<br> 7. Enter `root` for the username.
<br>Your setup should now look like this:
<br><img src="MySQL connection setup.png">
<br> 7. Click “OK” 
<br> 8. The dialogue window will close and the connection should now display under MySQL Connections
<br> 9. Click on the connection you just created (should display by the Connection Name provided in step 3)
<br> 10. Provide the following password when prompted: `team04csc648`
<br> 11. Click “OK”
<br>If the connection is successful, you should see “pool-db” under Schemas on the left.
<br><img src="successful db connection.png">


