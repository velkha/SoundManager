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
import org.velka.soundmanager.databinding.FragmentHomeBinding
import org.velka.soundmanager.model.SoundProfile

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

        viewManager = GridLayoutManager(context, 2) // 2 columns in the grid
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

    private fun loadProfiles(): List<SoundProfile> {
        val gson = Gson()
        val profiles = mutableListOf<SoundProfile>()

        // Open the file and read the JSON string
        val jsonString = context?.openFileInput("profiles.json")?.bufferedReader().use { it?.readText() }

        // Convert the JSON string to a SoundProfile object and add it to the list
        if (jsonString != null) {
            val type = object : TypeToken<List<SoundProfile>>() {}.type
            profiles.addAll(gson.fromJson(jsonString, type))
        }

        return profiles
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}