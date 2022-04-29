package uk.co.freemimp.democreditreport.ui.creditreportdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.co.freemimp.democreditreport.databinding.CreditReportDetailsFragmentBinding

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

        viewModel.getDetails()

        viewModel.equifaxScoreBandDescription.observe(viewLifecycleOwner) {
            binding.id.text = it
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
