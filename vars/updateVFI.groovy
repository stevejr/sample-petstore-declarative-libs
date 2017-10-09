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
    writeFile file: config.vfiFile, text: vfiJSONPretty
}