package com.lychee.data.common.util

import android.arch.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmModel
import io.realm.RealmResults

class RealmResultsLiveData<T: RealmModel> constructor(
        private val realmResults: RealmResults<T>
): LiveData<RealmResults<T>>() {

    private val mListener = RealmChangeListener<RealmResults<T>> { results -> value = results }

    override fun onActive() {
        if(realmResults.isValid) {
            realmResults.addChangeListener(mListener)
        }
    }

    override fun onInactive() {
        if(realmResults.isValid) {
            realmResults.removeChangeListener(mListener)
        }
    }
}