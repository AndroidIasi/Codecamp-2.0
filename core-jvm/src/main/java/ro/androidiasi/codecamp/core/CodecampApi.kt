package ro.androidiasi.codecamp.core

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by andrei.
 */
interface CodecampApi {

    @GET("api/AllPartners")
    fun getAllPartners(): Single<List<ApiModel.Partner>>

    @GET("api/Conferences")
    fun getConferences(): Single<List<ApiModel.Conference>>

    @GET("api/Conferences/past")
    fun getPastConferences(): Single<List<ApiModel.Conference>>

    @GET("api/Conferences/{id}")
    fun getConference(@Path("id") conferenceId: String): Single<ApiModel.Conference>
}