package uk.co.freemimp.democreditreport.data

import retrofit2.HttpException
import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails
import uk.co.freemimp.democreditreport.domain.models.CreditScore

class RetrofitCreditReportRepository constructor(
    private val creditReportService: CreditReportService
) : CreditReportRepository {

    override suspend fun getCreditReportDetails(): CreditReportDetails {
        return try {
            val response = creditReportService.getCreditReport()
            if (response.isSuccessful) {
                CreditReportDetails(
                    requireNotNull(response.body()).creditReportInfo.percentageCreditUsed,
                    requireNotNull(response.body()).creditReportInfo.currentShortTermDebt,
                    requireNotNull(response.body()).creditReportInfo.currentShortTermNonPromotionalDebt,
                    requireNotNull(response.body()).creditReportInfo.currentShortTermCreditLimit,
                    requireNotNull(response.body()).creditReportInfo.currentShortTermCreditUtilisation,
                    requireNotNull(response.body()).creditReportInfo.changeInShortTermDebt,
                    requireNotNull(response.body()).creditReportInfo.currentLongTermDebt,
                    requireNotNull(response.body()).creditReportInfo.currentLongTermNonPromotionalDebt,
                    requireNotNull(response.body()).creditReportInfo.changeInLongTermDebt,
                    requireNotNull(response.body()).creditReportInfo.numPositiveScoreFactors,
                    requireNotNull(response.body()).creditReportInfo.numNegativeScoreFactors,
                    requireNotNull(response.body()).creditReportInfo.equifaxScoreBand,
                    requireNotNull(response.body()).creditReportInfo.equifaxScoreBandDescription,
                    requireNotNull(response.body()).creditReportInfo.daysUntilNextReport
                )
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCreditScore(): CreditScore {
        return try {
            val response = creditReportService.getCreditReport()
            if (response.isSuccessful) {
                CreditScore(
                    requireNotNull(response.body()).creditReportInfo.score,
                    requireNotNull(response.body()).creditReportInfo.maxScoreValue
                )
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
