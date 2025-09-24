import edu.ucne.registrojugador.data.local.entities.LogroEntity
import edu.ucne.registrojugador.domain.jugador.model.Logro

fun LogroEntity.toDomain(): Logro {
    return Logro(
        logroId = this.logroId,
        nombre = this.nombre,
        descripcion = this.descripcion
    )
}

fun Logro.toEntity(): LogroEntity {
    return LogroEntity(
        logroId = this.logroId,
        nombre = this.nombre,
        descripcion = this.descripcion
    )
}

fun List<LogroEntity>.toDomainList(): List<Logro> {
    return this.map { it.toDomain() }
}