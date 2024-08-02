package hello.reposotorys

import hello.classen.entity.HomeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HomeRepository : JpaRepository<HomeEntity, String>
