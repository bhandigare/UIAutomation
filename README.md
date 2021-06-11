# UIAutomation
This is simple Java-TestNg-Maven Project. 

Local Execution

I have created properties file config.properties at src/test/resources. It contains all required data fields like url,username,password,browser,local/remote execution,os(mac/windows)

You can run project as Maven or run directly testNG.xml file present in src/test/resources.

File Upload scenario : Generally sendkeys method is best way to run test case at cloud/local with any OS.But I see this website is having different implementation of file upload. Hence this is not supporting.

Reports :
It will contain extent report for execution result => target/customReporter/index.html
Log file ==> automation.out 

Remote Execution

Just open this project root directory on terminal.
Run below command for docker-compose

docker-compose run test

Execution :
After running above command it will take uiautomation:latest image which contains the test code, hub image and chrome image. It will run all the scenarios on chrome. If you want to see the execution you can download VNC and put url as localhost:9090 or in MAC use go to server option and put same ip address.

Reports :
After execution you will get "Report" folder at your current folder where docker-compose file is present.
It will contain "customReporter" folder with extent report for execution result.