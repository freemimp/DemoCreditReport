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
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails
import utils.TestException

@ExtendWith(MockKExtension::class)
internal class GetCreditReportDetailsUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: CreditReportRepository

    @InjectMockKs
    private lateinit var sut: GetCreditReportDetailsUseCase

    @Test
    fun `given execute is invoked, when repository call is successful, then return CreditReportDetails`() {
        runBlocking {
            val creditReportDetails = mockk<CreditReportDetails>()
            coEvery { repository.getCreditReportDetails() } returns creditReportDetails

            val actual = sut.execute()

            assertEquals(creditReportDetails, actual)
            coVerifySequence {
                repository.getCreditReportDetails()
            }
        }
    }

    @Test
    fun `given execute is invoked, when repository call is NOT successful, then throw Exception`() {
        runBlocking {
            coEvery { repository.getCreditReportDetails() } throws TestException

            assertThrows<TestException> { sut.execute() }
            coVerifySequence {
                repository.getCreditReportDetails()
            }
        }
    }
}
