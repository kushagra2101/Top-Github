package com.kushagragoel.top_github.ui.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kushagragoel.top_github.R
import com.kushagragoel.top_github.databinding.RepoDetailFragmentBinding
import com.kushagragoel.top_github.network.model.Item

class RepoDetailFragment : Fragment() {

    private lateinit var viewModel: RepoDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = RepoDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RepoDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val repoItem = requireArguments().getParcelable("repo_item") as Item?

        Glide.with(requireContext())
            .load(repoItem!!.avatars?.get(0))
            .apply(
                RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)

        view.findViewById<TextView>(R.id.repoDetailView).text = HtmlCompat.fromHtml(repoItem.toString(),
            HtmlCompat.FROM_HTML_MODE_LEGACY)

    }

}