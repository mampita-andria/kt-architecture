package mg.socolait.data.dto.response.login

import mg.socolait.data.dto.ProductDto
import mg.socolait.data.dto.UserDto


/**
 * Created by lovasoa_arnaud on 11/12/2017.
 */
data class LoginResponseDto(

        var isSuccess: Boolean = false,
        var message: String? = null,
        var token: String? = null,
        var user: UserDto? = null,
        var products: List<ProductDto>? = null
)