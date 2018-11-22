package com.destiny.Router.utilities.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import java.util.*


/**
 * @author e.fetskovich on 11/20/18.
 */

class PermissionHelper {

    private var REQUEST_CODE: Int = 0
    private var activity: Activity? = null
    private var fragment: Fragment? = null
    private var permissions: Array<String> = emptyArray()
    private var permissionCallback: PermissionCallback? = null
    private var showRational: Boolean = false

    constructor(activity: Activity, permissions: Array<String>, requestCode: Int) {
        this.activity = activity
        this.permissions = permissions
        this.REQUEST_CODE = requestCode
        checkIfPermissionPresentInAndroidManifest()
    }

    constructor(fragment: Fragment, permissions: Array<String>, requestCode: Int) {
        this.fragment = fragment
        this.permissions = permissions
        this.REQUEST_CODE = requestCode
        checkIfPermissionPresentInAndroidManifest()
    }

    private fun checkIfPermissionPresentInAndroidManifest() {

        for (permission in permissions) {
            if (!hasPermissionInManifest(permission)) {
                throw RuntimeException("Permission ($permission) Not Declared in manifest")
            }
        }

    }

    fun request(permissionCallback: PermissionCallback) {
        this.permissionCallback = permissionCallback
        if (!checkSelfPermission(permissions)) {
            showRational = shouldShowRational(permissions)
            if (activity != null) {
                ActivityCompat.requestPermissions(activity!!, filterNotGrantedPermission(permissions), REQUEST_CODE)
            } else {
                fragment?.requestPermissions(filterNotGrantedPermission(permissions), REQUEST_CODE)
            }
        } else {
            permissionCallback.onPermissionGranted()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            val grantedPermissions = ArrayList<String>()

            grantResults.forEachIndexed { index, grantResult ->
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permissions.get(index))
                }
            }

            handlePermissionCallback(grantedPermissions.size != permissions.size, grantedPermissions.toTypedArray())
        }
    }

    private fun handlePermissionCallback(isDenied: Boolean, grantedPermissions: Array<String>) {
        if (isDenied) {
            onPermissionDenied(grantedPermissions)
        } else {
            permissionCallback?.onPermissionGranted()
        }
    }

    private fun onPermissionDenied(grantedPermissions: Array<String>) {
        val currentShowRational = shouldShowRational(permissions)
        if (!showRational && !currentShowRational) {
            permissionCallback?.onPermissionDeniedBySystem()
        } else {
            if (!grantedPermissions.isEmpty()) {
                permissionCallback?.onIndividualPermissionGranted(grantedPermissions)
            }
            permissionCallback?.onPermissionDenied()
        }
    }


    @SuppressWarnings("unchecked")
    private fun <T : Context> getContext(): T {
        return if (activity != null) activity as T else fragment!!.context as T
    }

    private fun filterNotGrantedPermission(permissions: Array<String>): Array<String> {
        val notGrantedPermission = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermission.add(permission)
            }
        }
        return notGrantedPermission.toTypedArray()
    }

    fun checkSelfPermission(permissions: Array<String> = emptyArray()): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun shouldShowRational(permissions: Array<String>): Boolean {
        var currentShowRational = false
        for (permission in permissions) {

            if (activity != null) {
                if (shouldShowRequestPermissionRationale(activity!!, permission)) {
                    currentShowRational = true
                    break
                }
            } else {
                if (fragment?.shouldShowRequestPermissionRationale(permission) == true) {
                    currentShowRational = true
                    break
                }
            }
        }
        return currentShowRational
    }

    private fun hasPermissionInManifest(permission: String): Boolean {
        try {
            val context = activity ?: fragment!!.activity
            val info = context!!.packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            // If the permission is not declared in Manifest, throw the exception
            info?.requestedPermissions?.firstOrNull { it.equals(permission) }
                    ?: throw java.lang.Exception()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

}
