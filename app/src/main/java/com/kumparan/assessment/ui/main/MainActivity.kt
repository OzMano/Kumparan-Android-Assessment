package com.kumparan.assessment.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kumparan.assessment.R
import com.kumparan.assessment.databinding.ActivityMainBinding
import com.kumparan.assessment.model.Post
import com.kumparan.assessment.model.State
import com.kumparan.assessment.model.User
import com.kumparan.assessment.ui.base.BaseActivity
import com.kumparan.assessment.ui.main.adapter.PostListAdapter
import com.kumparan.assessment.utils.*
import com.shreyaspatil.MaterialDialog.MaterialDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val mViewModel: MainViewModel by viewModels()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private val mAdapter = PostListAdapter(this::onItemClicked)

    companion object {
        var userList = listOf<User>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        initView()
        observeUsers()
    }

    override fun onStart() {
        super.onStart()
        handleNetworkChanges()
    }

    private fun initView() {
        mViewBinding.run {
            // set post adapter
            rvPost.adapter = mAdapter

            swipeRefreshLayout.setOnRefreshListener { mViewModel.getAllUser() }
        }
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mViewModel.users.collect { state ->
                        when (state) {
                            is State.Loading -> showLoading(true)
                            is State.Success -> {
                                showLoading(false)
                                userList = state.data
                                mViewModel.getPosts()
                            }
                            is State.Error -> {
                                showToast(state.message)
                                showLoading(false)
                            }
                        }
                    }
                }

                launch {
                    mViewModel.posts.collect { state ->
                        when (state) {
                            is State.Loading -> showLoading(true)
                            is State.Success -> {
                                if (state.data.isNotEmpty()) {
                                    mAdapter.submitList(state.data.toMutableList())
                                }
                                showLoading(false)
                            }
                            is State.Error -> {
                                showToast(state.message)
                                showLoading(false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        mViewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    /**
     * observe perubahan koneksi internet
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                mViewBinding.textViewNetworkStatus.text =
                    getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (mAdapter.itemCount == 0) mViewModel.getAllUser()

                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(1000L)
                        .setDuration(1000L)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        }
    }

    override fun onBackPressed() {
        // menampilkan confirmation dialog saat tekan button back
        MaterialDialog.Builder(this)
            .setTitle(getString(R.string.exit_dialog_title))
            .setMessage(getString(R.string.exit_dialog_message))
            .setPositiveButton(getString(R.string.option_yes)) { dialogInterface, _ ->
                dialogInterface.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton(getString(R.string.option_no)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .build()
            .show()
    }

    private fun onItemClicked(post: Post, user: User) {
//        val intent = Intent(this, PostDetailsActivity::class.java)
//            .apply {
//                putExtra("post", post)
//                putExtra("user", user)
//            }
//        startActivity(intent)
    }
}