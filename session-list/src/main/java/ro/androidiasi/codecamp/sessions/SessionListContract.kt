package ro.androidiasi.codecamp.sessions

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import ro.androidiasi.codecamp.core.ApiModel
import ro.androidiasi.codecamp.core.Lce

/**
 * Created by andrei.
 */
object SessionListContract {

    interface View : MvpView {

        fun render(state: State)

        fun loadSessions() : Observable<Intent.LoadSessionList>

        fun favoriteSession() : Observable<Intent.FavoriteSession>
    }

    abstract class Presenter : MviBasePresenter<View, State>()

    data class State(
            val sessionListLce: Lce = Lce.IDLE,
            val favoriteSessionLce: Lce = Lce.IDLE,

            val sessionList: List<ApiModel.Session?> = listOf(),
            val favoriteSessionId: String = ""
    ) {

        override fun toString(): String {
            return "State(sessionListLce=$sessionListLce, favoriteSessionLce=$favoriteSessionLce, favoriteSessionId='$favoriteSessionId')"
        }
    }

    interface Intent {

        data class LoadSessionList(private val ignored: Unit) : Intent
        data class FavoriteSession(val sessionId: String) : Intent

    }

    interface Result {

        fun reduce(oldState: State) : State



        class LoadSessionListLoading : Result {
            override fun reduce(oldState: State) : State {
                return oldState.copy(
                        sessionListLce = Lce.LOADING
                )
            }
        }

        class LoadSessionListSuccess(private val sessionList: List<ApiModel.Session?>) : Result {
            override fun reduce(oldState: State) : State {
                return oldState.copy(
                        sessionListLce = Lce.CONTENT,
                        sessionList = this.sessionList
                )
            }
        }

        class LoadSessionListError : Result {
            override fun reduce(oldState: State) : State {
                return oldState.copy(
                        sessionListLce = Lce.ERROR
                )
            }
        }



        class FavoriteSessionLoading(val sessionId: String) : Result {
            override fun reduce(oldState: State) : State {
                return oldState.copy(
                        favoriteSessionLce = Lce.LOADING,
                        favoriteSessionId = sessionId
                )
            }
        }

        class FavoriteSessionSuccess(val sessionId: String) : Result {
            override fun reduce(oldState: State) : State {
                return oldState.copy(
                        favoriteSessionLce = Lce.CONTENT,
                        favoriteSessionId = sessionId
                )
            }
        }

        class FavoriteSessionError(val sessionId: String) : Result {
            override fun reduce(oldState: State) : State {
                return oldState.copy(
                        favoriteSessionLce = Lce.ERROR,
                        favoriteSessionId = sessionId
                )
            }

        }

    }

}