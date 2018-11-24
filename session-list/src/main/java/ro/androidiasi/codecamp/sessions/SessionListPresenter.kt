package ro.androidiasi.codecamp.sessions

import io.reactivex.android.schedulers.AndroidSchedulers
import ro.androidiasi.codecamp.core.Lce
import timber.log.Timber

/**
 * Created by andrei.
 */
class SessionListPresenter(private val interactor: SessionListInteractor)
    : SessionListContract.Presenter(){

    override fun bindIntents() {

        val loadSessionIntent = intent { it.loadSessions() }
                .doOnNext { Timber.i("intent: $it") }
                .flatMap { interactor.loadSessions(it) }

        val favoriteSession = intent { it.favoriteSession() }
                .doOnNext { Timber.i("intent: $it") }
                .flatMap { interactor.favoriteSession(it) }

        val initialState = SessionListContract.State()

        val allIntents = loadSessionIntent
                .mergeWith(favoriteSession)
                .scan(initialState) { oldState, result ->
                    val cleanLceState = oldState.copy(
                            sessionListLce = Lce.IDLE,
                            favoriteSessionLce = Lce.IDLE
                    )
                    result.reduce(cleanLceState)
                }
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents) { view, viewState ->
            Timber.i("render: $viewState")
            view.render(viewState)
        }
    }


}