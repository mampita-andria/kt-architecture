package mg.socolait.service.applying.ws.authentification

import mg.socolait.common.BeforeClosure
import mg.socolait.common.DoneClosure
import mg.socolait.common.FailureClosure
import mg.socolait.common.SuccessClosure
import mg.socolait.data.dto.request.LoginDto

/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
interface AuthentificationSA {

    fun authenticate(params: LoginDto,
                     before: BeforeClosure = null,
                     success: SuccessClosure<String> = null,
                     failure: FailureClosure = null,
                     done: DoneClosure = null)
}