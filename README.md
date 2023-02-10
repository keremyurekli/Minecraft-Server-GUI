# Minecraft Server Gui
> **WARNING**: Requires java 17 or higher!

Do you want to manage your minecraft server easily? You are in the right place, Minecraft Server Gui allows you to do this.

## **Features:**                
  >Customizable ram amount, port, javapath and more                 
  >Portforwarding option (via [ngrok](https://ngrok.com/), [java-ngrok](https://github.com/alexdlaird/java-ngrok))                   
  >View server information
  >Built-in text editor for server settings
  
## **Usage:**
When you start the application you will see a file chooser. Locate and choose jar file of your server.

  ![image](https://user-images.githubusercontent.com/85027678/210397651-0f9af1b3-961e-496a-9ae3-7c9e4886bfda.png)

If you chose your jar file you will see a simple and easy-to-use gui.

![image](https://user-images.githubusercontent.com/85027678/218209075-d4c1dfd0-48f1-4f50-92fb-7245f1d25023.png)

There are three parts in the gui: one for server options, one for command line and one for viewing server informations

![image](https://user-images.githubusercontent.com/85027678/218209081-9301fa0c-4c85-4f7c-94ad-1d3cf8223bf1.png)

You can customize server settings from the left.

And there is a simple built in text editor for server.properties file.

![image](https://user-images.githubusercontent.com/85027678/218209426-0790d4df-7535-4cac-b24b-01f5ab4e19ba.png)


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
  > ~~An option to remember servers and their settings~~
  
  > ~~View server information without portforwarding~~

  > A menu to download server jars
  
  > Support for more portforwarding services
  
 ## Thanks to:                  
 >[@alexdlaird](https://github.com/alexdlaird) for [java-ngrok](https://github.com/alexdlaird/java-ngrok)  
 
 >[ngrok](https://ngrok.com/)

 >[@FXMisc](https://github.com/FXMisc) for [RichTextFX](https://github.com/FXMisc/RichTextFX)
