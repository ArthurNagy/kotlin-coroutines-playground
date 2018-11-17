package me.arthurnagy.news.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import me.arthurnagy.news.HomeBinding
import me.arthurnagy.news.R
import me.arthurnagy.news.core.observeNonNull
import me.arthurnagy.news.feature.detail.DetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeBinding.inflate(inflater)
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        val adapter = ArticlesAdapter()
        adapter.setOnItemSelectedListener {
            val articleId = viewModel.articles.value?.get(it)?.title
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundleOf(DetailFragment.ARTICLE_ID to articleId))
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.accent))

        viewModel.articles.observeNonNull(viewLifecycleOwner, adapter::submitList)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

}