package com.objectpartners.rest.resource

import com.objectpartners.rest.annotation.EntityWithRestResource
import com.objectpartners.rest.annotation.RestResourceMapper
import com.objectpartners.rest.util.ResourceParsingUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceProcessor

import java.lang.reflect.Field

@Configuration
class RestResourceMapperConfig {
    @Autowired
    RestResourceMapperService restResourceMapperService

    @Bean
    public ResourceProcessor<Resource<?>> resourceProcessor() {
        return new ResourceProcessor<Resource<?>>() {
            @Override
            public Resource<?> process(Resource<?> resource) {
                // additional processing only for entity's with have rest resources
                if (resource.getContent().class.isAnnotationPresent(EntityWithRestResource.class)) {
                    Map<String, String> links = [:]

                    // process any fields that have the RestResourceMapper annotation
                    resource.getContent().class.getDeclaredFields().each { Field field ->
                        RestResourceMapper restResourceMapper = field.getAnnotation(RestResourceMapper.class)

                        if (restResourceMapper) {
                            String resourceId = resource.content."${field.getName()}"

                            if (resourceId) {
                                // construct a REST endpoint URL from the annotation properties and resource id
                                final String resourceURL = restResourceMapperService.getResourceURL(restResourceMapper, resourceId)

                                // for eager fetching, fetch the resource and embed its contents within the designated property
                                // no links are added
                                if (restResourceMapper.resolveToProperty()) {
                                    String resolvedResource = restResourceMapperService.getResolvedResource(resourceURL)

                                    resource.content."${restResourceMapper.resolveToProperty()}" =
                                            ResourceParsingUtil.deserializeJSON(resolvedResource)
                                } else {
                                    // for external links, we simply want to put the constructed URL into the JSON output
                                    // for internal links, we want to ensure that the URL conforms to HATEOAS for the given resource
                                    links.put(field.name, restResourceMapper.external() ?
                                            resourceURL : restResourceMapperService.getHATEOASURLForResource(resourceURL, resource.content.class))
                                }
                            }
                        }
                    }

                    // add any additional links to the output
                    links.keySet()?.each { String linkResourceName ->
                        resource.add(new Link(links.get(linkResourceName), linkResourceName))
                    }
                }

                return resource
            }
        }
    }
}
