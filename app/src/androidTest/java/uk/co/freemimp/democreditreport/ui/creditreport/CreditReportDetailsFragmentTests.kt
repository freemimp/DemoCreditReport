package uk.co.freemimp.democreditreport.ui.creditreport

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import uk.co.freemimp.democreditreport.R
import uk.co.freemimp.democreditreport.ui.MainActivity
import uk.co.freemimp.democreditreport.ui.creditreport.koin.testModuleFailure
import uk.co.freemimp.democreditreport.ui.creditreport.koin.testModuleSuccess

@RunWith(AndroidJUnit4::class)
class CreditReportDetailsFragmentTests {

    @Test
    fun givenActivityStarts_whenAllCallsAreSuccessfulAndCreditReportDetailsFragmentIsOpened_thenDetailsAreCorrectlyPopulated() {
        loadKoinModules(testModuleSuccess)
        launchActivity<MainActivity>().use {
            onView(withId(R.id.pointsProgress)).perform(ViewActions.click())

            onView(withId(R.id.creditReportDetailsTitle))
                .check(matches(withText("Your Credit Report Details")))
            onView(withId(R.id.equifaxScoreDescription))
                .check(matches(withText("Equifax Score Description: Excellent")))
            onView(withId(R.id.equifaxScoreBand))
                .check(matches(withText("Equifax Score Band: 1")))
            onView(withId(R.id.daysTillUpdate))
                .check(matches(withText("Days Left Till Update: 1")))
            onView(withId(R.id.shortTermDebt))
                .check(matches(withText("Short term debt: £1")))
            onView(withId(R.id.shortTermDebtLimit))
                .check(matches(withText("Short term debt limit: £1")))
            onView(withId(R.id.percentageCreditUsed))
                .check(matches(withText("Percentage debt used: 1%")))
            onView(withId(R.id.longTermDebt))
                .check(matches(withText("Long term debt: £1")))
        }

        unloadKoinModules(testModuleSuccess)
    }

    @Test
    fun givenActivityStarts_whenCallsAreNotSuccessfulAndCreditReportDetailsFragmentIsOpened_thenDetailsAreCorrectlyPopulatedWithZerosAndQuestionMarksAndErrorIsShown() {
        loadKoinModules(testModuleFailure)
        launchActivity<MainActivity>().use {
            onView(withId(R.id.pointsProgress)).perform(ViewActions.click())

            onView(withId(R.id.creditReportDetailsTitle))
                .check(matches(withText("Your Credit Report Details")))
            onView(withId(R.id.equifaxScoreDescription))
                .check(matches(withText("Equifax Score Description: ???")))
            onView(withId(R.id.equifaxScoreBand))
                .check(matches(withText("Equifax Score Band: 0")))
            onView(withId(R.id.daysTillUpdate))
                .check(matches(withText("Days Left Till Update: 0")))
            onView(withId(R.id.shortTermDebt))
                .check(matches(withText("Short term debt: £0")))
            onView(withId(R.id.shortTermDebtLimit))
                .check(matches(withText("Short term debt limit: £0")))
            onView(withId(R.id.percentageCreditUsed))
                .check(matches(withText("Percentage debt used: 0%")))
            onView(withId(R.id.longTermDebt))
                .check(matches(withText("Long term debt: £0")))
            onView(withText("Something went wrong, please try again!")).check(matches(ViewMatchers.isDisplayed()))
        }

        unloadKoinModules(testModuleFailure)
    }
}
