package uk.co.freemimp.democreditreport.data

import retrofit2.Response
import retrofit2.http.GET

interface CreditReportService {

    @GET("endpoint.json")
    suspend fun getCreditReport(): Response<CreditReportResponse>
}