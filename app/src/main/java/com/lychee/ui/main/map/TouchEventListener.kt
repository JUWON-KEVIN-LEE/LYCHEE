package com.lychee.ui.main.map

interface TouchEventListener {

    fun moveUp(per : Float)

    fun moveDown(per : Float)

    fun expand()

    fun shrink()
}