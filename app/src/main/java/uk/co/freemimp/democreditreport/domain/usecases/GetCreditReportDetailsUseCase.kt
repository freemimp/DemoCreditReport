package uk.co.freemimp.democreditreport.domain.usecases

import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails

class GetCreditReportDetailsUseCase constructor(private val creditReportRepository: CreditReportRepository) {

    suspend fun execute(): CreditReportDetails {
        return creditReportRepository.getCreditReportDetails()
    }
}