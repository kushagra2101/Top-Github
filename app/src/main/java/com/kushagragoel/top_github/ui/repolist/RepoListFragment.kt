package com.kushagragoel.top_github.ui.repolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.kushagragoel.top_github.R
import com.kushagragoel.top_github.databinding.RepoListFragmentBinding
import com.kushagragoel.top_github.network.model.Item

class RepoListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: RepoListFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.repo_list_fragment, container, false)

        val repoListViewModel: RepoListViewModel = ViewModelProvider(this)
            .get(RepoListViewModel::class.java)

        binding.repoListViewModel = repoListViewModel

        val itemList: MutableList<Item> = ArrayList()

        val adapter = RepoListAdapter(object : RepoListAdapter.IClickListener {
            override fun onRepoItemClick(item: Item, imageView: ImageView) {
                val extras = FragmentNavigatorExtras(
                    imageView to "imageView"
                )
                val bundle = Bundle()
                bundle.putParcelable("repo_item", item)
                this@RepoListFragment.findNavController().navigate(R.id.action_repoListFragment_to_repoDetailFragment, bundle, null, extras)
            }
        }, itemList)

        binding.repoRecyclerView.adapter = adapter

        repoListViewModel.response.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (!it.items.isNullOrEmpty()) {
                    itemList.clear()
                    itemList.addAll(it.items)
                    adapter.notifyDataSetChanged()
                    binding.repoRecyclerView.visibility = View.VISIBLE
                    binding.connectivityImageView.visibility = View.GONE
                } else {
                    binding.repoRecyclerView.visibility = View.GONE
                    binding.connectivityImageView.visibility = View.VISIBLE
                }
            } else {
                binding.repoRecyclerView.visibility = View.GONE
                binding.connectivityImageView.visibility = View.VISIBLE
            }
        })

        binding.button.setOnClickListener {
            if (!binding.editTextTextPersonName.text.isNullOrEmpty()) {
                repoListViewModel.onLangButtonClick(binding.editTextTextPersonName.text.toString())
            }
        }

        repoListViewModel.isApiInProgress.observe(viewLifecycleOwner, Observer {
            binding.apiProgressBar.visibility = if(it == true) View.VISIBLE else View.GONE
        })

        return binding.root
    }

}