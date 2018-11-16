package me.arthurnagy.news.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import me.arthurnagy.news.HomeBinding
import me.arthurnagy.news.core.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = HomeBinding.inflate(inflater)

        val adapter = ArticlesAdapter()

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        viewModel.articles.observeNonNull(viewLifecycleOwner, adapter::submitList)

        return binding.root
    }

}