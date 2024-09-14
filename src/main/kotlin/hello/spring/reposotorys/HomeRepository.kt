package hello.spring.reposotorys

import hello.spring.classen.entity.BuildRealmAllowedEntity
import hello.spring.classen.entity.HomeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HomeRepository : JpaRepository<HomeEntity, Long>
