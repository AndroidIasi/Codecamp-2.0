package ro.androidiasi.codecamp.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

object ApiModel {

    @JsonClass(generateAdapter = true)
    data class Conference(
        @Json(name = "refId") val refId: Int? = null,
        @Json(name = "title") val title: String? = null,
        @Json(name = "startDate") val startDate: String? = null,
        @Json(name = "endDate") val endDate: String? = null,
        @Json(name = "venue") val venue: Venue? = null,
        @Json(name = "sponsorshipPackages") val sponsorshipPackages: List<SponsorshipPackage>? = null,
        @Json(name = "sponsors") val sponsors: List<Sponsor>? = null,
        @Json(name = "schedules") val schedules: List<Schedule>? = null,
        @Json(name = "speakers") val speakers: List<Speaker>? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class Venue(
        @Json(name = "name") val name: String? = null,
        @Json(name = "city") val city: String? = null,
        @Json(name = "country") val country: String? = null,
        @Json(name = "directions") val directions: String? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class Sponsor(
        @Json(name = "name") val name: String? = null,
        @Json(name = "logoUrl") val logoUrl: String? = null,
        @Json(name = "websiteUrl") val websiteUrl: String? = null,
        @Json(name = "sponsorshipPackage") val sponsorshipPackage: String? = null,
        @Json(name = "displayOrder") val displayOrder: Int? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class Schedule(
        @Json(name = "date") val date: String? = null,
        @Json(name = "timeSlots") val timeSlots: List<TimeSlot?>? = null,
        @Json(name = "tracks") val tracks: List<Track?>? = null,
        @Json(name = "sessions") val sessions: List<Session?>? = null
    )

    @JsonClass(generateAdapter = true)
    data class TimeSlot(
        @Json(name = "startTime") val startTime: String? = null,
        @Json(name = "endTime") val endTime: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Session(
        @Json(name = "startTime") val startTime: String? = null,
        @Json(name = "endTime") val endTime: String? = null,
        @Json(name = "allTracks") val allTracks: Boolean? = null,
        @Json(name = "track") val track: String? = null,
        @Json(name = "title") val title: String? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "speakers") val speakers: List<String>? = null,
        @Json(name = "speakingLang") val speakingLang: String? = null,
        @Json(name = "level") val level: String? = null,
        @Json(name = "presentationFileUrl") val presentationFileUrl: String? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class Track(
        @Json(name = "name") val name: String? = null,
        @Json(name = "capacity") val capacity: Int? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "displayOrder") val displayOrder: Int? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class SponsorshipPackage(
        @Json(name = "name") val name: String? = null,
        @Json(name = "displayOrder") val displayOrder: Int? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class Speaker(
        @Json(name = "name") val name: String? = null,
        @Json(name = "photoUrl") val photoUrl: String? = null,
        @Json(name = "company") val company: String? = null,
        @Json(name = "companyWebsiteUrl") val companyWebsiteUrl: String? = null,
        @Json(name = "jobTitle") val jobTitle: String? = null,
        @Json(name = "bio") val bio: String? = null,
        @Json(name = "displayOrder") val displayOrder: Int? = null,
        @Json(name = "twitter") val twitter: String? = null,
        @Json(name = "facebook") val facebook: String? = null,
        @Json(name = "linkedIn") val linkedIn: String? = null
    ) {
        companion object
    }

    @JsonClass(generateAdapter = true)
    data class Partner(
        @Json(name = "name") val name: String? = null,
        @Json(name = "logoFileName") val logoFileName: String? = null,
        @Json(name = "websiteUrl") val websiteUrl: String? = null,
        @Json(name = "displayOrder") val displayOrder: Int? = null
    )
}
