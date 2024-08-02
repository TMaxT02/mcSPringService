package hello.reposotorys

import hello.classen.entity.BuildRealmAllowedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BuildRealmAllowedRepository : JpaRepository<BuildRealmAllowedEntity, Long>
