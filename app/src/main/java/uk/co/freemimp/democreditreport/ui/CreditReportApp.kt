package uk.co.freemimp.democreditreport.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import uk.co.freemimp.democreditreport.BuildConfig
import uk.co.freemimp.democreditreport.koin.apiModule
import uk.co.freemimp.democreditreport.koin.appModule
import uk.co.freemimp.democreditreport.koin.retrofitModule


class CreditReportApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            allowOverride(true)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CreditReportApp)
            modules(
                listOf(
                    appModule,
                    apiModule,
                    retrofitModule
                )
            )
        }
    }
}
