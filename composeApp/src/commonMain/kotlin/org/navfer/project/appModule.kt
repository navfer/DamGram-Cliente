package org.navfer.project

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.navfer.project.post.PostRepository
import org.navfer.project.user.UserRepository

val appModule = module {
    single { UserRepository() }
    single { PostRepository() }
    viewModel { AppViewModel(get(),get()) }
}