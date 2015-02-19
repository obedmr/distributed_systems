#!/bin/bash

java -cp /home/vagrant/distributed_systems/src/RMI/computeEngine:/home/vagrant/distributed_systems/src/RMI/computeEngine/compute.jar -Djava.rmi.server.codebase=file:/home/vagrant/distributed_systems/src/RMI/computeEngine -Djava.security.policy=client.policy client.ComputePi 10.43.6.91 2
