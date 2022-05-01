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
internal class CreditScoreMapperTest {

    @RelaxedMockK
    private lateinit var response: CreditReportResponse

    @InjectMockKs
    private lateinit var sut: CreditScoreMapper

    @Test
    fun `given map is invoked, then map score from response to currentScore in CreditScore`() {
        val score = 100
        every { response.creditReportInfo.score } returns score

        val result = sut.map(response).currentScore

        assertEquals(score, result)
    }

    @Test
    fun `given map is invoked, then map maxScoreValue from response to maxScore in CreditScore`() {
        val maxScoreValue = 100
        every { response.creditReportInfo.maxScoreValue } returns maxScoreValue

        val result = sut.map(response).maxScore

        assertEquals(maxScoreValue, result)
    }
}
