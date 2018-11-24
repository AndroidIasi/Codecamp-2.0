package ro.androidiasi.codecamp.sessions

data class SessionItemViewModel (
        val name: String,
        val time: String,
        val location: String,
        val checked: Boolean = false,
        val starLoading: Boolean = false
)