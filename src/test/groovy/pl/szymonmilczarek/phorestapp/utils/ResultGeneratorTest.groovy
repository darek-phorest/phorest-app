package pl.szymonmilczarek.phorestapp.utils

import spock.lang.Specification
import spock.lang.Unroll

class ResultGeneratorTest extends Specification {

    def expectedResult = List.of("black", "white", "green", "yellow")


    /* ❓ What is the value of this test?, does it really test the randomness or that it generates results with black, white, green, yellow
    options, what will happen if we return from the generator always ['black', 'black', 'black', 'black'], will the test pass anyways ? */
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