package hello.reposotorys

import hello.classen.McPlayerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface McPlayerRepository : JpaRepository<McPlayerEntity, String> {
}
