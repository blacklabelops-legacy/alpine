/**
 * Jenkins 2.0 Buildfile
 **/
 
def pushImages() {

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

def dockerPush(imageName, tagName) {
    sh 'docker push ' + imageName + ':' + tagName
}
