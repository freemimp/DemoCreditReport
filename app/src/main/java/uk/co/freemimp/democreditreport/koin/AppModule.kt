package uk.co.freemimp.democreditreport.koin

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.co.freemimp.democreditreport.data.RetrofitCreditReportRepository
import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditReportDetailsUseCase
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditScoreUseCase
import uk.co.freemimp.democreditreport.ui.creditreport.CreditReportViewModel
import uk.co.freemimp.democreditreport.ui.creditreportdetails.CreditReportDetailsViewModel

val appModule = module {

    single<CreditReportRepository> { RetrofitCreditReportRepository(get()) }

    single { GetCreditScoreUseCase(get()) }
    single { GetCreditReportDetailsUseCase(get()) }

    viewModel { CreditReportViewModel(get()) }
    viewModel { CreditReportDetailsViewModel(get()) }
}