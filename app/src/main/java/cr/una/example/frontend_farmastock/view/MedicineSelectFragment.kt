package cr.una.example.frontend_farmastock.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cr.una.example.frontend_farmastock.adapter.MedicineSelectAdapter
import cr.una.example.frontend_farmastock.databinding.FragmentMedicineSelectBinding
import cr.una.example.frontend_farmastock.viewmodel.StateMedicine
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModel
import cr.una.example.frontend_farmastock.viewmodel.MedicineViewModelFactory

class MedicineSelectFragment : Fragment() {


    // Definition of the binding variable
    private var _binding: FragmentMedicineSelectBinding? = null
    private val binding get() = _binding!!


    private val medicineViewModel: MedicineViewModel by activityViewModels{
        MedicineViewModelFactory()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = MedicineSelectAdapter()
        _binding = FragmentMedicineSelectBinding.inflate(inflater, container, false)
        binding.rvMedicineListSelect.adapter = adapter
        binding.rvMedicineListSelect.layoutManager = LinearLayoutManager(activity)
        medicineViewModel.findAllMedicine()
        medicineViewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                // just checking equality because Loading is a -singleton object instance-
                StateMedicine.Loading -> {
                    // TODO: If you need do something in loading
                }
                // Error and Success are both -classes- so we need to check their type with 'is'
                is StateMedicine.Error -> {
                    // TODO: If you need do something in error
                }
                is StateMedicine.SuccessList -> {
                    state.medicineList?.let { adapter.setVehicleList(it) }
                }
                else -> {
                    // TODO: Not state loaded
                }
            }
        }

        binding.SearchViewMedicineSelect.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        return binding.root

    }


}