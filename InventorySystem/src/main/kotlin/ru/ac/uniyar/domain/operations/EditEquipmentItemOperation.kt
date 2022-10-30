package ru.ac.uniyar.domain.operations

import ru.ac.uniyar.domain.storage.Equipment
import ru.ac.uniyar.domain.storage.Storage

class EditEquipmentItemOperation(
    private val storage: Storage,
){
    operator fun invoke(equipment: Equipment){
        storage.equipmentRepository.edit(equipment)
    }
}