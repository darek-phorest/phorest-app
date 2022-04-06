package pl.szymonmilczarek.phorestapp.service

import pl.szymonmilczarek.phorestapp.dao.entity.Machine
import pl.szymonmilczarek.phorestapp.dao.entity.Player
import pl.szymonmilczarek.phorestapp.dao.repository.MachineRepository
import pl.szymonmilczarek.phorestapp.dao.repository.PlayerRepository
import pl.szymonmilczarek.phorestapp.exceptions.EntityDoesNotExistException
import pl.szymonmilczarek.phorestapp.exceptions.NotEnoughMoneyException
import spock.lang.Specification
import spock.lang.Unroll

class GameServiceTest extends Specification {

    def "start game throws EntityDoesNotExistException when player does not exists"() {
        given:
            def playerRepository = Mock(PlayerRepository) {
                1 * findById(_) >> Optional.empty()
            }
            def service = Spy(new GameService(Mock(MachineRepository), playerRepository))
        when:
            service.startGame(1L, 10L)
        then:
            thrown(EntityDoesNotExistException)
    }

    def "play game throws NotEnoughMoneyException"() {
        given:
            def player = new Player()
            player.setPlayerMoney(0L)
            def machine = new Machine()
            machine.setId(1L)
            machine.setMachineMoney(10L)
            def playerRepository = Mock(PlayerRepository) {
                1 * findById(_) >> Optional.of(player)
            }
            def machineRepository = Mock(MachineRepository) {
                1 * findById(_) >> Optional.of(machine)
            }
            def service = Spy(new GameService(machineRepository, playerRepository))
        when:
            service.playGame(1L, 1L)
        then:
            thrown(NotEnoughMoneyException)
    }

    /* ❓ Do we need to repeat the same test here 100 times with the same set of data ? Would it be better to split it into more
    distinct tests testing wins and loses separately ? */
    @Unroll("test repeated #i time")
    def "play game return win or lost result"() {
        given:
            def player = new Player()
            player.setId(1L)
            player.setPlayerMoney(10L)
            def machine = new Machine()
            machine.setId(1L)
            machine.setMachineMoney(10L)

            def playerRepository = Mock(PlayerRepository) {
                1 * findById(_) >> Optional.of(player)
            }
            def machineRepository = Mock(MachineRepository) {
                1 * findById(_) >> Optional.of(machine)
            }
            def service = Spy(new GameService(machineRepository, playerRepository))
        when:
            def result = service.playGame(1L, 1L)
        then:
            if (result.getWin()) {
                assert result.getResult().stream().distinct().count() == 1
            } else {
                assert result.getResult().stream().distinct().count() != 1
            }
        where:
            i << (1..100)
    }

}
