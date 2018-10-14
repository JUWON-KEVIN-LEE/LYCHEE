package com.lychee.data.core.model

import io.realm.RealmObject

open class Category (
        var id: Int = 0,
        var category: String? = null,
        var image: String? = null
): RealmObject()