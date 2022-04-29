package uk.co.freemimp.democreditreport.ui.creditreport.koin

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.HttpException
import uk.co.freemimp.democreditreport.data.RetrofitCreditReportRepository
import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.models.CreditScore
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditReportDetailsUseCase
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditScoreUseCase
import uk.co.freemimp.democreditreport.ui.creditreport.CreditReportViewModel
import uk.co.freemimp.democreditreport.ui.creditreportdetails.CreditReportDetailsViewModel

val testModuleSuccess = module {
    single<CreditReportRepository> { mockk<RetrofitCreditReportRepository>() {
        coEvery { this@mockk.getCreditScore() } returns CreditScore(CURRENT_POINTS, MAX_POINTS)
        coEvery { this@mockk.getCreditReportDetails() } returns mockk (relaxed = true) {
            every { equifaxScoreBandDescription } returns  "Excellent"
        }
    } }
    single { GetCreditScoreUseCase(get()) }
    single { GetCreditReportDetailsUseCase(get()) }

    viewModel { CreditReportViewModel(get()) }
    viewModel { CreditReportDetailsViewModel(get()) }
}

val testModuleFailure = module {
    single<CreditReportRepository> { mockk<RetrofitCreditReportRepository>() {
        coEvery { this@mockk.getCreditScore() } throws  HttpException(mockk(relaxed = true))
        coEvery { this@mockk.getCreditReportDetails() } returns mockk (relaxed = true) {
            every { equifaxScoreBandDescription } returns  "Excellent"
        }
    } }
    single { GetCreditScoreUseCase(get()) }
    single { GetCreditReportDetailsUseCase(get()) }

    viewModel { CreditReportViewModel(get()) }
    viewModel { CreditReportDetailsViewModel(get()) }
}

internal const val CURRENT_POINTS = 350
internal const val MAX_POINTS = 700
