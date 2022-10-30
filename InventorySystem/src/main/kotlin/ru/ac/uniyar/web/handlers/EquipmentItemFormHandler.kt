package ru.ac.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.*
import ru.ac.uniyar.domain.operations.AddEquipmentItemOperation
import ru.ac.uniyar.web.models.EquipmentFormVM
import ru.ac.uniyar.web.templates.ContextAwareViewRender

class ShowEquipmentItemFormHandler(
    private val htmlView : ContextAwareViewRender
) : HttpHandler{
    override fun invoke(request: Request): Response {
        return Response(OK).with(htmlView(request) of EquipmentFormVM())
    }
}

class AddEquipmentItemFormHandler(
    private val addEquipmentItemOperation : AddEquipmentItemOperation,
    private val htmlView: ContextAwareViewRender
) : HttpHandler {
    companion object {
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
    }

    override fun invoke(request: Request) : Response{
        val webForm = equipmentFormLens(request)
        if (webForm.errors.isEmpty()){
            val start = addEquipmentItemOperation.invoke(
                nameFormLens(webForm),
                productIdFormLens(webForm),
                descriptionFormLens(webForm),
                submissionDateFormLens(webForm)
            )
            return Response(Status.FOUND).header("Location", "/equipment/"+start.toString())
        }
        return Response(OK).with(htmlView(request) of EquipmentFormVM(
            null,
            null,
            null,
            null,
            webForm
        )
        )
    }

}
