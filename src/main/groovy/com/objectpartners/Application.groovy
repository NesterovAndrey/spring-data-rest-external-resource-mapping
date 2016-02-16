package com.objectpartners

import com.objectpartners.rest.util.BootstrapData
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext

/**
 * @author sconnelly
 * created 2/12/2016
 *
 */
@SpringBootApplication
class Application {
    static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args)

        // load some initial data
        context.getBean(BootstrapData.class).loadData()
    }
}
