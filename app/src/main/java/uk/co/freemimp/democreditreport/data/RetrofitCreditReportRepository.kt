package uk.co.freemimp.democreditreport.data

import retrofit2.HttpException
import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.mapper.CreditReportDetailsMapper
import uk.co.freemimp.democreditreport.domain.mapper.CreditScoreMapper
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails
import uk.co.freemimp.democreditreport.domain.models.CreditScore

class RetrofitCreditReportRepository constructor(
    private val creditReportService: CreditReportService,
    private val creditScoreMapper: CreditScoreMapper,
    private val creditReportDetailsMapper: CreditReportDetailsMapper
) : CreditReportRepository {

    override suspend fun getCreditScore(): CreditScore {
        return try {
            val response = creditReportService.getCreditReport()
            if (response.isSuccessful) {
                creditScoreMapper.map(requireNotNull(response.body()))
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCreditReportDetails(): CreditReportDetails {
        return try {
            val response = creditReportService.getCreditReport()
            if (response.isSuccessful) {
                creditReportDetailsMapper.map(requireNotNull(response.body()))
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
