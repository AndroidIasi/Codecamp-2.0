package ro.androidiasi.codecamp.sessiondetails

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import ro.androidiasi.codecamp.core.ApiModel
import ro.androidiasi.codecamp.core.Lce

/**
 * Created by andrei.
 */
object SessionDetailsContract {

    interface View : MvpView {

        fun render(state: State)

        fun loadSession(): Observable<Intent.LoadSession>

    }

    abstract class Presenter : MviBasePresenter<View, State>()

    data class State(
            val sessionLce: Lce = Lce.IDLE,
            val session: ApiModel.Session = ApiModel.Session()
    )

    interface Intent {

        data class LoadSession(val sessionId: String) : Intent

    }

    interface Result {

        fun reduce(oldState: State) : State

        class LoadSessionLoading : Result {
            override fun reduce(oldState: State): State {
                return oldState.copy(
                        sessionLce = Lce.LOADING
                )
            }
        }

        class LoadSessionSuccess(private val session: ApiModel.Session) : Result {
            override fun reduce(oldState: State): State {
                return oldState.copy(
                        sessionLce = Lce.CONTENT,
                        session = this.session
                )
            }
        }

        class LoadSessionError : Result {
            override fun reduce(oldState: State): State {
                return oldState.copy(
                        sessionLce = Lce.ERROR
                )
            }
        }

    }

}