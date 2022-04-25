package uk.co.freemimp.democreditreport.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import uk.co.freemimp.democreditreport.databinding.CreditReportFragmentBinding

class CreditReportFragment : Fragment() {

    private lateinit var viewModel: CreditReportViewModel

    private val interpolator: Interpolator = AccelerateDecelerateInterpolator()

    private var _binding: CreditReportFragmentBinding? = null
    private val binding: CreditReportFragmentBinding
        get() = requireNotNull(_binding) {
            "Cannot access binding outside of onCreateView & onDestroyView as it will be null"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreditReportFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreditReportViewModel::class.java]

        binding.pointsProgress.setInterpolator(interpolator)
        binding.pointsProgress.setProgress(0f, 270f)
    }
}
