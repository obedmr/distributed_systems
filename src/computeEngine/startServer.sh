#!/bin/bash

java -cp /home/vagrant/distributed_systems/src/RMI/computeEngine:/home/vagrant/distributed_systems/src/RMI/computeEngine/compute.jar -Djava.rmi.server.codebase=file:/home/vagrant/distributed_systems/src/RMI/computeEngine/compute.jar -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy engine.ComputeEngine
