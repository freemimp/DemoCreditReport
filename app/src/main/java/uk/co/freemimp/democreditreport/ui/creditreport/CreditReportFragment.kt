package uk.co.freemimp.democreditreport.ui.creditreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.co.freemimp.democreditreport.R
import uk.co.freemimp.democreditreport.databinding.CreditReportFragmentBinding
import uk.co.freemimp.democreditreport.mvvm.EventObserver

class CreditReportFragment : Fragment() {

    private val viewModel by viewModel<CreditReportViewModel>()

    private val interpolator: Interpolator = AccelerateDecelerateInterpolator()

    private var _binding: CreditReportFragmentBinding? = null
    private val binding: CreditReportFragmentBinding
        get() = requireNotNull(_binding) {
            "Cannot access binding outside of onCreateView & onDestroyView as it will be null"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreditReportFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pointsProgress.setInterpolator(interpolator)

        viewModel.getCreditScore()

        viewModel.currentScore.observe(viewLifecycleOwner, EventObserver {
            binding.creditReportBodyPoints.text = it
        })
        viewModel.maxScore.observe(viewLifecycleOwner, EventObserver {
            binding.creditReportBodyMaxPoints.text =
                getString(R.string.max_points_body, it)
        })
        viewModel.innerArcAngle.observe(viewLifecycleOwner, EventObserver {
            binding.pointsProgress.setProgress(it)
        })

        viewModel.showError.observe(viewLifecycleOwner, EventObserver { showSnackBar(show = it) })

        binding.pointsProgress.setOnClickListener { findNavController().navigate(R.id.creditReportDetailsFragment) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showSnackBar(show: Boolean) {
        val snackbar = Snackbar.make(
            binding.root,
            getString(R.string.error),
            LENGTH_INDEFINITE
        )
            .setAction(R.string.retry) { viewModel.getCreditScore() }
        if (show) {
            snackbar.show()
        } else {
            snackbar.dismiss()
        }
    }
}
