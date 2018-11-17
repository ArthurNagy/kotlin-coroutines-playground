package me.arthurnagy.news.feature.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import me.arthurnagy.news.DetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel { parametersOf(DetailFragmentArgs.fromBundle(arguments).articleId) }
    private lateinit var binding: DetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DetailBinding.inflate(inflater)
        binding.setLifecycleOwner(viewLifecycleOwner)

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())

        binding.viewModel = viewModel
        binding.content.movementMethod = LinkMovementMethod.getInstance()

        binding.readMore.setOnClickListener {
            openArticle(requireContext(), viewModel.article.value?.url)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

}