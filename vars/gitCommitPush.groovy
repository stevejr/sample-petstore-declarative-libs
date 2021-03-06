/**
 * Created by steve-leftshift on 09/10/2017.
 */

def call(Map config) {

    // push back
    def branchName = sh(returnStdout: true, script: "git symbolic-ref --short HEAD").trim()
    def message = config.message

    sh "git config user.email \"${config.email}\""
    sh "git config --global user.name \"${config.username}\""

    sh "git add ${config.file}"
    sh "git commit -m \"${config.message}\""

    sh "git tag -a ${config.tag} -m \"${config.message}\""
    withCredentials([usernamePassword(credentialsId: config.credentials, passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
        sh("git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${config.repo} --follow-tags")
    }
}