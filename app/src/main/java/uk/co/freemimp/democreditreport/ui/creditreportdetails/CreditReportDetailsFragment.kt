package uk.co.freemimp.democreditreport.ui.creditreportdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.co.freemimp.democreditreport.R
import uk.co.freemimp.democreditreport.databinding.CreditReportDetailsFragmentBinding
import uk.co.freemimp.democreditreport.mvvm.EventObserver

class CreditReportDetailsFragment : Fragment() {

    private val viewModel by viewModel<CreditReportDetailsViewModel>()

    private var _binding: CreditReportDetailsFragmentBinding? = null
    private val binding: CreditReportDetailsFragmentBinding
        get() = requireNotNull(_binding) {
            "Cannot access binding outside of onCreateView & onDestroyView as it will be null"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreditReportDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCreditReportDetails()

        viewModel.equifaxScoreDescription.observe(viewLifecycleOwner, EventObserver {
            binding.equifaxScoreDescription.text = getString(R.string.equifax_score_description, it)
        })
        viewModel.equifaxScoreBand.observe(viewLifecycleOwner, EventObserver {
            binding.equifaxScoreBand.text = getString(R.string.equifax_score_band, it)
        })
        viewModel.daysTillUpdate.observe(viewLifecycleOwner, EventObserver {
            binding.daysTillUpdate.text = getString(R.string.days_until_update, it)
        })
        viewModel.currentShortTermDebt.observe(viewLifecycleOwner, EventObserver {
            binding.shortTermDebt.text = getString(R.string.short_term_debt, it)
        })
        viewModel.currentShortTermDebtLimit.observe(viewLifecycleOwner, EventObserver {
            binding.shortTermDebtLimit.text = getString(R.string.short_term_debt_limit, it)
        })
        viewModel.percentageCreditUsed.observe(viewLifecycleOwner, EventObserver {
            binding.percentageCreditUsed.text =
                getString(R.string.percentage_debt_used, it)
        })
        viewModel.currentLongTermDebt.observe(viewLifecycleOwner, EventObserver {
            binding.longTermDebt.text = getString(R.string.long_term_debt, it)
        })
        viewModel.showError.observe(viewLifecycleOwner, EventObserver { showSnackBar(show = it) })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showSnackBar(show: Boolean) {
        val snackbar = Snackbar.make(
            binding.root,
            getString(R.string.error),
            BaseTransientBottomBar.LENGTH_LONG
        )
            .setAction(R.string.retry) { viewModel.getCreditReportDetails() }
        if (show) {
            snackbar.show()
        } else {
            snackbar.dismiss()
        }
    }
}
