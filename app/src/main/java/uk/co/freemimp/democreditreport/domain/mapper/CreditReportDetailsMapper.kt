package uk.co.freemimp.democreditreport.domain.mapper

import uk.co.freemimp.democreditreport.data.CreditReportResponse
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails

class CreditReportDetailsMapper {
    fun map(response: CreditReportResponse): CreditReportDetails {
        return CreditReportDetails(
            response.creditReportInfo.percentageCreditUsed,
            response.creditReportInfo.currentShortTermDebt,
            response.creditReportInfo.currentShortTermCreditLimit,
            response.creditReportInfo.currentLongTermDebt,
            response.creditReportInfo.equifaxScoreBand,
            response.creditReportInfo.equifaxScoreBandDescription,
            response.creditReportInfo.daysUntilNextReport
        )
    }
}
