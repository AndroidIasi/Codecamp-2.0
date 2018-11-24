package ro.androidiasi.codecamp.sessiondetails

import io.reactivex.android.schedulers.AndroidSchedulers
import ro.androidiasi.codecamp.core.Lce
import timber.log.Timber

/**
 * Created by andrei.
 */
class SessionDetailsPresenter(private val interactor: SessionDetailsInteractor)
    : SessionDetailsContract.Presenter() {

    override fun bindIntents() {

        val loadSession = intent { it.loadSession() }
                .doOnNext { Timber.i("intent: $it") }
                .flatMap { interactor.loadSession(it) }

        val initialState = SessionDetailsContract.State()

        val allIntents = loadSession
                .scan(initialState) { oldState, result ->
                val cleanState = oldState.copy(
                sessionLce = Lce.IDLE
        )
            result.reduce(cleanState)
        }
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents) { view, viewState -> view.render(viewState) }
    }

}
