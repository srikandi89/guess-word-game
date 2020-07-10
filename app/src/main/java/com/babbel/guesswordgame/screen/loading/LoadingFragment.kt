package com.babbel.guesswordgame.screen.loading


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.babbel.guesswordgame.R
import com.babbel.guesswordgame.datasource.remote.RemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoadingFragment : Fragment(R.layout.fragment_loading) {
    private lateinit var viewModel: LoadingViewModel
    private lateinit var remoteRepository: RemoteRepository
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        remoteRepository = RemoteRepository(requireContext())
        viewModel = LoadingViewModel(remoteRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = viewModel
            .getEngSpaPairs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                // TODO: Jump to game fragment here
            }, { throws ->
                Log.d(TAG, "Error ${throws.message}")
            })

        disposable.add(result)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }

    companion object {
        const val TAG = "LOADING_FRAGMENT"
    }
}