package uk.co.freemimp.democreditreport.ui.creditreportdetails

import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditReportDetailsUseCase
import uk.co.freemimp.democreditreport.mvvm.Event
import utils.InstantTaskExecutorExtension
import utils.TestException

@ExtendWith(MockKExtension::class, InstantTaskExecutorExtension::class)
internal class CreditReportDetailsViewModelTest {

    @RelaxedMockK
    private lateinit var equifaxScoreDescriptionObserver: Observer<Event<String>>

    @RelaxedMockK
    private lateinit var equifaxScoreBandObserver: Observer<Event<Int>>

    @RelaxedMockK
    private lateinit var daysTillUpdateObserver: Observer<Event<Int>>

    @RelaxedMockK
    private lateinit var currentShortTermDebtObserver: Observer<Event<Int>>

    @RelaxedMockK
    private lateinit var currentShortTermDebtLimitObserver: Observer<Event<Int>>

    @RelaxedMockK
    private lateinit var percentageCreditUsedObserver: Observer<Event<Int>>

    @RelaxedMockK
    private lateinit var currentLongTermDebtObserver: Observer<Event<Int>>

    @RelaxedMockK
    private lateinit var showErrorObserver: Observer<Event<Boolean>>

    @MockK
    private lateinit var useCase: GetCreditReportDetailsUseCase

    @InjectMockKs
    private lateinit var sut: CreditReportDetailsViewModel

    @BeforeEach
    fun setUp() {
        sut.equifaxScoreDescription.observeForever(equifaxScoreDescriptionObserver)
        sut.equifaxScoreBand.observeForever(equifaxScoreBandObserver)
        sut.daysTillUpdate.observeForever(daysTillUpdateObserver)
        sut.currentShortTermDebt.observeForever(currentShortTermDebtObserver)
        sut.currentShortTermDebtLimit.observeForever(currentShortTermDebtLimitObserver)
        sut.percentageCreditUsed.observeForever(percentageCreditUsedObserver)
        sut.currentLongTermDebt.observeForever(currentLongTermDebtObserver)
        sut.showError.observeForever(showErrorObserver)
    }

    @Test
    @DisplayName("given getCreditReportDetails is executed,when use case call is successful, then trigger all observers with correct values and show error observer with false")
    fun `given getCreditReportDetails is executed,when use case call is successful, then trigger all observers`() {
        val equifaxScoreBandDescription = "Ok"
        val equifaxScoreBand = 4
        val daysUntilNextReport = 1
        val currentShortTermDebt = 1000
        val currentShortTermCreditLimit = 2000
        val percentageCreditUsed = 50
        val currentLongTermDebt = 20000

        val creditScore = mockk<CreditReportDetails> {
            every { this@mockk.equifaxScoreBandDescription } returns equifaxScoreBandDescription
            every { this@mockk.equifaxScoreBand } returns equifaxScoreBand
            every { this@mockk.daysUntilNextReport } returns daysUntilNextReport
            every { this@mockk.currentShortTermDebt } returns currentShortTermDebt
            every { this@mockk.currentShortTermCreditLimit } returns currentShortTermCreditLimit
            every { this@mockk.percentageCreditUsed } returns percentageCreditUsed
            every { this@mockk.currentLongTermDebt } returns currentLongTermDebt
        }
        coEvery { useCase.execute() } returns creditScore

        sut.getCreditReportDetails()

        coVerifySequence {
            useCase.execute()
            equifaxScoreDescriptionObserver.onChanged(withArg {
                assertEquals(
                    equifaxScoreBandDescription,
                    it.peekContent()
                )
            })
            equifaxScoreBandObserver.onChanged(withArg {
                assertEquals(
                    equifaxScoreBand,
                    it.peekContent()
                )
            })
            daysTillUpdateObserver.onChanged(withArg {
                assertEquals(
                    daysUntilNextReport,
                    it.peekContent()
                )
            })
            currentShortTermDebtObserver.onChanged(withArg {
                assertEquals(
                    currentShortTermDebt,
                    it.peekContent()
                )
            })
            currentShortTermDebtLimitObserver.onChanged(withArg {
                assertEquals(
                    currentShortTermCreditLimit,
                    it.peekContent()
                )
            })
            percentageCreditUsedObserver.onChanged(withArg {
                assertEquals(
                    percentageCreditUsed,
                    it.peekContent()
                )
            })
            currentLongTermDebtObserver.onChanged(withArg {
                assertEquals(
                    currentLongTermDebt,
                    it.peekContent()
                )
            })
            showErrorObserver.onChanged(withArg { assertFalse(it.peekContent()) })
        }
    }

    @Test
    @DisplayName("given getCreditReportDetails is executed,when use case call is NOT successful, then trigger all observers with 0 and _???_ and show error observer with true")
    fun `given getCreditReportDetails is executed,when use case call is NOT successful, then trigger all observers`() {
        coEvery { useCase.execute() } throws TestException

        sut.getCreditReportDetails()

        coVerifySequence {
            useCase.execute()
            showErrorObserver.onChanged(withArg { assertTrue(it.peekContent()) })
            equifaxScoreDescriptionObserver.onChanged(withArg {
                assertEquals(
                    "???",
                    it.peekContent()
                )
            })
            equifaxScoreBandObserver.onChanged(withArg {
                assertEquals(
                    0,
                    it.peekContent()
                )
            })
            daysTillUpdateObserver.onChanged(withArg {
                assertEquals(
                    0,
                    it.peekContent()
                )
            })
            currentShortTermDebtObserver.onChanged(withArg {
                assertEquals(
                    0,
                    it.peekContent()
                )
            })
            currentShortTermDebtLimitObserver.onChanged(withArg {
                assertEquals(
                    0,
                    it.peekContent()
                )
            })
            percentageCreditUsedObserver.onChanged(withArg {
                assertEquals(
                    0,
                    it.peekContent()
                )
            })
            currentLongTermDebtObserver.onChanged(withArg {
                assertEquals(
                    0,
                    it.peekContent()
                )
            })
        }
    }
}
