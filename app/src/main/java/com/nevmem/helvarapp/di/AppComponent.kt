package com.nevmem.helvarapp.di

import com.nevmem.helvarapp.PswdActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WifiModule::class])
interface AppComponent {
    fun inject(pswdActivity: PswdActivity)
}