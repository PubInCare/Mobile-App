package com.dicoding.picodiploma.pubincare.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.pubincare.R
import com.dicoding.picodiploma.pubincare.TouristActivity
import com.dicoding.picodiploma.pubincare.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.pubincare.ui.form.FormActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupAction()
//        setupRvAdapter()
//        setupObserver()
    }

//    override fun onResume() {
//        super.onResume()
//        binding?.rvStory?.scrollToPosition(0)
//    }
//
//    private fun setupRvAdapter() {
//        listStoryAdapter = ListStoryAdapter()
//
//        val layoutManager = LinearLayoutManager(activity)
//        binding?.rvStory?.layoutManager = layoutManager
//
//        listStoryAdapter.addLoadStateListener { loadState ->
//            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
//                binding?.tvPlaceholder?.text = resources.getString(R.string.not_found)
//                binding?.tvPlaceholder?.isVisible = true
//                binding?.rvStory?.isVisible = false
//            } else {
//                binding?.tvPlaceholder?.isVisible = false
//                binding?.rvStory?.isVisible = true
//            }
//        }
//
//        binding?.rvStory?.apply {
//            this.adapter = listStoryAdapter.withLoadStateFooter(
//                footer = LoadingStateAdapter {
//                    listStoryAdapter.retry()
//                }
//            )
//            postponeEnterTransition()
//            viewTreeObserver.addOnPreDrawListener {
//                startPostponedEnterTransition()
//                true
//            }
//        }
//    }
//
//    private fun setupObserver() {
//        viewModel.user.observe(viewLifecycleOwner) { user ->
//            viewModel.getPagedStories(user.token).observe(viewLifecycleOwner) {
//                listStoryAdapter.submitData(lifecycle, it)
//            }
//        }
//    }
//
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }
//
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                val dialogBuilder = AlertDialog.Builder(requireActivity())
                dialogBuilder.setMessage(R.string.message_logout_dialog)
                dialogBuilder.setPositiveButton(R.string.logout) { _, _ ->
                    viewModel.logout()
                }
                dialogBuilder.setNegativeButton(R.string.cancel) { _, _ -> }
                dialogBuilder.show()
                true
            }
            else -> NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) || super.onOptionsItemSelected(item)
        }
    }
//
    private fun setupAction() {
        binding?.btnPelaporan?.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, FormActivity::class.java))
            }
        }

        binding?.btnTuris?.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, TouristActivity::class.java))
            }
        }
    }
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}