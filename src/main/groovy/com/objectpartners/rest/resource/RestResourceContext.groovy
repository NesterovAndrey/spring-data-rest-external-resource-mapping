package com.objectpartners.rest.resource

enum RestResourceContext {
    LOCAL("http://localhost:8080"),
    PROFILE("http://localhost:8080/profiles"),
    LOCATOR("https://maps.googleapis.com/maps/api")

    final String context

    public RestResourceContext(String context) {
        this.context = context
    }

    public value() {
        return this.context
    }
}
