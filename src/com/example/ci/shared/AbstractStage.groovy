package com.example.ci.shared

import com.example.ci.helpers.JenkinsHelper
import com.example.ci.helpers.Slack

/**
 * Created by steve-leftshift on 04/09/2017.
 */
abstract class AbstractStage implements Stage {

    def stageName
    def script
    JenkinsHelper jenkinsHelper
    Slack slack

    AbstractStage(script, String stageName, JenkinsHelper jenkinsHelper, Slack slack) {
        this.script = script
        this.stageName = stageName
        this.jenkinsHelper = jenkinsHelper
        this.slack = slack
    }

    abstract void execute()
}
