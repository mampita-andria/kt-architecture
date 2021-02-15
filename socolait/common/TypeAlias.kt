package mg.socolait.common

import com.androidnetworking.error.ANError

/**
 * Type alias for Web Services Requests
 */
typealias BeforeClosure = (() -> Unit)?
typealias SuccessClosure <T> = ((T) -> Unit)?
typealias APIFailureClosure = ((ANError?) -> Unit)?
typealias FailureClosure = ((String) -> Unit)?
typealias DoneClosure = (() -> Unit)?

/**
 * Type alias global
 */
typealias VoidClosure = (() -> Unit)?
typealias Closure<T> = ((T) -> Unit)?