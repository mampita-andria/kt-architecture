package mg.socolait.repository

import android.content.Context
import mg.socolait.data.domainobject.User
import mg.socolait.repository.common.GenericCommonRepository
import org.androidannotations.annotations.AfterInject
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext

/**
 * Created by mampita_andria on 11/12/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
open class UserRepository: GenericCommonRepository<User, Long>() {

    @RootContext
    lateinit var context: Context

    @AfterInject
    fun initialize() {
        initDatabase(context)
        if (dao == null) {
            dao = databaseHelper?.getRuntimeExceptionDao(User::class.java)
        }
    }
}