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
            every { equifaxScoreBand } returns  1
            every { daysUntilNextReport } returns  1
            every { currentShortTermDebt } returns  1
            every { currentShortTermCreditLimit } returns  1
            every { percentageCreditUsed } returns  1
            every { currentLongTermDebt } returns  1
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
            every { equifaxScoreBandDescription } returns  "???"
            every { equifaxScoreBand } returns  0
            every { daysUntilNextReport } returns  0
            every { currentShortTermDebt } returns  0
            every { currentShortTermCreditLimit } returns  0
            every { percentageCreditUsed } returns  0
            every { currentLongTermDebt } returns  0
        }
    } }
    single { GetCreditScoreUseCase(get()) }
    single { GetCreditReportDetailsUseCase(get()) }

    viewModel { CreditReportViewModel(get()) }
    viewModel { CreditReportDetailsViewModel(get()) }
}

internal const val CURRENT_POINTS = 350
internal const val MAX_POINTS = 700
