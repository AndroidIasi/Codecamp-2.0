package ro.androidiasi.codecamp.sessiondetails

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ro.androidiasi.codecamp.core.CodecampApi

/**
 * Created by andrei.
 */
class SessionDetailsInteractor(private val api: CodecampApi) {

    fun loadSession(intent: SessionDetailsContract.Intent.LoadSession): Observable<SessionDetailsContract.Result> {
        return api.getConference("24")
                .map { it.schedules?.get(0)?.sessions ?: listOf() }
                .map { it.find { it?.title == intent.sessionId } }
                .toObservable()
                .map { SessionDetailsContract.Result.LoadSessionSuccess(it!!) }
                .cast(SessionDetailsContract.Result::class.java)
                .startWith(SessionDetailsContract.Result.LoadSessionLoading())
                .onErrorReturn { SessionDetailsContract.Result.LoadSessionError() }
                .subscribeOn(Schedulers.io())
    }
}