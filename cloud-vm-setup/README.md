These are step that you gonna follow to deploy yhe app in the right environment.

1. First is you want to clone the project by using After that you can direct to go folder
    ```
    $ git clone https://github.com/bakarbarakkabarakbar/slinguage.git
    $ cd cloud-vm-setup
    ```
2. You can install the dependencies first(if you are first time to boot the vm) we are using docker, checkout their [pages](https://docs.docker.com/engine/install/ubuntu/) for futher information

3. Type this to create docker images.
    ```
    $ docker build -t "slinguage:Dockerfile" .
    ```
4. Double check if the images already build by type
    ```
    $ docker images
    ```
5. Then finally we can run in manually on the vm by typing
    ```
    $ docker run -p 8070:9090 slinguage
    ```
    this code will expose the port of the container which open on port 9090 to the port 8070 that will be serving through the vm IP's, this port is used for the application to send the images of the handsign and process it on the vm using the POST method.
    
    
Accessing Method
    
* To return the hello message from the container, this method can be use to check wheter the container is serving or not.
    
    ```
    $ curl http:/[IP-ADDRESS]:8070/hello
    ```
* Using `POST` to sent the images to the container to predict the value, then sent them back as response
    
    ```
    $ curl -X POST -F 'image=@[IMAGE-LOCATION]' http:/[IP-ADDRESS]:8070/predict
    ```
    
    you can change the `[IMAGE-LOCATION]` to your hand sign image file for example `image=@/home/user/Pictures/wallpaper.jpg` and also don't forget to attach `@` to the beginning of the file directory.
    

