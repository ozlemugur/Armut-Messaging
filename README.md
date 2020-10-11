# Armut-Messaging
Messaging Service 


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


to check services, run th following commands:

    >docker service ls

ID                  NAME                MODE                REPLICAS            IMAGE                                  PORTS
twanzcnwilko        armut_armutapi      replicated          1/1                 ozlemugur/armutmessagingimage:latest   *:8080->8080/tcp
1diul7yhnlwj        armut_pgadmin       replicated          1/1                 dpage/pgadmin4:latest                  *:5433->80/tcp
9xryu69hf59r        armut_postgres      replicated          1/1                 postgres:12.4                          *:5432->5432/tcp




