package id.qyupee.ui.base

import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.qyupee.MainActivity
import id.qyupee.utils.UserPreference
import id.qyupee.R
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

//
// Created by Fathur Radhy 
// on May 2019-05-29.
//
abstract class BaseFragment : Fragment() {
    private lateinit var userPreference: UserPreference

    private var mView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mView == null) {
            mView = inflater.inflate(setLayoutResource(), container, false)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //UserPreference
        userPreference = UserPreference(PreferenceManager.getDefaultSharedPreferences(ctx))

//        mView?.findViewById<ImageView>(R.id.iv_back)?.onClick {
//            findNavController().navigateUp()
//        }
//
        mView?.findViewById<ImageView>(R.id.iv_menu)?.onClick {
            (context as MainActivity).showDrawer()
        }

        initFragment()
        onViewReady()
    }

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

    protected abstract fun setLayoutResource(): Int
    protected abstract fun initFragment()
    protected abstract fun onViewReady()

    protected fun getPreference(): UserPreference {
        return userPreference
    }

    protected fun getRootView(): View? {
        return mView
    }
}