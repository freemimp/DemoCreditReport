package uk.co.freemimp.democreditreport.domain.models

data class CreditReportDetails(
    val percentageCreditUsed: Int,
    val currentShortTermDebt: Int,
    val currentShortTermNonPromotionalDebt: Int,
    val currentShortTermCreditLimit: Int,
    val currentShortTermCreditUtilisation: Int,
    val changeInShortTermDebt: Int,
    val currentLongTermDebt: Int,
    val currentLongTermNonPromotionalDebt: Int,
    val changeInLongTermDebt: Int,
    val numPositiveScoreFactors: Int,
    val numNegativeScoreFactors: Int,
    val equifaxScoreBand: Int,
    val equifaxScoreBandDescription: String,
    val daysUntilNextReport: Int
)
