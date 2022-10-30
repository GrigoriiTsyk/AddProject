package ru.ac.uniyar.domain.storage

import ru.ac.uniyar.domain.operations.EMPTY_UUID
import java.util.UUID

class EquipmentRepository(equipmentList: List<Equipment>) {
    private val equipment = equipmentList.associateBy { it.id }.toMutableMap()

    fun list(): List<Equipment> = equipment.values.toList()

    fun fetch(id: UUID) = equipment[id]

    fun add(equipmentItem: Equipment) : UUID {
        var newId = equipmentItem.id
        while (equipment.containsKey(newId) || newId == EMPTY_UUID){
            newId = UUID.randomUUID()
        }
        equipment[newId] = equipmentItem.setUuid(newId)
        return newId
    }

    fun edit(equipmentItem: Equipment){
        equipment[equipmentItem.id] = equipmentItem
    }

}
