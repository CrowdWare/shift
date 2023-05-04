package at.crowdware.shift.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import at.crowdware.shift.R
import at.crowdware.shift.databinding.FragmentHomeBinding
import at.crowdware.shift.ui.ListItem
import at.crowdware.shift.ui.TransactionListAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.setContext(requireActivity().application)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val transactions: ListView = binding.transactions
        val adapter = TransactionListAdapter(requireContext(), inflater, getTransList())
        transactions.setAdapter(adapter)
        return root
    }

    fun getTransList(): MutableList<ListItem> {
        val list: MutableList<ListItem> = mutableListOf()
        list.add(ListItem("30.05.2023", "Liquid scooped", "10 l"))
        list.add(ListItem("29.05.2023", "Liquid scooped", "10 l"))
        list.add(ListItem("28.05.2023", "Liquid scooped", "1 l"))
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}