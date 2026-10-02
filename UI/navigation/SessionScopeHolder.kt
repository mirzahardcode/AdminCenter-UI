package com.mirzadev.admincenter.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class SessionScopeHolder : ViewModel() {

    private class Owner(override val viewModelStore: ViewModelStore) : ViewModelStoreOwner

    private var uid: String? = null
    private var owner: Owner? = null

    fun ownerFor(uid: String): ViewModelStoreOwner {
        val current = owner
        if (current != null && this.uid == uid) return current
        clear()
        val created = Owner(ViewModelStore())
        owner = created
        this.uid = uid
        return created
    }

    fun clear() {
        owner?.viewModelStore?.clear()
        owner = null
        uid = null
    }

    override fun onCleared() {
        clear()
    }
}
