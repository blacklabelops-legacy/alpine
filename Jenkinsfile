/** Jenkins 2.0 Buildfile
 *
 * Master Jenkins 2.0 can be started by typing:
 * docker run -d -p 8090:8080 --name jenkins blacklabelops/jenkins
 *
 * Slave 'packer' can be started by typing:
 * docker run -d -v /dev/vboxdrv:/dev/vboxdrv --link jenkins:jenkins -e "SWARM_CLIENT_LABELS=docker" blacklabelops/hashicorp-virtualbox
 *
 * Slave 'docker' can be started by typing:
 * docker run -d -v /var/run/docker.sock:/var/run/docker.sock --link jenkins:jenkins -e "SWARM_CLIENT_LABELS=docker" blacklabelops/swarm-dockerhost
 **/

node('docker') {
    checkout scm
    load './build/buildImage.groovy'
}
