package mg.socolait.service.applying.db.user

import mg.socolait.data.dto.UserDto

/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
interface AdministerUserSA {

    /**
     * Insert a user in db
     * @param user
     */
    fun insert(user: UserDto)

    /**
     * Delete user in db
     */
    fun delete()

    /**
     * Get the user saved in db
     */
    fun get(): UserDto?
}