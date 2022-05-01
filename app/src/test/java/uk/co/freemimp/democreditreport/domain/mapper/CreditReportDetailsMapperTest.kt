package uk.co.freemimp.democreditreport.domain.mapper

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import uk.co.freemimp.democreditreport.data.CreditReportResponse

@ExtendWith(MockKExtension::class)
internal class CreditReportDetailsMapperTest {

    @RelaxedMockK
    private lateinit var response: CreditReportResponse

    @InjectMockKs
    private lateinit var sut: CreditReportDetailsMapper

    @Test
    fun `given map is invoked, then map equifaxScoreBandDescription from response to equifaxScoreBandDescription in CreditReportDetails`() {
        val equifaxScoreBandDescription = "Ok"
        every { response.creditReportInfo.equifaxScoreBandDescription } returns equifaxScoreBandDescription

        val result = sut.map(response).equifaxScoreBandDescription

        assertEquals(equifaxScoreBandDescription, result)
    }

    @Test
    fun `given map is invoked, then map equifaxScoreBand from response to equifaxScoreBand in CreditReportDetails`() {
        val equifaxScoreBand = 1
        every { response.creditReportInfo.equifaxScoreBand } returns equifaxScoreBand

        val result = sut.map(response).equifaxScoreBand

        assertEquals(equifaxScoreBand, result)
    }

    @Test
    fun `given map is invoked, then map daysUntilNextReport from response to daysUntilNextReport in CreditReportDetails`() {
        val daysUntilNextReport = 1
        every { response.creditReportInfo.daysUntilNextReport } returns daysUntilNextReport

        val result = sut.map(response).daysUntilNextReport

        assertEquals(daysUntilNextReport, result)
    }

    @Test
    fun `given map is invoked, then map currentShortTermDebt from response to currentShortTermDebt in CreditReportDetails`() {
        val currentShortTermDebt = 100
        every { response.creditReportInfo.currentShortTermDebt } returns currentShortTermDebt

        val result = sut.map(response).currentShortTermDebt

        assertEquals(currentShortTermDebt, result)
    }

    @Test
    fun `given map is invoked, then map currentShortTermCreditLimit from response to currentShortTermCreditLimit in CreditReportDetails`() {
        val currentShortTermCreditLimit = 100
        every { response.creditReportInfo.currentShortTermCreditLimit } returns currentShortTermCreditLimit

        val result = sut.map(response).currentShortTermCreditLimit

        assertEquals(currentShortTermCreditLimit, result)
    }

    @Test
    fun `given map is invoked, then map percentageCreditUsed from response to percentageCreditUsed in CreditReportDetails`() {
        val percentageCreditUsed = 100
        every { response.creditReportInfo.percentageCreditUsed } returns percentageCreditUsed

        val result = sut.map(response).percentageCreditUsed

        assertEquals(percentageCreditUsed, result)
    }

    @Test
    fun `given map is invoked, then map currentLongTermDebt from response to currentLongTermDebt in CreditReportDetails`() {
        val currentLongTermDebt = 100
        every { response.creditReportInfo.currentLongTermDebt } returns currentLongTermDebt

        val result = sut.map(response).currentLongTermDebt

        assertEquals(currentLongTermDebt, result)
    }
}
