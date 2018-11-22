package com.destiny.Router.utilities.permissions

/**
 * @author e.fetskovich on 11/20/18.
 */
interface PermissionCallback {
    fun onPermissionGranted()
    fun onIndividualPermissionGranted(grantedPermission: Array<String>)
    fun onPermissionDenied()
    fun onPermissionDeniedBySystem()
}
