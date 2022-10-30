package ru.ac.uniyar.web.handlers

import org.http4k.core.HttpHandler
import ru.ac.uniyar.domain.operations.OperationStorage
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class HandlerStorage(
    operationStorage: OperationStorage,
    htmlView: ContextAwareViewRender,
) {
    val showEmployeesHandler: HttpHandler = ShowEmployeesHandler(
        operationStorage.listEmployeesOperation,
        htmlView,
    )

    val showEmployeeHandler: HttpHandler = ShowEmployeeHandler(
        operationStorage.fetchEmployeeOperation,
        htmlView,
    )

    val showEquipmentHandler: HttpHandler = ShowEquipmentHandler(
        operationStorage.fetchEquipmentOperation,
        htmlView,
    )

    val showEquipmentListHandler: HttpHandler = ShowEquipmentListHandler(
        operationStorage.listEquipmentOperation,
        htmlView,
    )

    val showStartPageHandler: HttpHandler = ShowStartPageHandler(
        htmlView,
    )

    val addEquipmentItemFormHandler: HttpHandler = AddEquipmentItemFormHandler(
        operationStorage.addEquipmentItemOperation,
        htmlView
    )

    val showEquipmentItemFormHandler: HttpHandler = ShowEquipmentItemFormHandler(
        htmlView
    )

    val showEditEquipmentItemFormHandler: HttpHandler = ShowEditEquipmentItemFormHandler(
        htmlView,
        operationStorage.fetchEquipmentOperation
    )

    val changeEquipmentItemHandler: HttpHandler = ChangeEquipmentItemFormHandler(
        operationStorage.editEquipmentItemOperation,
        operationStorage.fetchEquipmentOperation,
        htmlView
    )

}
