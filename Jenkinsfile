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
def dockerTestCommands =
  ["echo hello world",
   "ps -All",
   "uname -r",
   "whoami",
   "cat /etc/hosts",
   "cat /etc/passwd"] as String[]
node('docker') {
  checkout scm
  def branchName = getDockerBranchName()

  stage 'Build Image'
  echo 'Building the base image'
  buildImage(env.DockerImageName,'latest',branchName)

  stage 'Test Image'
  echo 'Testing the base image'
  for (int i=0;i < dockerTestCommands.length;i++) {
    sh 'docker run --rm $DockerImageName ' + dockerTestCommands[i]
  }
}

def buildImage(imageName, tagName, branchName) {
  def branchSuffix = branchName != null && !branchName.isEmpty() ? '' : '-' + branchName
  def image = imageName + ':' + tagName + branchSuffix
  echo 'Building: ' + image
  sh 'docker build --no-cache -t ' + image
}

def getDockerBranchName() {
  def branchName = env.JOB_NAME.replaceFirst('.+/', '')
  echo 'Building on Branch: ' + branchName
  def tagPostfix = ''
  if (branchName != null && !'master'.equals(branchName)) {
    tagPostfix = branchName
  }
  return tagPostfix
}

/**
 * Docker needs three parameters to work, I distributed those Credentials inside
 * two Jenkins-UsernamePassword Credentials.
 * Credentials 'Dockerhub' with Dockerhub username and password
 * Credentials 'DockerhubEmail' with the email inside the password field.
 **/
def dockerHubLogin() {
  echo 'Login to Dockerhub with Credentials Dockerhub and DockerhubEmail'
  withCredentials([[$class: 'UsernamePasswordMultiBinding',
    credentialsId: 'Dockerhub',
    usernameVariable: 'USERNAME',
    passwordVariable: 'PASSWORD']]) {
    withCredentials([[$class: 'UsernamePasswordMultiBinding',
      credentialsId: 'DockerhubEmail',
      usernameVariable: 'DUMMY',
      passwordVariable: 'EMAIL']]) {
      sh 'docker login --email $EMAIL --username $USERNAME --password $PASSWORD'
    }
  }
}

def dockerPush(imageName, uptagName) {
    sh 'docker push ' + imageName + ':' + uptagName + tagName
}
