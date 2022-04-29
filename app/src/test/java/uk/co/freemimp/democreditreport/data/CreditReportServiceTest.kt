package uk.co.freemimp.democreditreport.data

import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import utils.JsonResourseToStringMapper

@ExtendWith(MockKExtension::class)
internal class CreditReportServiceTest {
    private lateinit var sut: CreditReportService

    private lateinit var server: MockWebServer

    private val response =
        JsonResourseToStringMapper.getJsonStringFromFile("response.json")

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(response)
        )
        server.start()
        sut = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CreditReportService::class.java)

    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Nested
    @DisplayName("given get details is invoked, ")
    inner class GetDetails {

        @Test
        fun `then api call is made with correct path`() {
            runBlocking {
                sut.getCreditReport()

                val expected = "/endpoint.json"
                val actual = server.takeRequest().path

                assertEquals(expected, actual)
            }
        }

        @Test
        fun `then api call is made with correct http method`() {
            runBlocking {
                sut.getCreditReport()

                val expected = "GET"
                val actual = server.takeRequest().method

                assertEquals(expected, actual)
            }
        }
    }
}
