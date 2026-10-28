package com.example.firsttry

import android.app.Application
import com.example.firsttry.di.AppContainer

class CatalogApplication : Application() {
    val container by lazy { AppContainer() }
}
