package org.velka.soundmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.velka.soundmanager.adapters.ProfileGridAdapter
import org.velka.soundmanager.controllers.ProfileHomeController
import org.velka.soundmanager.databinding.FragmentHomeBinding
import org.velka.soundmanager.model.SoundProfile
import org.velka.soundmanager.utils.PROFILE_JSON_FILENAME

class ProfileHomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ProfileHomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewManager = GridLayoutManager(context, 3) // 3 columns in the grid
        viewAdapter = ProfileGridAdapter(loadProfiles())
        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        recyclerView = binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return root
    }

    private fun loadProfiles(): MutableList<SoundProfile> {
        val profileHomeController = ProfileHomeController(requireContext())
        return profileHomeController.loadProfiles()
    }

    fun addProfileToAdapter(profile: SoundProfile) {
        (viewAdapter as ProfileGridAdapter).addProfile(profile)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}