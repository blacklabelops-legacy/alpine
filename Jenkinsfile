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

 /**
  * Build parameters, must be adjusted when forked!
  **/
 env.DockerImageName = 'blacklabelops/alpine'
 def dockerTags = ["latest","3.3"] as String[]
 dockerTestCommands =
   ["echo hello world",
    "ps -All",
    "uname -r",
    "whoami",
    "cat /etc/hosts",
    "cat /etc/passwd"] as String[]

node('docker') {
    checkout scm
    job = load './build/buildImage.groovy'
    job.buildJobCI(dockerTags,dockerTestCommands)
}
