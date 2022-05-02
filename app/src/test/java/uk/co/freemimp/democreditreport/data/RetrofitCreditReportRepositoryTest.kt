package uk.co.freemimp.democreditreport.data

import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.HttpException
import retrofit2.Response
import uk.co.freemimp.democreditreport.domain.mapper.CreditReportDetailsMapper
import uk.co.freemimp.democreditreport.domain.mapper.CreditScoreMapper
import uk.co.freemimp.democreditreport.domain.models.CreditReportDetails
import uk.co.freemimp.democreditreport.domain.models.CreditScore
import utils.TestException

@ExtendWith(MockKExtension::class)
internal class RetrofitCreditReportRepositoryTest {

    @MockK
    private lateinit var service: CreditReportService
    @MockK
    private lateinit var creditScoreMapper: CreditScoreMapper
    @MockK
    private lateinit var creditReportDetailsMapper: CreditReportDetailsMapper
    @InjectMockKs
    private lateinit var sut: RetrofitCreditReportRepository

    @RelaxedMockK
    private lateinit var response: Response<CreditReportResponse>

    @RelaxedMockK
    private lateinit var creditReportResponse: CreditReportResponse

    @Nested
    @DisplayName("given getCreditScore is invoked, ")
    inner class GetCreditScore {

        @Test
        fun `when service call is successful, then return CreditScore`() {
            runBlocking {
                val creditScore = mockk<CreditScore>()
                every { response.isSuccessful } returns true
                every { response.body() } returns creditReportResponse
                coEvery { service.getCreditReport() } returns response
                every { creditScoreMapper.map(any()) } returns creditScore


                val actual = sut.getCreditScore()

                assertEquals(creditScore, actual)
            }
        }

        @Test
        fun `when service call is NOT successful, then throw HttpException`() {
            runBlocking {
                every { response.isSuccessful } returns false
                every { response.body() } returns creditReportResponse
                coEvery { service.getCreditReport() } returns response

                assertThrows<HttpException> { sut.getCreditScore() }
            }
        }

        @Test
        fun `when service call throws exception, then throw this exception`() {
            runBlocking {
                coEvery { service.getCreditReport() } throws TestException

                assertThrows<TestException> { sut.getCreditScore() }
            }
        }
    }

    @Nested
    @DisplayName("given getCreditReportDetails is invoked, ")
    inner class GetCreditReportDetails {
        @Test
        fun `when service call is successful, then return CreditReportDetails`() {
            runBlocking {
                val creditReportDetails = mockk<CreditReportDetails>()
                every { creditReportDetailsMapper.map(any()) } returns creditReportDetails
                every { response.isSuccessful } returns true
                every { response.body() } returns creditReportResponse
                coEvery { service.getCreditReport() } returns response

                val actual = sut.getCreditReportDetails()

                assertEquals(creditReportDetails, actual)
            }
        }

        @Test
        fun `when service call is NOT successful, then throw HttpException`() {
            runBlocking {
                every { response.isSuccessful } returns false
                every { response.body() } returns creditReportResponse
                coEvery { service.getCreditReport() } returns response

                assertThrows<HttpException> { sut.getCreditReportDetails() }
            }
        }

        @Test
        fun `when service call throws exception, then throw this exception`() {
            runBlocking {
                coEvery { service.getCreditReport() } throws TestException

                assertThrows<TestException> { sut.getCreditReportDetails() }
            }
        }
    }
}
