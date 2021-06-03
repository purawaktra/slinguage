These are step that you gonna follow to deploy the app in the right environment. The app will be deployed in the containerized environtment that needed to build before it can be deploy either directly on the virtual machines, or by doing a cloud build then upload it to cointainer registry to deploy it via kubernetes.

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

By using postman application we can try the cloud accessibility. There are several API that we can use for the cloud.
    
* To return the hello message from the container, this method can be use to check wheter the container is serving or not. 
    
    ```
    $ http://34.122.140.171/hello
    ```
* To using the models prediction, we need to upload the image first to the cloud then include it on the json file that can be sent to the API from the link below.
    
    ```
    $ http://34.122.140.171/predict
    ```
    
    The json format that contain the url is like this.
    
    ```
    {"url": "[httpsL//....jpg]"}
    ```
    
    you can change the `[httpsL//....jpg]` to your hand sign image and the respons given by the clous is something like this.
    
    ```
    {"result": "[LETTER]"}
    ```
    
    the `[LETTER]` shown the prediction from the machine learning that compute on the cloud.
    

