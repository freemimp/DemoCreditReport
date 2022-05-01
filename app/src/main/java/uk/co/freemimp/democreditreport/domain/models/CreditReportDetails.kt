package uk.co.freemimp.democreditreport.domain.models

data class CreditReportDetails(
    val percentageCreditUsed: Int,
    val currentShortTermDebt: Int,
    val currentShortTermCreditLimit: Int,
    val currentLongTermDebt: Int,
    val equifaxScoreBand: Int,
    val equifaxScoreBandDescription: String,
    val daysUntilNextReport: Int
)
