package ro.androidiasi.codecamp.sessions

import ro.androidiasi.codecamp.core.ApiInjector

/**
 * Created by andrei.
 */
object SessionListInjector {

    fun injectPresenter() : SessionListContract.Presenter {
        val api = ApiInjector.injectApi()
        val interactor = SessionListInteractor(api)
        return SessionListPresenter(interactor)
    }

}