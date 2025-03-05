package com.devname.plinjump.di

import com.devname.plinjump.data.GameRepositoryImpl
import com.devname.plinjump.domain.GameRepository
import com.devname.plinjump.presentation.screen.game.view_model.GameViewModel
import com.devname.plinjump.presentation.screen.menu.view_model.MenuViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<GameRepository> { GameRepositoryImpl() }
    viewModel { GameViewModel(get()) }
    viewModel { MenuViewModel(get()) }
}
