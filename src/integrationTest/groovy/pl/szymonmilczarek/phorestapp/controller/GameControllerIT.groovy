package pl.szymonmilczarek.phorestapp.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import pl.szymonmilczarek.phorestapp.controller.dto.GameResultDTO
import pl.szymonmilczarek.phorestapp.controller.dto.PlayerDTO
import pl.szymonmilczarek.phorestapp.dao.repository.MachineRepository
import pl.szymonmilczarek.phorestapp.dao.repository.PlayerRepository
import pl.szymonmilczarek.phorestapp.util.PhorestAppContainerConfig
import pl.szymonmilczarek.phorestapp.util.PhorestAppSpringBootApplicationTest
import spock.lang.Unroll

@PhorestAppSpringBootApplicationTest
@Sql(scripts = "classpath:/seed/clear_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/seed/phorest_app_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GameControllerIT extends PhorestAppContainerConfig {

    @Autowired
    PlayerRepository playerRepository

    @Autowired
    MachineRepository machineRepository

    def "start game should update player data [POST]"() {
        given:
            def response = httpPost(String.format("api/start?playerId=%s&money=%s", 1, 1000))
        expect:
            response.statusCode == HttpStatus.OK
            def result = mapper.readValue(response.body, PlayerDTO.class)
            result.playerMoney == 2000
    }

    @Unroll("test repeated #i time")
    def "play should update player and machine data and return result [POST]"() {
        given:
            def response = httpPost(String.format("api/play?playerId=%s&machineId=%s", 1, 1))
        expect:
            response.statusCode == HttpStatus.OK
            def result = mapper.readValue(response.body, GameResultDTO.class)
            def updatedPlayer = playerRepository.findById(1L)
            def updatedMachine = machineRepository.findById(1L)
            if (result.win) {
                updatedPlayer.get().playerMoney == 2000
                updatedMachine.get().machineMoney == 0
            } else {
                updatedPlayer.get().playerMoney == 999
                updatedMachine.get().machineMoney == 1001
            }
        where:
            i << (1..100)
    }

    def "withdraw money should update player data [POST]"() {
        given:
            def response = httpPost(String.format("api/withdraw-money?playerId=%s", 1))
        expect:
            response.statusCode == HttpStatus.OK
            def result = mapper.readValue(response.body, Long.class)
            def updatedPlayer = playerRepository.findById(1L).get()
            result == 1000
            updatedPlayer.playerMoney == 0
    }
}
