/**
 * Created by steve-leftshift on 08/10/2017.
 */
import groovy.json.JsonSlurper

def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    body()

    def vfiJSON = readJSON file: config.vfiFile
    echo "vfi Json: ${vfiJSON}"
}
