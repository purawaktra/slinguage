[![<bakarbarakkabarakbar>](https://circleci.com/gh/bakarbarakkabarakbar/slinguage.svg?style=shield)](https://circleci.com/gh/bakarbarakkabarakbar/slinguage)
[![<erikcontena>](https://circleci.com/gh/bakarbarakkabarakbar/slinguage.svg?style=shield)](https://circleci.com/gh/bakarbarakkabarakbar/slinguage)

# slinguage
Slinguage Official Repository

New Android Apps - Slinguage Repos.

Communication is important in aspects of life. Through communication, people can express their ideas, and thoughts to others. However, people with disabilities such as hearing or speaking impairment have difficulty communicating with other people, especially 'normal' people. As for today people with disabilities still face communication issues in the public, the difficulties of people with disabilities are felt because not all normal people understand the language that is used by people with disabilities such as sign language.

Our aim with slinguage is to facilitate communication for persons with disabilities in the public areas.
  
All the code contained for android application setup is presented on this folder, but for especially new folder called the `cloud-vm-setup` is the code that you can clone to the vm and setup.

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
    

