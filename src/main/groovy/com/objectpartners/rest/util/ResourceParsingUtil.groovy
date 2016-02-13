package com.objectpartners.rest.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class ResourceParsingUtil {
    public static final String RESOURCE_ID_PLACEHOLDER = "#id"
    public static final String API_KEY_PLACEHOLDER = "#apiKey"

    static String paramsToQueryString(final String[] params) {
        if (!params) {
            return ""
        }

        StringBuilder sb = new StringBuilder()

        params.eachWithIndex { param, index ->
            sb.append(param)
            if (index < params.length - 1) {
                sb.append("&")
            }
        }

        return sb.toString()
    }

    static String substitutionsForResource(String str, String resourceId, String apiKey)  {
        return str?.replaceAll(RESOURCE_ID_PLACEHOLDER, resourceId)?.replaceAll(API_KEY_PLACEHOLDER, apiKey)
    }

    static Object deserializeJSON(String json) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {})
    }
}
