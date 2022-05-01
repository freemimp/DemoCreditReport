package uk.co.freemimp.democreditreport.ui.creditreport

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
import uk.co.freemimp.democreditreport.domain.models.CreditScore
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditScoreUseCase
import uk.co.freemimp.democreditreport.mvvm.Event
import utils.InstantTaskExecutorExtension
import utils.TestException

@ExtendWith(MockKExtension::class, InstantTaskExecutorExtension::class)
internal class CreditReportViewModelTest {

    @RelaxedMockK
    private lateinit var currentScoreObserver: Observer<Event<String>>

    @RelaxedMockK
    private lateinit var maxScoreObserver: Observer<Event<String>>

    @RelaxedMockK
    private lateinit var innerArchAngleObserver: Observer<Event<Float>>

    @RelaxedMockK
    private lateinit var showErrorObserver: Observer<Event<Boolean>>

    @MockK
    private lateinit var useCase: GetCreditScoreUseCase

    @InjectMockKs
    private lateinit var sut: CreditReportViewModel

    @BeforeEach
    internal fun setUp() {
        sut.currentScore.observeForever(currentScoreObserver)
        sut.maxScore.observeForever(maxScoreObserver)
        sut.innerArcAngle.observeForever(innerArchAngleObserver)
        sut.showError.observeForever(showErrorObserver)
    }

    @Test
    @DisplayName("given getCreditScore is executed,when use case call is successful, then trigger current score, max score and inner arch angle observers with correct values and show error with false")
    fun `given getCreditScore is executed,when use case call is successful, then trigger observers with correct values and show error with false`() {
        val creditScore = mockk<CreditScore> {
            every { this@mockk.currentScore } returns CURRENT_POINTS
            every { this@mockk.maxScore } returns MAX_POINTS
        }
        coEvery { useCase.execute() } returns creditScore

        sut.getCreditScore()

        coVerifySequence {
            useCase.execute()
            currentScoreObserver.onChanged(withArg {
                assertEquals(
                    "$CURRENT_POINTS",
                    it.peekContent()
                )
            })
            maxScoreObserver.onChanged(withArg { assertEquals("$MAX_POINTS", it.peekContent()) })
            innerArchAngleObserver.onChanged(withArg { assertEquals(ANGLE, it.peekContent()) })
            showErrorObserver.onChanged(withArg { assertFalse(it.peekContent()) })
        }
    }

    @Test
    @DisplayName("given getCreditScore is executed,when use case call is NOT successful, then trigger current score and max score with _???_, inner arch angle with 360 degrees and show error observer with true")
    fun `given getCreditScore is executed,when use case call is NOT successful, then trigger observers and show error with true`() {
        coEvery { useCase.execute() } throws TestException

        sut.getCreditScore()

        coVerifySequence {
            useCase.execute()
            showErrorObserver.onChanged(withArg {
                assertTrue(it.peekContent())
            })
            maxScoreObserver.onChanged(withArg { assertEquals(ON_ERROR_POINTS, it.peekContent()) })
            currentScoreObserver.onChanged(withArg {
                assertEquals(
                    ON_ERROR_POINTS,
                    it.peekContent()
                )
            })
            innerArchAngleObserver.onChanged(withArg {
                assertEquals(
                    ON_ERROR_ANGLE,
                    it.peekContent()
                )
            })
        }
    }
}

private const val CURRENT_POINTS = 350
private const val ON_ERROR_POINTS = "???"
private const val MAX_POINTS = 700
private const val ANGLE = 180f
private const val ON_ERROR_ANGLE = 360f
