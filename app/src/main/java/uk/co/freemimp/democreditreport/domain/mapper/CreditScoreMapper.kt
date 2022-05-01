package uk.co.freemimp.democreditreport.domain.mapper

import uk.co.freemimp.democreditreport.data.CreditReportResponse
import uk.co.freemimp.democreditreport.domain.models.CreditScore

class CreditScoreMapper {
    fun map(response: CreditReportResponse): CreditScore {
        return CreditScore(
            response.creditReportInfo.score,
            response.creditReportInfo.maxScoreValue
        )
    }
}
