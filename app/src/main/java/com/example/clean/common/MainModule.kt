package com.example.clean.common

import com.example.clean.mainModel.model.DataSource
import com.example.clean.mainModel.model.DataSourceImpl
import com.example.clean.mainModel.model.MainRepository
import com.example.clean.mainModel.model.MainRepositoryImpl
import com.example.clean.mainModel.presenter.MainPresenter
import com.example.clean.mainModel.presenter.MainPresenterImpl
import com.example.clean.mainModel.view.MainView
import com.example.clean.mainModel.view.OnClickListener
import com.example.clean.mainModel.view.ResultAdapter
import org.koin.dsl.module

/****
 * single: tiene que ver con 1 única instancia, sería cómo simular el patró singleton
 * esto se recomienda para base de datos o para aquellas clases que únicamente requieren una
 * instancia para poder tener una coherencia de datos dentro de la aplicación y por último también
 * poder añadir un extra con las clases que van a estar vivas toda la aplicación
 *
 * factory: para aquellas que no importa si se puede repetir las instancias o que mayormente pueden ser
 * reutilizadas o creadas y eliminadas durante el ciclo de vida de android, todas aquellas van a ir acá
 *
 * RESUMEN:
 * single va crear la instancia una sola vez y la mantendrá mientras la aplicación esté activa, mientras
 * que factory va crear las instancias siempre que se reqieran de forma separada y siempre que se requieran
 */

fun provideMainRepository(dataSource: DataSource): MainRepository = MainRepositoryImpl(dataSource)
fun provideDataSource(): DataSource = DataSourceImpl()

val mainModule = module {
    factory<ResultAdapter> { (listener: OnClickListener) -> ResultAdapter(listener) }

    single { provideDataSource() }
    single { provideMainRepository(get()) }
    factory<MainPresenter> { (view: MainView) -> MainPresenterImpl(view, get()) }
}