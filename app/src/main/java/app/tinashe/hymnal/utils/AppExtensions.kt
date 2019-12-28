/*
 * Copyright (c) 2019. Tinashe Mzondiwa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package app.tinashe.hymnal.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.tinashe.hymnal.di.ViewModelFactory
import java.util.Random

inline fun <reified T : ViewModel> getViewModel(activity: FragmentActivity, factory: ViewModelFactory): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}

inline fun <reified T : ViewModel> getViewModel(activity: Fragment, factory: ViewModelFactory): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun RecyclerView.vertical() {
    this.layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.horizontal() {
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun inflateView(@LayoutRes layoutResId: Int, parent: ViewGroup, attachToRoot: Boolean): View =
        LayoutInflater.from(parent.context).inflate(layoutResId, parent, attachToRoot)

fun String.toColor() = Color.parseColor(this)

fun Drawable.tint(color: Int) {
    DrawableCompat.wrap(this)
    DrawableCompat.setTint(this, color)
    DrawableCompat.unwrap<Drawable>(this)
}

/**
 * Returns a random element.
 */
fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null