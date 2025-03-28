package com.atm.buenas_practicas_java.dtos

import java.io.Serializable

/**
 * DTO for {@link com.atm.buenas_practicas_java.entities.EntidadPadre}
 */
data class EntidadPadreDto(
    val id: Long? = null,
    val nombre: String? = null,
    val entidadesHijas: MutableList<EntidadHijaDto>? = mutableListOf()
) : Serializable {
    /**
     * DTO for {@link com.atm.buenas_practicas_java.entities.EntidadHija}
     */
    data class EntidadHijaDto(val id: Long? = null, val nombre: String? = null) : Serializable
}