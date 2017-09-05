package com.example.ci.v1

import com.example.ci.helpers.JenkinsHelper
import com.example.ci.helpers.Slack
import com.example.ci.shared.Stage
import com.example.ci.v1.stages.GitCheckout
import org.jenkinsci.plugins.workflow.cps.DSL

/**
 * Created by steve-leftshift on 04/09/2017.
 */
class Pipeline implements Serializable {

    def script

    def stages = []

    DSL steps

    Slack slack

    JenkinsHelper jenkinsHelper

    static builder(script, DSL steps) {
        return new Builder(script, steps)
    }

    static class Builder implements Serializable {

        def stages = []

        def script

        DSL steps

        Slack slack

        JenkinsHelper jenkinsHelper

        Builder(def script, DSL steps) {
            this.script = script
            this.steps = steps
            this.slack = new Slack(script)
            this.jenkinsHelper = new JenkinsHelper(script)
        }

        def withGitCheckoutStage() {
            stages << new GitCheckout(script, jenkinsHelper, slack)
            return this
        }

//        def withLintStage() {
//            stages << new Lint(script, jenkinsHelper, slack)
//            return this
//        }
//
//        def withBuildStage() {
//            stages << new Build(script, jenkinsHelper, slack)
//            return this
//        }
//
//        def withAcceptanceStage() {
//            stages << new Acceptance(script, jenkinsHelper, slack)
//            return this
//        }
//
//        def withPublishStage() {
//            stages << new Publish(script, jenkinsHelper, slack)
//            return this
//        }

        def build() {
            return new Pipeline(this)
        }

        def buildDefaultPipeline() {
            withGitCheckoutStage()
//            withLintStage()
//            withBuildStage()
//            withAcceptanceStage()

            if (script.env.BRANCH_NAME == 'master') {
                withPublishStage()
            }

            return new Pipeline(this)
        }

    }

    private Pipeline(Builder builder) {
        this.script = builder.script
        this.stages = builder.stages
        this.steps = builder.steps
        this.slack = builder.slack
        this.jenkinsHelper = builder.jenkinsHelper
    }

    void execute() {
        slack.buildStart()

        // `stages.each { ... }` does not work, see https://issues.jenkins-ci.org/browse/JENKINS-26481
        for (Stage stage : stages) {

            try {
                stage.execute()
            } catch (err) {
                script.currentBuild.result = "FAILURE"
                slack.buildFinish()
                script.error "Build failed: ${err.getMessage()}"
            }
        }

        slack.buildFinish()
    }
}
