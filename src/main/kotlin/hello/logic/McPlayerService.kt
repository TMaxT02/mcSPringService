package hello.logic

import hello.reposotorys.McPlayerRepository
import hello.classen.McPlayerEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class McPlayerService(private val mcPlayerRepository: McPlayerRepository) {
    @Transactional(readOnly = true)
    fun getMcPlayerById(uuid: String): McPlayerEntity? {
        return mcPlayerRepository.findById(uuid).orElse(null)
    }

    @Transactional
    fun saveMcPlayer(mcPlayer: McPlayerEntity): McPlayerEntity? {
        return mcPlayerRepository.save(mcPlayer)
    }

    @Transactional
    fun deleteMcPlayer(uuid: String) {
        mcPlayerRepository.deleteById(uuid)
    }
}

