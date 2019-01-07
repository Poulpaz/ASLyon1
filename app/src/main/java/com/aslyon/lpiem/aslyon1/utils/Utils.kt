package com.aslyon.lpiem.aslyon1.utils

fun <T> T?.or(default: T): T = this ?: default
fun <T> T?.or(compute: () -> T): T = this ?: compute()