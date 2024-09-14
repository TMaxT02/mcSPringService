package hello.spring.reposotorys

import hello.spring.classen.entity.McPlayerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface McPlayerRepository : JpaRepository<McPlayerEntity, String> {
}
