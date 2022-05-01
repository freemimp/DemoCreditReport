package uk.co.freemimp.democreditreport.ui.creditreport

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import uk.co.freemimp.democreditreport.R
import uk.co.freemimp.democreditreport.ui.MainActivity
import uk.co.freemimp.democreditreport.ui.creditreport.koin.CURRENT_POINTS
import uk.co.freemimp.democreditreport.ui.creditreport.koin.MAX_POINTS
import uk.co.freemimp.democreditreport.ui.creditreport.koin.testModuleFailure
import uk.co.freemimp.democreditreport.ui.creditreport.koin.testModuleSuccess

@RunWith(AndroidJUnit4::class)
class CreditReportFragmentTests {

    @Test
    fun givenActivityStarts_whenAllCallsAreSuccessful_thenShowFragmentWithAllDetailsAndInnerArc() {
        loadKoinModules(testModuleSuccess)
        launchActivity<MainActivity>().use {
            onView(withText("Your credit score is")).check(matches(isDisplayed()))
            onView(withId(R.id.creditReportBodyPoints)).check(matches(withText("$CURRENT_POINTS")))
            onView(withId(R.id.creditReportBodyMaxPoints)).check(matches(withText("out of $MAX_POINTS")))
        }

        unloadKoinModules(testModuleSuccess)
    }

    @Test
    fun givenActivityStarts_whenAllCallsAreSuccessfulAndPointsProgressIsClicked_thenShowDetailsFragment() {
        loadKoinModules(testModuleSuccess)
        launchActivity<MainActivity>().use {
            onView(withId(R.id.pointsProgress)).perform(click())

            onView(withId(R.id.creditReportDetailsTitle)).check(matches(isDisplayed()))
        }

        unloadKoinModules(testModuleSuccess)
    }

    @Test
    fun givenActivityStarts_whenAllCallsAreNotSuccessful_thenShowFragmentWithErrorDetailsAndSnackBar() {
        loadKoinModules(testModuleFailure)

        launchActivity<MainActivity>().use {
            onView(withText("Your credit score is")).check(matches(isDisplayed()))
            onView(withId(R.id.creditReportBodyPoints)).check(matches(withText("???")))
            onView(withId(R.id.creditReportBodyMaxPoints)).check(matches(withText("out of ???")))
            onView(withText("Something went wrong, please try again!")).check(matches(isDisplayed()))
        }

        unloadKoinModules(testModuleFailure)
    }
}
