package id.qyupee.ui.base

import android.os.Bundle
import android.widget.ImageView
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import id.qyupee.R
import id.qyupee.utils.UserPreference
import org.jetbrains.anko.sdk27.coroutines.onClick

//
// Created by Fathur Radhy 
// on June 2019-06-06.
//

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var userPreference: UserPreference
    private lateinit var loadingDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutResource())

        //UserPreference
        userPreference = UserPreference(
            PreferenceManager
            .getDefaultSharedPreferences(this))

        findViewById<ImageView>(R.id.iv_back)?.onClick {
            finish()
        }

        //loadingDialog
        loadingDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getString(R.string.LOADING))


        initActivity()
        onViewReady()
    }

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
        }

    protected abstract fun setLayoutResource() : Int
    protected abstract fun initActivity()
    protected abstract fun onViewReady()

    protected fun getPreference() : UserPreference {
        return userPreference
    }

    protected fun getLoading() : SweetAlertDialog {
        return loadingDialog
    }
}