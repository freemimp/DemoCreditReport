package uk.co.freemimp.democreditreport.domain.usecases

import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.models.CreditScore

class GetCreditScoreUseCase constructor(private val creditReportRepository: CreditReportRepository) {

    suspend fun execute(): CreditScore {
       return creditReportRepository.getCreditScore()
    }
}