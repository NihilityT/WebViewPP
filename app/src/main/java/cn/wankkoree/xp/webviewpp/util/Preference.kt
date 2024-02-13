package cn.wankkoree.xp.webviewpp.util

import android.content.Context
import cn.wankkoree.xp.webviewpp.data.AppSP
import cn.wankkoree.xp.webviewpp.hook.Main
import com.highcapable.yukihookapi.hook.factory.prefs
import com.highcapable.yukihookapi.hook.param.PackageParam

fun PackageParam.appPrefs() =
    prefs("apps_${Main.mProcessName}")

fun PackageParam.resource(name: String, version: String) =
    prefs("resources_${name}_${version}")

fun PackageParam.source(name: String, version: String) =
    resource(name, version).getString(name)

fun Context.modulePrefs() =
    prefs("module")

fun Context.resources() =
    prefs("resources")

fun Context.apps() =
    prefs("apps")

fun Context.appPrefs(packageName: String) =
    prefs("apps_${packageName}")