package space.rodionov.porosenokpetr.feature_driller.presentation.base

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> Fragment.viewBinding() =
        FragmentBindingDelegate(T::class.java)

class FragmentBindingDelegate<T: ViewBinding>(
    bindingClass: Class<T>
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    private val bindMethod by lazy {
        bindingClass.getMethod("bind", View::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        // if exist a binding reference return
        binding?.let { return it }

        // add lifecycle callback -> onDestroy() remove binding
        thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {

            @Suppress("unused")
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                binding = null
            }

        })

        // bind binding given an already inflated view
        val invoke = bindMethod.invoke(null, thisRef.requireView()) as T
        return invoke.also { binding -> this.binding = binding }
    }
}