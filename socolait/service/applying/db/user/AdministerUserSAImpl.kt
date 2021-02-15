package mg.socolait.service.applying.db.user

import android.content.Context
import android.content.SharedPreferences
import com.j256.ormlite.misc.TransactionManager
import mg.socolait.common.exceptions.TechnicalException
import mg.socolait.constraint.factory.db.UserFactory
import mg.socolait.common.constants.Prefs
import mg.socolait.data.dto.UserDto
import mg.socolait.repository.UserRepository
import mg.socolait.service.preference.PreferenceHelper
import mg.socolait.service.preference.PreferenceHelper.set
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EBean
import org.androidannotations.annotations.RootContext
import java.sql.SQLException

/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
open class AdministerUserSAImpl : AdministerUserSA {

    @Bean
    open lateinit var userRepository: UserRepository

    @RootContext
    lateinit var context: Context

    private val prefs: SharedPreferences by lazy { PreferenceHelper.customPrefs(context) }

    override fun insert(user: UserDto) {
        try {
            TransactionManager.callInTransaction(userRepository.connectionSource) {
                userRepository.insert(UserFactory.toDomainObject(user))
                prefs[Prefs.USER_TOKEN] = user.token
                null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            throw TechnicalException(e)
        }
    }

    override fun delete() {
        try {
            TransactionManager.callInTransaction(userRepository.connectionSource) {
                userRepository.deleteAll()
                prefs[Prefs.USER_TOKEN] = ""
                null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            throw TechnicalException(e)
        }
    }

    override fun get(): UserDto? {
        val list = UserFactory.toDataTransferObjects(userRepository.findAll())
        return if (list.isNotEmpty())
            list[0]
        else
            null
    }

}