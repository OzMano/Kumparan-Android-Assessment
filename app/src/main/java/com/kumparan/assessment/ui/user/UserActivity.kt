package com.kumparan.assessment.ui.user

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.kumparan.assessment.R
import com.kumparan.assessment.databinding.ActivityUserBinding
import com.kumparan.assessment.model.Photo
import com.kumparan.assessment.model.State
import com.kumparan.assessment.model.User
import com.kumparan.assessment.ui.base.BaseActivity
import com.kumparan.assessment.ui.user.adapter.PhotoListAdapter
import com.kumparan.assessment.utils.hide
import com.kumparan.assessment.utils.show
import com.kumparan.assessment.utils.showToast
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserActivity : BaseActivity<UserViewModel, ActivityUserBinding>() {

    override val mViewModel: UserViewModel by viewModels()
    override fun getViewBinding() = ActivityUserBinding.inflate(layoutInflater)

    private val mAdapter = PhotoListAdapter(this, this::onItemClicked)

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "User Details"

        initView()
        observeUsers()
    }

    private fun initView() {
        mViewBinding.run {
            // set album adapter
            rvAlbum.adapter = mAdapter

            val mLayoutManager = GridLayoutManager(this@UserActivity, 4)
            rvAlbum.layoutManager = mLayoutManager

            mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (mAdapter.getItemViewType(position)) {
                        1 -> 4
                        else -> 1
                    }
                }
            }
        }

        val userId = intent.getIntExtra("userId", 0)
        mViewModel.getUserById(userId)
        mViewModel.getUserAlbum(userId)
    }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mViewModel.user.collect { state ->
                        when (state) {
                            is State.Loading -> {}
                            is State.Success -> {
                                user = state.data

                                mViewBinding.apply {
                                    userName.text = user.name
                                    userEmail.text = user.email
                                    userAddress.text = user.address.street
                                    userCompany.text = user.company.name
                                }
                            }
                            is State.Error -> {
                                showToast(state.message)
                            }
                        }
                    }
                }

                launch {
                    mViewModel.albums.collect { state ->
                        when (state) {
                            is State.Loading -> showProgress(true)
                            is State.Success -> {
                                if (state.data.isNotEmpty()) {
                                    mAdapter.addData(state.data.toMutableList())
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

    private fun onItemClicked(photo: Photo) {
        val overlayView = layoutInflater.inflate(R.layout.photo_overlay, null)
        val photoTitle = overlayView.findViewById<TextView>(R.id.photo_title)
        photoTitle.text = photo.title

        val builder = StfalconImageViewer.Builder(this, listOf(photo.url)) { imageView, image ->
            imageView.load(image) {
                placeholder(R.drawable.ic_photo)
                error(R.drawable.ic_broken_image)
            }
        }

        builder.withHiddenStatusBar(true)
        builder.allowSwipeToDismiss(true)
        builder.allowZooming(true)
        builder.withOverlayView(overlayView)

        builder.show()
    }
}