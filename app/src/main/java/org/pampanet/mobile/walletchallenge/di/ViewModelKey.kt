package org.pampanet.mobile.walletchallenge.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by pablo.biagioli on 12/03/2018.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)