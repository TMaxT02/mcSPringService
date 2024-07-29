package gg.flyte.template.repo

import gg.flyte.template.classes.McPlayerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface McPlayerRepository : JpaRepository<McPlayerEntity, String> {

}
