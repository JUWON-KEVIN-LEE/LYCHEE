package com.lychee.data.repository

import com.lychee.data.model.Expenditure
import io.reactivex.Flowable

/**
 * TODO
 * MUTABLE LIST
 */
interface ExpenditureRepository {

    fun getAll() : Flowable<List<Expenditure>>

    fun getRecent(size : Int) : Flowable<List<Expenditure>>
}