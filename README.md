# Armut-Messaging
Messaging Service 


 ##### to deploy swarm
 
 First init swarm using your host public ip as advertise-addr:

    > docker swarm init --advertise-addr 192.168.0.29

Swarm initialized: current node (vruq344ji56ewb880hcjrs0rm) is now a manager.


To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-0z6tjl6yc3who00897bdwweyvbzptvmsah99myxzsqm5gnddwv-dhh8c5w4mw1q9mpp91rmzez33 192.168.0.29:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.



To deploy armut messaging api , run the following commands:

    > docker stack deploy -c armut-stack.yml armut

    Creating network armut_armut
    Creating service armut_postgres
    Creating service armut_armutapi
    Creating service armut_pgadmin


to check services, run the following commands:

    >docker service ls

    ID                  NAME                MODE                REPLICAS            IMAGE                                  PORTS
    twanzcnwilko        armut_armutapi      replicated          1/1                 ozlemugur/armutmessagingimage:latest   *:8080->8080/tcp
    1diul7yhnlwj        armut_pgadmin       replicated          1/1                 dpage/pgadmin4:latest                  *:5433->80/tcp
    9xryu69hf59r        armut_postgres      replicated          1/1                 postgres:12.4                          *:5432->5432/tcp


to scale up armut_armutapi, run the following command:

    >docker service scale armut_armutapi=3


    armut_armutapi scaled to 3
    overall progress: 3 out of 3 tasks 
    1/3: running   [==================================================>] 
    2/3: running   [==================================================>] 
    3/3: running   [==================================================>] 
    verify: Service converged 

 ##### to deploy with docker-compose
 
 to deploy with docker-compose , you can find the docker-compose.yml file  in th project, run the following command:
 
    >docker-compose up -d
 
 
  ##### armut_armutapi endpoints
  
  ###### to signup  armut messaging api 
    change the localhost with your advertise IP or set up according to where you prefer to call.
   >http://localhost:8080/users/signup
   
   >{ "userName": "luffy" , "password" : "lola"}
   >{ "userName": "robin" , "password" : "lola"}
   >{ "userName": "zoro" , "password" : "lola"}
   
   ###### to login armut messaging api
   When the login operation ends succesfully, service returns a token which is going to be used for calling the other operations. Take the token.
   
   >http://localhost:8080/users/login
   
   >{ "userName": "luffy" , "password" : "lola"}
   >{ "userName": "zoro" , "password" : "lola"}
 
 
  ###### to send message 
  
 
 http://localhost:8080/messages/sendmessage
 
    { "userName": "luffy" , "token" : "FCEEB9B9D469401FE558062C4BD25954" ,  "receiverUserName" : "robin",
      "message": {
      "content" : "text is comminggggg"
     }
    }
 
   ##### to get messages
   
    http://localhost:8080/messages/getmessages
   
      { "userName": "luffy" , "token" : "FCEEB9B9D469401FE558062C4BD25954}
    
   ##### to block user
   
    http://localhost:8080/block
    
    { "userName": "luffy" , "token" : "FCEEB9B9D469401FE558062C4BD25954", "toBeBlockedUserName" : "zoro"}
    
    
