package pl.szymonmilczarek.phorestapp.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import javax.servlet.ServletContext

abstract class PhorestAppRestConfig extends Specification {

    private static final String APP_URL_TEMPLATE = "http://localhost:%s/%s"
    protected static final String POSTGRES_URL_BASE_TEMPLATE = "jdbc:postgresql://%s:%s/phorest-app"

    @LocalServerPort
    protected int localServerPort

    @Autowired
    protected ServletContext servletContext

    @Autowired
    protected TestRestTemplate client

    protected String apiUrl() {
        return String.format(APP_URL_TEMPLATE, localServerPort, servletContext.getContextPath())
    }

//    protected ResponseEntity<String> httpGet(String endpoint) {
//        return invokeGet(endpoint, null)
//    }

    protected ResponseEntity<String> httpPost(String endpoint) {
        return invokePost(endpoint, null)
    }

    protected ResponseEntity<String> httpPost(String endpoint, Object body) {
        return invokePost(endpoint, body)
    }

    private ResponseEntity<String> invokeGet(String endpoint, HttpHeaders headers) {
        return invoke(endpoint, HttpMethod.GET, null, headers)
    }

    private ResponseEntity<String> invokePost(String endpoint, Object body) {
        def headers = new HttpHeaders()
        return invoke(endpoint, HttpMethod.POST, body, headers)
    }

    private ResponseEntity<String> invoke(String endpoint, HttpMethod method, Object body, HttpHeaders headers) {
        def url = apiUrl() + endpoint
        def entity = new HttpEntity<>(body, headers)
        def response = client.exchange(
                url,
                method,
                entity,
                String.class)
        return response
    }

    def mapper

    void setup() {
        mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
    }
}
