package id.qyupee.ui.auth

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.ybq.android.spinkit.SpinKitView
import com.segamedev.bandros.BFormControl
import com.segamedev.bandros.BFormModel
import id.qyupee.MainActivity
import id.qyupee.R
import id.qyupee.ui.base.BaseActivity
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AuthActivity : BaseActivity() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var tvBtn: TextView
    private lateinit var spinKit: SpinKitView
    private lateinit var rvLogin: RelativeLayout
    private lateinit var phone: EditText
    private lateinit var pass: EditText

    override fun setLayoutResource(): Int = R.layout.activity_auth

    override fun initActivity() {
        tvBtn = findViewById(R.id.tv_btn)
        spinKit = findViewById(R.id.spin_kit)
        rvLogin = findViewById(R.id.rv_login)
        phone = findViewById(R.id.et_phone)
        pass = findViewById(R.id.et_password)

        pass.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> checkAndLogin()
                }
                false
            }
        }

        rvLogin.onClick { checkAndLogin() }
    }

    override fun onViewReady() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory { AuthViewModel(this@AuthActivity) })
                .get(AuthViewModel::class.java)

        viewModel.status.observe(this, Observer { status ->
            loading(status)
        })

        viewModel.data.observe(this, Observer { user ->
            user.data?.let { getPreference().storeData(it) }
            startActivity<MainActivity>()
            finish()
        })

    }

    private fun loading(isLoading: Boolean = true) {
        if (isLoading) {
            tvBtn.visibility = View.GONE
            spinKit.visibility = View.VISIBLE
            rvLogin.isClickable = false
        } else {
            tvBtn.visibility = View.VISIBLE
            spinKit.visibility = View.GONE
            rvLogin.isClickable = true
        }
    }

    private fun checkAndLogin() {
        BFormControl().init(this)
            .addForm(BFormModel(phone, getString(R.string.phone), 6))
            .addForm(BFormModel(pass, getString(R.string.password), 3))
            .listener(object : BFormControl.OnFormControlListener{
                override fun onFailed(errorModel: BFormModel, msg: String) {
                    //Return error log on snack bar
                    toast(msg)
                }

                override fun onSuccess() {
                    //Do Login Here
                    viewModel.login(phone.text.toString(), pass.text.toString())
                }
            })
            .check()
    }
}
