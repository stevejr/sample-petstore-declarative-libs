/**
 * Created by steve-leftshift on 08/10/2017.
 */
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def call(Map config) {
    def vfiJSON = readJSON file: config.vfiFile
    echo "vfi Json before update: ${vfiJSON}"

    vfiJSON[config.component]['address'] = config.address
    echo "vfi Json after update: ${vfiJSON}"

    // write back to file
    def vfiJSONPretty = new JsonBuilder(vfiJSON).toPrettyString()
    sh "echo ${vfiJSON} > ${config.vfiFile}"

    // push back
    def branchName =  sh (returnStdout: true, script: "git symbolic-ref --short HEAD").trim()
    def message = "Updated ${config.component} address to value ${config.address}"

    git add ${config.vfiFile}"
    sh "git commit -m \"${message}\""
    sh "git push origin ${branchName}"
}
