package ru.ac.uniyar.domain.operations

import ru.ac.uniyar.domain.storage.Equipment
import ru.ac.uniyar.domain.storage.Storage
import java.time.LocalDate
import java.util.*

class AddEquipmentItemOperation(storage: Storage) {
    private val equipmentRepository = storage.equipmentRepository

    operator fun invoke(
        name: String,
        productId: String,
        description: String,
        submissionDate: LocalDate
    ) : UUID {
        return equipmentRepository.add(
            Equipment(
                EMPTY_UUID,
                name,
                productId,
                description,
                submissionDate
            )
        )
    }
}