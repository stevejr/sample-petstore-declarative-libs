package com.example.ci.v1.stages

import com.example.ci.helpers.JenkinsHelper
import com.example.ci.helpers.Slack
import com.example.ci.shared.AbstractStage

/**
 * Created by steve-leftshift on 04/09/2017.
 */
class GitCheckout extends AbstractStage {

    GitCheckout(Object script, JenkinsHelper jenkinsHelper, Slack slack) {
        super(script, 'Git Checkout', jenkinsHelper, slack)
    }

    @Override
    void execute() {
        script.stage(stageName) {
            script.node {
                script.checkout(script.scm)
                // we e.g. have a .kitchen.docker.yml left from the last run. Remove that.
                script.sh("git clean -fdx")
            }
        }
    }
}
