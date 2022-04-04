package pl.szymonmilczarek.phorestapp.utils

import spock.lang.Specification
import spock.lang.Unroll

class ResultGeneratorTest extends Specification {

    def expectedResult = List.of("black", "white", "green", "yellow")

    @Unroll("generates random result - #i")
    def "generates random result"() {
        given:
            def generator = new ResultGenerator()
        when:
            def result = generator.getResult()
        then:
            result.each {r ->
                expectedResult.contains(r)
            }
        where:
            i << (1..100)
    }
}