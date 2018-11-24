package ro.androidiasi.codecamp.sessions

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ro.androidiasi.codecamp.core.CodecampApi
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by andrei.
 */
class SessionListInteractor(private val api: CodecampApi) {
    fun loadSessions(intent: SessionListContract.Intent.LoadSessionList)
            : Observable<SessionListContract.Result> {
        return  api.getConference("24")
                .map { it.schedules?.get(0)?.sessions ?: listOf() }
                .toObservable()
                .map { SessionListContract.Result.LoadSessionListSuccess(it) }
                .cast(SessionListContract.Result::class.java)
                .startWith(SessionListContract.Result.LoadSessionListLoading())
                .doOnError { Timber.e(it) }
                .onErrorReturn { SessionListContract.Result.LoadSessionListError() }
                .subscribeOn(Schedulers.io())
    }

    fun favoriteSession(intent: SessionListContract.Intent.FavoriteSession)
            : Observable<SessionListContract.Result> {
        return Observable.just(Unit)
                .delay(5, TimeUnit.SECONDS)
                .map { SessionListContract.Result.FavoriteSessionSuccess(intent.sessionId) }
                .cast(SessionListContract.Result::class.java)
                .startWith(SessionListContract.Result.FavoriteSessionLoading(intent.sessionId))
                .subscribeOn(Schedulers.io())
    }
}