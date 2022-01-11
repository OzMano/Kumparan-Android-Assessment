package com.kumparan.assessment.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumparan.assessment.databinding.ActivityPostBinding
import com.kumparan.assessment.model.Post
import com.kumparan.assessment.model.State
import com.kumparan.assessment.model.User
import com.kumparan.assessment.ui.base.BaseActivity
import com.kumparan.assessment.ui.post.adapter.CommentListAdapter
import com.kumparan.assessment.ui.user.UserActivity
import com.kumparan.assessment.utils.hide
import com.kumparan.assessment.utils.show
import com.kumparan.assessment.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PostActivity : BaseActivity<PostViewModel, ActivityPostBinding>() {

    override val mViewModel: PostViewModel by viewModels()
    override fun getViewBinding() = ActivityPostBinding.inflate(layoutInflater)

    private val mAdapter = CommentListAdapter()

    private var post = Post()
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Post Details"

        post = intent.getParcelableExtra("post")!!
        user = intent.getParcelableExtra("user")!!

        initView()
        observeUsers()
    }

    private fun initView() {
        mViewBinding.run {
            // set comment adapter
            rvComment.adapter = mAdapter

            rvComment.addItemDecoration(
                DividerItemDecoration(
                    this@PostActivity,
                    LinearLayoutManager.VERTICAL
                )
            )

            postTitle.text = post.title
            postBody.text = post.body
            postAuthor.text = "Posted By ${user.name}"

            postAuthor.setOnClickListener { userDetails() }
        }

        mViewModel.getCommentsByPostId(post.id)
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.comments.collect { state ->
                    when (state) {
                        is State.Loading -> showProgress(true)
                        is State.Success -> {
                            if (state.data.isNotEmpty()) {
                                mAdapter.submitList(state.data.toMutableList())
                            }
                            showProgress(false)
                        }
                        is State.Error -> {
                            showToast(state.message)
                            showProgress(false)
                        }
                    }
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            mViewBinding.progressbar.show()
        } else {
            mViewBinding.progressbar.hide()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun userDetails() {
        val intent = Intent(this, UserActivity::class.java)
            .apply { putExtra("userId", user.id) }
        startActivity(intent)
    }

}