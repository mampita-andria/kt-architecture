package mg.socolait.presentation.activity

import android.app.ProgressDialog
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login.*
import mg.socolait.R
import mg.socolait.common.extension.hideSoftKeyboard
import mg.socolait.common.extension.isConnected
import mg.socolait.common.extension.showToast
import mg.socolait.data.dto.request.LoginDto
import mg.socolait.service.applying.db.product.AdministerProductSA
import mg.socolait.service.applying.db.product.AdministerProductSAImpl
import mg.socolait.service.applying.ws.authentification.AuthentificationSA
import mg.socolait.service.applying.ws.authentification.AuthentificationSAImpl
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity


/**
 * Created by mampita_andria on 15/11/2017.
 */
@EActivity(R.layout.activity_login)
open class LoginActivity : GenericActivity() {

    @Bean(AuthentificationSAImpl::class)
    lateinit var authentificationSA: AuthentificationSA

    @Bean(AdministerProductSAImpl::class)
    lateinit var administerProduct: AdministerProductSA

    private lateinit var progressDialog: ProgressDialog

    @AfterViews
    fun initialize() {
        progressDialog = ProgressDialog(this)
        with(progressDialog) {
            setCancelable(false)
            isIndeterminate = true
            setMessage(getString(R.string.loading))
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    @Click(R.id.connect)
    open fun connect() {
        hideSoftKeyboard()
        if (isConnected()) {
            authenticate()
        } else {
            showToast(false, getString(R.string.device_not_connected))
        }
    }

    private fun authenticate() {

        if (isValid) {
            val loginDto = LoginDto().apply {
                login = this@LoginActivity.login.text.toString()
                password = this@LoginActivity.password.text.toString()
            }
            authentificationSA.authenticate(
                    params = loginDto,
                    before = {
                        progressDialog.show()
                    },
                    success = { token ->
                        userToken = token
                        MainActivity_.intent(this).start()
                        finish()
                    },
                    failure = { error ->
                        showToast(false, error)
                    },
                    done = {
                        progressDialog.dismiss()
                    }
            )
        } else {
            showToast(false, getString(R.string.empty_field))
        }
    }

    private var isValid: Boolean = false
        get() {

            if (login.text.isEmpty()) {
                login.error = ""
                return false
            } else {
                login.setError("", ContextCompat.getDrawable(this, R.drawable.input_error))
            }

            if (password.text.isEmpty()) {

                password.error = ""
                return false
            } else {
                password.setError("", ContextCompat.getDrawable(this, R.drawable.input_error))
            }
            return true
        }
}