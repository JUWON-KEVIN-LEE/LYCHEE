package com.lychee.data.core.repository

import com.lychee.data.core.model.Expense
import io.realm.RealmResults

interface ExpenseRepository {

    fun getExpenses(): RealmResults<Expense>

    fun queryExpenses(field:String, value: Any): RealmResults<Expense>

    fun saveExpense(expense: Expense): RealmResults<Expense>
}