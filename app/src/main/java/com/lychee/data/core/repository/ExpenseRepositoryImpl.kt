package com.lychee.data.core.repository

import com.lychee.data.core.model.Expense
import com.lychee.di.scope.ApplicationScoped
import com.lychee.mock.MockData
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

@ApplicationScoped
class ExpenseRepositoryImpl @Inject constructor(
        /* expenseDao */
) : ExpenseRepository {

    private val _expenses = Realm.getDefaultInstance().where(Expense::class.java).findAllAsync()

    private val expenses = MockData.expenses

    override fun getExpenses(): RealmResults<Expense> {
        return Realm.getDefaultInstance()
                .where(Expense::class.java)
                .findAllAsync()
    }

    override fun queryExpenses(field: String, value: Any): RealmResults<Expense> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveExpense(expense: Expense): RealmResults<Expense> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}