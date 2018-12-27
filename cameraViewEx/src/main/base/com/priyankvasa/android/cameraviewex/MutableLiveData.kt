/*
 * Copyright 2018 Priyank Vasa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.priyankvasa.android.cameraviewex

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

internal class MutableLiveData<T>(
        initialValue: T,
        val defaultValue: T = initialValue
) : MutableLiveData<T>() {

    internal var value: T = initialValue
        get() = super.getValue() ?: defaultValue
        set(value) {
            if (field == value) return
            lastValue = field
            field = value
            super.setValue(value)
        }

    init {
        value = initialValue
    }

    private var lastValue: T = initialValue

    internal fun revert() {
        value = lastValue
    }

    internal fun observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
        super.observe(owner, androidx.lifecycle.Observer { observer(it ?: defaultValue) })
    }
}