package uk.co.freemimp.democreditreport.domain.usecases

import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import uk.co.freemimp.democreditreport.domain.CreditReportRepository
import uk.co.freemimp.democreditreport.domain.models.CreditScore
import utils.TestException

@ExtendWith(MockKExtension::class)
internal class GetCreditScoreUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: CreditReportRepository

    @InjectMockKs
    private lateinit var sut: GetCreditScoreUseCase

    @Test
    fun `given execute is invoked, when repository call is successful, then return CreditScore`() {
        runBlocking {
            val creditScore = mockk<CreditScore>()
            coEvery { repository.getCreditScore() } returns creditScore

            val actual = sut.execute()

            assertEquals(creditScore, actual)
            coVerifySequence {
                repository.getCreditScore()
            }
        }
    }

    @Test
    fun `given execute is invoked, when repository call is NOT successful, then throw Exception`() {
        runBlocking {
            coEvery { repository.getCreditScore() } throws TestException

            assertThrows<TestException> { sut.execute() }
            coVerifySequence {
                repository.getCreditScore()
            }
        }
    }
}
