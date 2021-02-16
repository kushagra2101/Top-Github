package com.kushagragoel.top_github.ui.repolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.kushagragoel.top_github.hideKeyboard
import com.kushagragoel.top_github.isNetworkAvailable
import com.kushagragoel.top_github.network.model.Item

class RepoListFragment : Fragment() {

    private lateinit var binding: RepoListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.repo_list_fragment, container, false)

        val repoListViewModel: RepoListViewModel = ViewModelProvider(this)
            .get(RepoListViewModel::class.java)

        binding.repoListViewModel = repoListViewModel

        val itemList: MutableList<Item> = ArrayList()

        val adapter = RepoListAdapter(object : RepoListAdapter.IClickListener {
            override fun onRepoItemClick(item: Item, imageView: ImageView) {
                val extras = FragmentNavigatorExtras(
                    imageView to imageView.transitionName
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

        binding.editTextTextPersonName.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.button.isEnabled = p0?.length?.compareTo(0)?:0 > 0
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.button.setOnClickListener {
            hideKeyboard()
            if (it.isNetworkAvailable()) {
                repoListViewModel.onLangButtonClick(binding.editTextTextPersonName.text.toString())
            } else {
                binding.repoRecyclerView.visibility = View.GONE
                binding.connectivityImageView.visibility = View.VISIBLE
            }
        }

        repoListViewModel.isApiInProgress.observe(viewLifecycleOwner, Observer {
            binding.apiProgressBar.visibility = if(it == true) View.VISIBLE else View.GONE
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.repoRecyclerView.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

}