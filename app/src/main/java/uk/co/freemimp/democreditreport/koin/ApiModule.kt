package uk.co.freemimp.democreditreport.koin

import org.koin.dsl.module
import retrofit2.Retrofit
import uk.co.freemimp.democreditreport.data.CreditReportService

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(CreditReportService::class.java) }
}