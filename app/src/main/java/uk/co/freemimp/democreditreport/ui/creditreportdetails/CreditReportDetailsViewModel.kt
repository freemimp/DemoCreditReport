package uk.co.freemimp.democreditreport.ui.creditreportdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.co.freemimp.democreditreport.domain.usecases.GetCreditReportDetailsUseCase

class CreditReportDetailsViewModel constructor(
    private val getCreditReportDetailsUseCase: GetCreditReportDetailsUseCase
    ) : ViewModel() {

    private val _equifaxScoreBandDescription = MutableLiveData<String>()
    val equifaxScoreBandDescription: LiveData<String> = _equifaxScoreBandDescription


         fun getDetails() {
            viewModelScope.launch {
                val details = getCreditReportDetailsUseCase.execute()

                _equifaxScoreBandDescription.postValue(details.equifaxScoreBandDescription)
            }
        }
}