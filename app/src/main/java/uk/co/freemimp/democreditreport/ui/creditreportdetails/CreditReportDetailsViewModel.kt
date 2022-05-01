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

    private val _currentShortTermDebt = MutableLiveData<Event<Int>>()
    val currentShortTermDebt: LiveData<Event<Int>> = _currentShortTermDebt

    private val _currentShortTermDebtLimit = MutableLiveData<Event<Int>>()
    val currentShortTermDebtLimit: LiveData<Event<Int>> = _currentShortTermDebtLimit

    private val _percentageCreditUsed = MutableLiveData<Event<Int>>()
    val percentageCreditUsed: LiveData<Event<Int>> = _percentageCreditUsed

    private val _currentLongTermDebt = MutableLiveData<Event<Int>>()
    val currentLongTermDebt: LiveData<Event<Int>> = _currentLongTermDebt

    private val _showError = MutableLiveData<Event<Boolean>>()
    val showError: LiveData<Event<Boolean>> = _showError

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _showError.postValue(Event(true))

        _equifaxScoreDescription.postValue(Event(QUESTION_MARKS))
        _equifaxScoreBand.postValue(Event(0))
        _daysTillUpdate.postValue(Event(0))
        _currentShortTermDebt.postValue(Event(0))
        _currentShortTermDebtLimit.postValue(Event(0))
        _percentageCreditUsed.postValue(Event(0))
        _currentLongTermDebt.postValue(Event(0))
    }


    fun getCreditReportDetails() {
        viewModelScope.launch(exceptionHandler) {
            val details = getCreditReportDetailsUseCase.execute()

            _equifaxScoreDescription.postValue(Event(details.equifaxScoreBandDescription))
            _equifaxScoreBand.postValue(Event(details.equifaxScoreBand))
            _daysTillUpdate.postValue(Event(details.daysUntilNextReport))
            _currentShortTermDebt.postValue(Event(details.currentShortTermDebt))
            _currentShortTermDebtLimit.postValue(Event(details.currentShortTermCreditLimit))
            _percentageCreditUsed.postValue(Event(details.percentageCreditUsed))
            _currentLongTermDebt.postValue(Event(details.currentLongTermDebt))

            _showError.postValue(Event(false))
        }
    }
}

private const val QUESTION_MARKS = "???"
