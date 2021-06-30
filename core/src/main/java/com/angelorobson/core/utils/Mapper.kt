package com.angelorobson.core.utils

interface Mapper<S, T> {
    fun map(source: S): T
}