package com.objectpartners.rest.resource

import com.objectpartners.rest.annotation.RestResourceMapper
import com.objectpartners.rest.util.ResourceParsingUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.LinkBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RestResourceMapperService {
    @Autowired
    EntityLinks entityLinks

    String getResourceURL(final RestResourceMapper restResourceMapper, final Object resourceId) {
        String path = restResourceMapper.path().replaceAll(ResourceParsingUtil.RESOURCE_ID_PLACEHOLDER, resourceId.toString())
        String queryString = ResourceParsingUtil.paramsToQueryString(restResourceMapper.params()).replaceAll(ResourceParsingUtil.RESOURCE_ID_PLACEHOLDER, resourceId.toString())
        String restResourceUrl = "${restResourceMapper.context().value()}${path}"

        if (queryString) {
            restResourceUrl += "?${queryString}"
        }

        restResourceUrl = ResourceParsingUtil.substitutionsForResource(restResourceUrl, resourceId as String, restResourceMapper.apiKey())

        println("Constructed full URL to resource: ${restResourceUrl}")

        return restResourceUrl
    }

    String getHATEOASURLForResource(final String restResourceURL, final Class entityClass) {
        URL resourceURL = new URL(restResourceURL)
        //use HATEOAS LinkBuilder to get the right host and port for constructing the appropriate resource link
        LinkBuilder linkBuilder = entityLinks.linkFor(entityClass)
        URL hateoasURL = new URL(linkBuilder.withSelfRel().href)

        resourceURL = new URL(
                "${hateoasURL.protocol}://${hateoasURL.host}:${hateoasURL.port}${resourceURL.path}" +
                        (resourceURL.query ? "?${resourceURL.query}" : "")
        )

        return resourceURL.toString()
    }

    String getResolvedResource(final String restResourceURL) {
        try {
            RestTemplate restTemplate = new RestTemplate()

            HttpHeaders requestHeaders = new HttpHeaders()
            List<MediaType> acceptHeaders = new ArrayList<MediaType>()
            acceptHeaders.add(new MediaType("application", "json"))

            requestHeaders.setAccept(acceptHeaders)
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders)

            ResponseEntity<String> response = restTemplate.exchange(restResourceURL, HttpMethod.GET, requestEntity, String.class)

            return response.getBody()
        } catch (Exception e) {
            println("Error occurred while fetching resource for URL: ${restResourceURL}, reason: ${e.getMessage()}")
        }
    }
}
