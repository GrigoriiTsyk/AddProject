package ru.ac.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.Status.Companion.FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.*
import ru.ac.uniyar.domain.operations.EditEquipmentItemOperation
import ru.ac.uniyar.domain.operations.FetchEquipmentOperation
import ru.ac.uniyar.domain.storage.Equipment
import ru.ac.uniyar.web.models.EquipmentFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender


class ShowEditEquipmentItemFormHandler(
    private val htmlView: ContextAwareViewRender,
    private val fetchEquipmentOperation: FetchEquipmentOperation,
) : HttpHandler {
    companion object{
        private val idLens = Path.uuid().of("id")
    }

    override fun invoke(request: Request): Response {
        val id = lensOrNull(idLens, request) ?: return Response(Status.BAD_REQUEST)
        val equipmentItemToEdit = fetchEquipmentOperation.fetch(id)
        return Response(OK).with(htmlView(request) of EquipmentFormVM(
            equipmentItemToEdit!!.name,
            equipmentItemToEdit.productId,
            equipmentItemToEdit.description,
            equipmentItemToEdit.submissionDate
        )
        )
    }
}

class ChangeEquipmentItemFormHandler(
    private val editEquipmentItemOperation : EditEquipmentItemOperation,
    private val fetchEquipmentOperation : FetchEquipmentOperation,
    private val htmlView : ContextAwareViewRender,
) : HttpHandler {
    companion object{
        private val nameFormLens = FormField.nonEmptyString().required("name")
        private val productIdFormLens = FormField.nonEmptyString().required("productId")
        private val descriptionFormLens = FormField.nonEmptyString().required("description")
        private val submissionDateFormLens = FormField.localDate().required("submissionDate")
        private val equipmentFormLens = Body.webForm(
            Validator.Feedback,
            nameFormLens,
            productIdFormLens,
            descriptionFormLens,
            submissionDateFormLens
        ).toLens()
        private val idLens = Path.uuid().of("id")
    }

    override fun invoke(request: Request) : Response{
        val id = lensOrNull(idLens, request) ?: return Response(Status.BAD_REQUEST)
        val equipmentItemToEdit = fetchEquipmentOperation.fetch(id)
        val webForm = equipmentFormLens(request)
        if (webForm.errors.isEmpty()){
            editEquipmentItemOperation.invoke(
                Equipment(
                    id,
                    nameFormLens(webForm),
                    productIdFormLens(webForm),
                    descriptionFormLens(webForm),
                    submissionDateFormLens(webForm)
                )
            )
            return Response(FOUND).header("Location", "/equipment/"+equipmentItemToEdit!!.id.toString())
        }
        return Response(OK).with(htmlView(request) of EquipmentFormVM())
    }

}