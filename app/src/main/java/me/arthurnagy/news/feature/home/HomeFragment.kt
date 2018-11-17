package me.arthurnagy.news.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import me.arthurnagy.news.HomeBinding
import me.arthurnagy.news.R
import me.arthurnagy.news.core.GlideApp
import me.arthurnagy.news.core.data.article.Article
import me.arthurnagy.news.core.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: HomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeBinding.inflate(inflater)
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        val adapter = ArticlesAdapter()
        adapter.setOnItemSelectedListener {
            viewModel.articles.value?.get(it)?.title?.let { articleId ->
                findNavController().navigate(HomeFragmentDirections.ActionHomeFragmentToDetailFragment(articleId))
            }
        }

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter

            addOnScrollListener(
                RecyclerViewPreloader<Article>(
                    GlideApp.with(this),
                    adapter as ListPreloader.PreloadModelProvider<Article>,
                    adapter.glideSizeProvider,
                    10
                )
            )

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