package com.mirzadev.admincenter.ui.navigation

import android.net.Uri

object Routes {
    const val DASHBOARD = "dashboard"
    const val USERS = "users"
    const val SETTINGS = "settings"
    const val ADD_USER = "add_user"

    const val ARG_UID = "uid"
    const val USER_DETAIL = "user/{$ARG_UID}"

    fun userDetail(uid: String): String = "user/${Uri.encode(uid)}"
}
