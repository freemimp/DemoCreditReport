package uk.co.freemimp.democreditreport.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditReportResponse(
    @Json(name = "accountIDVStatus")
    val accountIDVStatus: String,
    @Json(name = "creditReportInfo")
    val creditReportInfo: CreditReportInfo,
    @Json(name = "dashboardStatus")
    val dashboardStatus: String,
    @Json(name = "personaType")
    val personaType: String,
    @Json(name = "coachingSummary")
    val coachingSummary: CoachingSummary,
    @Json(name = "augmentedCreditScore")
    val augmentedCreditScore: Int?
) {
    @JsonClass(generateAdapter = true)
    data class CreditReportInfo(
        @Json(name = "score")
        val score: Int,
        @Json(name = "scoreBand")
        val scoreBand: Int,
        @Json(name = "clientRef")
        val clientRef: String,
        @Json(name = "status")
        val status: String,
        @Json(name = "maxScoreValue")
        val maxScoreValue: Int,
        @Json(name = "minScoreValue")
        val minScoreValue: Int,
        @Json(name = "monthsSinceLastDefaulted")
        val monthsSinceLastDefaulted: Int,
        @Json(name = "hasEverDefaulted")
        val hasEverDefaulted: Boolean,
        @Json(name = "monthsSinceLastDelinquent")
        val monthsSinceLastDelinquent: Int,
        @Json(name = "hasEverBeenDelinquent")
        val hasEverBeenDelinquent: Boolean,
        @Json(name = "percentageCreditUsed")
        val percentageCreditUsed: Int,
        @Json(name = "percentageCreditUsedDirectionFlag")
        val percentageCreditUsedDirectionFlag: Int,
        @Json(name = "changedScore")
        val changedScore: Int,
        @Json(name = "currentShortTermDebt")
        val currentShortTermDebt: Int,
        @Json(name = "currentShortTermNonPromotionalDebt")
        val currentShortTermNonPromotionalDebt: Int,
        @Json(name = "currentShortTermCreditLimit")
        val currentShortTermCreditLimit: Int,
        @Json(name = "currentShortTermCreditUtilisation")
        val currentShortTermCreditUtilisation: Int,
        @Json(name = "changeInShortTermDebt")
        val changeInShortTermDebt: Int,
        @Json(name = "currentLongTermDebt")
        val currentLongTermDebt: Int,
        @Json(name = "currentLongTermNonPromotionalDebt")
        val currentLongTermNonPromotionalDebt: Int,
        @Json(name = "currentLongTermCreditLimit")
        val currentLongTermCreditLimit: Any?,
        @Json(name = "currentLongTermCreditUtilisation")
        val currentLongTermCreditUtilisation: Any?,
        @Json(name = "changeInLongTermDebt")
        val changeInLongTermDebt: Int,
        @Json(name = "numPositiveScoreFactors")
        val numPositiveScoreFactors: Int,
        @Json(name = "numNegativeScoreFactors")
        val numNegativeScoreFactors: Int,
        @Json(name = "equifaxScoreBand")
        val equifaxScoreBand: Int,
        @Json(name = "equifaxScoreBandDescription")
        val equifaxScoreBandDescription: String,
        @Json(name = "daysUntilNextReport")
        val daysUntilNextReport: Int
    )

    @JsonClass(generateAdapter = true)
    data class CoachingSummary(
        @Json(name = "activeTodo")
        val activeTodo: Boolean,
        @Json(name = "activeChat")
        val activeChat: Boolean,
        @Json(name = "numberOfTodoItems")
        val numberOfTodoItems: Int,
        @Json(name = "numberOfCompletedTodoItems")
        val numberOfCompletedTodoItems: Int,
        @Json(name = "selected")
        val selected: Boolean
    )
}