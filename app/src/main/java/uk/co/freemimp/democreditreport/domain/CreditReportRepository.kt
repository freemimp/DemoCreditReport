package uk.co.freemimp.democreditreport.domain

import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails
import uk.co.freemimp.democreditreport.domain.models.CreditScore

interface CreditReportRepository {
    suspend fun getCreditScore(): CreditScore
    suspend fun getCreditReportDetails(): CreditReportDetails
}
