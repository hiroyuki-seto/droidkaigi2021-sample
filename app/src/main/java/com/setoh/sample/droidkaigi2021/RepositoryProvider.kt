package com.setoh.sample.droidkaigi2021

import androidx.annotation.RestrictTo

object RepositoryProvider {

    var repository: Repository = Repository()
        @RestrictTo(RestrictTo.Scope.TESTS)
        set

    @RestrictTo(RestrictTo.Scope.TESTS)
    fun reset() {
        repository = Repository()
    }
}