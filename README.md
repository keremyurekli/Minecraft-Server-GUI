# Minecraft Server Gui

Do you want to manage your minecraft server easily? You are in the right place, Minecraft Server Gui allows you to do this.

## **Features:**                
  >Customizable ram amount, port, javapath and more                 
  >Portforwarding option (via [ngrok](https://ngrok.com/), [java-ngrok](https://github.com/alexdlaird/java-ngrok))                   
  >View server information (only works while portforwarding is enabled)
  
## **Usage:**
When you start the application you will see a file chooser. Locate and choose jar file of your server.

  ![image](https://user-images.githubusercontent.com/85027678/210397651-0f9af1b3-961e-496a-9ae3-7c9e4886bfda.png)

If you chose your jar file you will see a simple and easy-to-use gui.

![image](https://user-images.githubusercontent.com/85027678/210393846-361f14b8-e8d0-49c3-9e47-9ed25a66fe42.png)

There are three parts in the gui: one for server options, one for command line and one for viewing server informations

![Untitled-1](https://user-images.githubusercontent.com/85027678/210394831-fbf17e71-13c6-4fd6-98ca-bc34db052a25.png)

You can customize server settings from the left

> **WARNING**: You can only use command line while server is open!

> **WARNING**: For now you can only see server information if portforwarding enabled!

## **Portforwarding:**
> At the moment there is only support for ngrok!

If you want to use portforwarding for the first time, follow these steps
1) Click on the "NGROK SETUP" button on the startup window

2) Wait few seconds and then restart the app

3) Reopen and select your server jar file, then go to the [ngrok-page](https://dashboard.ngrok.com/get-started/your-authtoken) and get your authtoken

4) Go back to the app and paste it to the "Ngrok Auth Token" part in the gui

5) When you start portforwarding, it will give you public ip address to join the server

6) Done, Portforwarding is ready!

> **WARNING**: You need to start the server to use portforwarding!
                      
## **TODO:**                            
  > An option to remember servers and their settings
  
  > View server information without portforwarding
  
  > Support for more portforwarding services
  
 ## Thanks to:                  
 >[alexdlaird](https://github.com/alexdlaird) for [java-ngrok](https://github.com/alexdlaird/java-ngrok)  
 
 >[ngrok](https://ngrok.com/)
