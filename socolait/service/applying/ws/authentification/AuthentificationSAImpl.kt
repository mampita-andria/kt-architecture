package mg.socolait.service.applying.ws.authentification

import android.content.Context
import mg.socolait.R
import mg.socolait.common.BeforeClosure
import mg.socolait.common.DoneClosure
import mg.socolait.common.FailureClosure
import mg.socolait.common.SuccessClosure
import mg.socolait.common.utils.UrlManager
import mg.socolait.data.dto.request.LoginDto
import mg.socolait.data.dto.response.login.LoginResponseDto
import mg.socolait.service.applying.db.product.AdministerProductSA
import mg.socolait.service.applying.db.product.AdministerProductSAImpl
import mg.socolait.service.applying.db.user.AdministerUserSA
import mg.socolait.service.applying.db.user.AdministerUserSAImpl
import mg.socolait.service.applying.ws.GenericSA
import mg.socolait.service.applying.ws.customer.CustomerSA
import mg.socolait.service.applying.ws.customer.CustomerSAImpl
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext

/**
 * Created by mampita_andria on 11/12/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
open class AuthentificationSAImpl : GenericSA(), AuthentificationSA {

    @Bean(AdministerProductSAImpl::class)
    lateinit var administerProduct: AdministerProductSA
    @Bean(AdministerUserSAImpl::class)
    lateinit var administerUser: AdministerUserSA
    @RootContext
    lateinit var context: Context
    @Bean(CustomerSAImpl::class)
    lateinit var customerSA: CustomerSA
    @Bean
    lateinit var urlManager: UrlManager


    override fun authenticate(params: LoginDto,
                              before: BeforeClosure,
                              success: SuccessClosure<String>,
                              failure: FailureClosure, done: DoneClosure) {

        before?.invoke()

        post<LoginDto, LoginResponseDto>(
                url = urlManager.login,
                params = params,
                success = { response ->
                    if (response != null) {
                        if (response.isSuccess) {
                            response.user?.let {
                                administerUser.delete()
                                it.token = response.token
                                administerUser.insert(it)
                            }
                            response.products?.let {
                                administerProduct.insertAll(it)
                            }
                            customerSA.retrieve(
                                    token = response.token!!,
                                    success = { _ ->
                                        success?.invoke(response.token!!)
                                        done?.invoke()
                                    },
                                    failure = { _ ->
                                        //failure?.invoke(error)
                                        success?.invoke(response.token!!)
                                        done?.invoke()
                                    }
                            )
                        } else {
                            response.message?.let {
                                failure?.invoke(it)
                            }
                            done?.invoke()
                        }
                    } else {
                        failure?.invoke(context.getString(R.string.api_error_empty_response))
                        done?.invoke()
                    }
                },
                failure = { error ->
                    failure?.invoke(error)
                    done?.invoke()
                })
    }

}