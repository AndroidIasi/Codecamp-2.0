package ro.androidiasi.codecamp.sessions

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ro.androidiasi.codecamp.core.Lce
import ro.androidiasi.codecamp.sessions.databinding.ActivitySessionListBinding

/**
 * Created by andrei.
 */
class SessionListActivity
    : MviActivity<SessionListContract.View, SessionListContract.Presenter>(),
        SessionListContract.View {

    private lateinit var binding: ActivitySessionListBinding
    private lateinit var adapter: SessionListAdapter

    private val loadSessionsSubject = PublishSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.adapter = SessionListAdapter(this) {
//            SessionDetailsActivity.start(this, it)
        }
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_session_list)
        this.binding.recycler.layoutManager = LinearLayoutManager(this)
        this.binding.recycler.adapter = adapter

        this.binding.swipeToRefresh.setOnRefreshListener { loadSessionsSubject.onNext(Unit) }
    }

    override fun onResume() {
        super.onResume()
        loadSessionsSubject.onNext(Unit)
    }

    override fun onDestroy() {
        super.onDestroy()
        loadSessionsSubject.onComplete()
    }

    override fun createPresenter(): SessionListContract.Presenter {
        return SessionListInjector.injectPresenter()
    }

    override fun loadSessions(): Observable<SessionListContract.Intent.LoadSessionList> {
        return loadSessionsSubject
                .map { SessionListContract.Intent.LoadSessionList(Unit) }
    }

    override fun favoriteSession(): Observable<SessionListContract.Intent.FavoriteSession> {
        return adapter.favoriteSubject
                .map { SessionListContract.Intent.FavoriteSession(it) }
    }

    override fun render(state: SessionListContract.State) {
        renderSessionList(state)
        renderFavoriteSession(state)
    }

    private fun renderFavoriteSession(state: SessionListContract.State) {
        when (state.favoriteSessionLce) {
            Lce.LOADING -> {
                adapter.updateToggleLoading(state.favoriteSessionId, true)
            }
            Lce.CONTENT -> {
                adapter.updateToggle(state.favoriteSessionId)
                adapter.updateToggleLoading(state.favoriteSessionId, false)
            }
            Lce.ERROR -> {
                Snackbar.make(binding.root, "Server error :)", Snackbar.LENGTH_SHORT).show()
                adapter.updateToggleLoading(state.favoriteSessionId, false)
            }
            Lce.IDLE -> { }
        }
    }

    private fun renderSessionList(state: SessionListContract.State) {
        when (state.sessionListLce) {
            Lce.LOADING -> {
                binding.swipeToRefresh.isRefreshing = true
            }
            Lce.CONTENT -> {
                val sessions = state.sessionList.map {
                    val name = it?.title ?: ""
                    val time = "${it?.startTime?.removeSuffix(":00")} - ${it?.endTime?.removeSuffix(":00")}"
                    SessionItemViewModel(name, time, "Some room", false)
                }
                adapter.update(sessions)
                binding.swipeToRefresh.isRefreshing = false
            }
            Lce.ERROR -> {
                Snackbar.make(binding.root, "Server error :)", Snackbar.LENGTH_SHORT).show()
                binding.swipeToRefresh.isRefreshing = false
            }
            Lce.IDLE -> { }
        }
    }

}