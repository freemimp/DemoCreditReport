package uk.co.freemimp.democreditreport.ui.creditreport

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditScoreUseCase
import uk.co.freemimp.democreditreport.mvvm.Event

class CreditReportViewModel constructor(private val getCreditScoreUseCase: GetCreditScoreUseCase) :
    ViewModel() {

    private val _currentScore = MutableLiveData<Event<String>>()
    val currentScore: LiveData<Event<String>> = _currentScore

    private val _maxScore = MutableLiveData<Event<String>>()
    val maxScore: LiveData<Event<String>> = _maxScore

    private val _innerArcAngle = MutableLiveData<Event<Float>>()
    val innerArcAngle: LiveData<Event<Float>> = _innerArcAngle

    private val _showError = MutableLiveData<Event<Boolean>>()
    val showError: LiveData<Event<Boolean>> = _showError

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _showError.postValue(Event(true))
        _maxScore.postValue(Event(QUESTION_MARKS))
        _currentScore.postValue(Event(QUESTION_MARKS))
        _innerArcAngle.postValue(Event(DEGREES_IN_CIRCLE))
    }

    fun getCreditScore() {
        viewModelScope.launch(exceptionHandler) {
            val creditScore = getCreditScoreUseCase.execute()

            _currentScore.postValue(Event(creditScore.currentScore.toString()))
            _maxScore.postValue(Event(creditScore.maxScore.toString()))
            _innerArcAngle.postValue(Event(calculateAngle(creditScore.currentScore, creditScore.maxScore)))

            _showError.postValue(Event(false))
        }
    }

    private fun calculateAngle(currentScore:Int, maxScore: Int): Float {
       return (currentScore.toFloat() / maxScore.toFloat()) * DEGREES_IN_CIRCLE
    }
}

private const val DEGREES_IN_CIRCLE = 360f
private const val QUESTION_MARKS = "???"
