package uk.co.freemimp.democreditreport.ui.creditreportdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditReportDetailsUseCase
import uk.co.freemimp.democreditreport.mvvm.Event

class CreditReportDetailsViewModel constructor(
    private val getCreditReportDetailsUseCase: GetCreditReportDetailsUseCase
) : ViewModel() {

    private val _equifaxScoreDescription = MutableLiveData<Event<String>>()
    val equifaxScoreDescription: LiveData<Event<String>> = _equifaxScoreDescription

    private val _equifaxScoreBand = MutableLiveData<Event<Int>>()
    val equifaxScoreBand: LiveData<Event<Int>> = _equifaxScoreBand

    private val _daysTillUpdate = MutableLiveData<Event<Int>>()
    val daysTillUpdate: LiveData<Event<Int>> = _daysTillUpdate

    private val _shortTermDebt = MutableLiveData<Event<Int>>()
    val shortTermDebt: LiveData<Event<Int>> = _shortTermDebt

    private val _shortTermDebtLimit = MutableLiveData<Event<Int>>()
    val shortTermDebtLimit: LiveData<Event<Int>> = _shortTermDebtLimit

    private val _shortTermCreditUsedPercentage = MutableLiveData<Event<Int>>()
    val shortTermCreditUsedPercentage: LiveData<Event<Int>> = _shortTermCreditUsedPercentage

    private val _longTermDebt = MutableLiveData<Event<Int>>()
    val longTermDebt: LiveData<Event<Int>> = _longTermDebt

    private val _showError = MutableLiveData<Event<Boolean>>()
    val showError: LiveData<Event<Boolean>> = _showError

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _showError.postValue(Event(true))
        _equifaxScoreDescription.postValue(Event(QUESTION_MARKS))
        _equifaxScoreBand.postValue(Event(0))
        _daysTillUpdate.postValue(Event(0))
        _shortTermDebt.postValue(Event(0))
        _shortTermDebtLimit.postValue(Event(0))
        _shortTermCreditUsedPercentage.postValue(Event(0))
        _longTermDebt.postValue(Event(0))
        _daysTillUpdate.postValue(Event(0))
    }


    fun getCreditReportDetails() {
        viewModelScope.launch(exceptionHandler) {
            val details = getCreditReportDetailsUseCase.execute()

            _equifaxScoreDescription.postValue(Event(details.equifaxScoreBandDescription))
            _equifaxScoreBand.postValue(Event(details.equifaxScoreBand))
            _daysTillUpdate.postValue(Event(details.daysUntilNextReport))
            _shortTermDebt.postValue(Event(details.currentShortTermDebt))
            _shortTermDebtLimit.postValue(Event(details.currentShortTermCreditLimit))
            _shortTermCreditUsedPercentage.postValue(Event(details.percentageCreditUsed))
            _longTermDebt.postValue(Event(details.currentLongTermDebt))
            _daysTillUpdate.postValue(Event(details.daysUntilNextReport))

            _showError.postValue(Event(false))
        }
    }
}

private const val QUESTION_MARKS = "???"
