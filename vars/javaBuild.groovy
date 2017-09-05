def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config

    body()

    com.example.ci.v1.Pipeline.builder(this, steps)
            .buildDefaultPipeline()
            .execute()

}