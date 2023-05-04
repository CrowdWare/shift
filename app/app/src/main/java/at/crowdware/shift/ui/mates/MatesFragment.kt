package at.crowdware.shift.ui.mates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.crowdware.shift.databinding.FragmentMatesBinding

class MatesFragment : Fragment() {

    private var _binding: FragmentMatesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val matesViewModel =
            ViewModelProvider(this).get(MatesViewModel::class.java)

        _binding = FragmentMatesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMates
        matesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}