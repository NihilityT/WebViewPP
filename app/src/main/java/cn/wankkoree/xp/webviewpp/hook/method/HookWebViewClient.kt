package cn.wankkoree.xp.webviewpp.hook.method

import cn.wankkoree.xp.webviewpp.data.AppSP
import cn.wankkoree.xp.webviewpp.hook.Main
import cn.wankkoree.xp.webviewpp.hook.methodX
import cn.wankkoree.xp.webviewpp.util.appPrefs
import cn.wankkoree.xp.webviewpp.util.source
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.loggerD
import com.highcapable.yukihookapi.hook.log.loggerE
import com.highcapable.yukihookapi.hook.log.loggerI
import com.highcapable.yukihookapi.hook.param.PackageParam

/** Hook WebViewClient类，实现:
 *
 * webViewClient.onPageFinished({webView.evaluateJavascript($vConsole+$eruda)})
 **/
fun PackageParam.hookWebViewClient (
    Class_WebViewClient : String,
    Method_onPageFinished : String,
    Class_WebView: String,
    Method_evaluateJavascript: String,
) {
    Class_WebViewClient.hook {
        injectMember {
            methodX(Method_onPageFinished)
            beforeHook {
                val webView = args[0]

                if (Main.debug) loggerD(msg = "${instanceClass.name}.onPageFinished({webView.evaluateJavascript(\$vConsole+\$eruda)})")
                Class_WebView.toClass().method {
                    methodX(Method_evaluateJavascript)
                }.result {
                    onNoSuchMethod {
                        loggerE(msg = "Hook.Method.NoSuchMethod at hookWebViewClient\uD83D\uDC49onPageFinished\uD83D\uDC49evaluateJavascript", e = it)
                    }
                    val appPrefs = appPrefs()
                    if (appPrefs.get(AppSP.vConsole)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof vConsole === 'undefined'){\n" +
                            "    ${source("vConsole", appPrefs.get(AppSP.vConsole_version))};\n" +
                            "    var vConsole=(typeof VConsole !== 'undefined')?new VConsole():'未知的 vConsole 版本，可能需要其他加载方式';\n" + // 创建全局变量以供用户使用
                            "    document.getElementById('__vconsole').style.zIndex=2147483647;\n" + // 将 vConsole 提升到最顶层
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.vConsole_plugin_sources)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof vConsole !== 'undefined'){\n" +
                            "    ${source("vConsole_plugin_sources", appPrefs.get(AppSP.vConsole_plugin_sources_version))};\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.vConsole_plugin_stats)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof vConsole !== 'undefined' && typeof vConsoleStatsPlugin === 'undefined'){\n" +
                            "    ${source("vConsole_plugin_stats", appPrefs.get(AppSP.vConsole_plugin_stats_version))};\n" +
                            "    var vConsoleStatsPlugin=new VConsoleStatsPlugin(vConsole);\n" + // 创建全局变量以供用户使用
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.vConsole_plugin_vue_devtools)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof vConsole !== 'undefined' && typeof vueVconsoleDevtools === 'undefined'){\n" +
                            "    ${source("vConsole_plugin_vue_devtools", appPrefs.get(AppSP.vConsole_plugin_vue_devtools_version))};\n" +
                            "    vueVconsoleDevtools.initPlugin(vConsole);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.vConsole_plugin_outputlog)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof vConsole !== 'undefined' && typeof vConsoleOutputLogsPlugin === 'undefined'){\n" +
                            "    ${source("vConsole_plugin_outputlog", appPrefs.get(AppSP.vConsole_plugin_outputlog_version))};\n" +
                            "    var vConsoleOutputLogsPlugin=new VConsoleOutputLogsPlugin(vConsole);\n" + // 创建全局变量以供用户使用
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda === 'undefined'){\n" +
                            "    ${source("eruda", appPrefs.get(AppSP.eruda_version))};\n" +
                            "    eruda.init();\n" +
                            "    eruda._shadowRoot.getElementById('eruda').style.zIndex=2147483647;\n" + // 将 eruda 提升到最顶层
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_fps)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaFps === 'undefined'){\n" +
                            "    ${source("eruda_plugin_fps", appPrefs.get(AppSP.eruda_plugin_fps_version))};\n" +
                            "    eruda.add(erudaFps);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_features)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaFeatures === 'undefined'){\n" +
                            "    ${source("eruda_plugin_features", appPrefs.get(AppSP.eruda_plugin_features_version))};\n" +
                            "    eruda.add(erudaFeatures);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_timing)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaTiming === 'undefined'){\n" +
                            "    ${source("eruda_plugin_timing", appPrefs.get(AppSP.eruda_plugin_timing_version))};\n" +
                            "    eruda.add(erudaTiming);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_memory)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaMemory === 'undefined'){\n" +
                            "    ${source("eruda_plugin_memory", appPrefs.get(AppSP.eruda_plugin_memory_version))};\n" +
                            "    eruda.add(erudaMemory);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_code)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaCode === 'undefined'){\n" +
                            "    ${source("eruda_plugin_code", appPrefs.get(AppSP.eruda_plugin_code_version))};\n" +
                            "    eruda.add(erudaCode);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_benchmark)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaBenchmark === 'undefined'){\n" +
                            "    ${source("eruda_plugin_benchmark", appPrefs.get(AppSP.eruda_plugin_benchmark_version))};\n" +
                            "    eruda.add(erudaBenchmark);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_geolocation)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaGeolocation === 'undefined'){\n" +
                            "    ${source("eruda_plugin_geolocation", appPrefs.get(AppSP.eruda_plugin_geolocation_version))};\n" +
                            "    eruda.add(erudaGeolocation);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_dom)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaDom === 'undefined'){\n" +
                            "    ${source("eruda_plugin_dom", appPrefs.get(AppSP.eruda_plugin_dom_version))};\n" +
                            "    eruda.add(erudaDom);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_orientation)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaOrientation === 'undefined'){\n" +
                            "    ${source("eruda_plugin_orientation", appPrefs.get(AppSP.eruda_plugin_orientation_version))};\n" +
                            "    eruda.add(erudaOrientation);\n" +
                            "}\n"
                            , null
                        )
                    }
                    if (appPrefs.get(AppSP.eruda_plugin_touches)) {
                        get(webView).call(
                            "javascript:" +
                            "if (typeof eruda !== 'undefined' && typeof erudaTouches === 'undefined'){\n" +
                            "    ${source("eruda_plugin_touches", appPrefs.get(AppSP.eruda_plugin_touches_version))};\n" +
                            "    eruda.add(erudaTouches);\n" +
                            "}\n"
                            , null
                        )
                    }
                }
            }
        }.result {
            onNoSuchMemberFailure {
                loggerE(msg = "Hook.Member.NoSuchMember at hookWebViewClient\uD83D\uDC49onPageFinished", e = it)
            }
            onHookingFailure {
                loggerE(msg = "Hook.Member.HookFailure at hookWebViewClient\uD83D\uDC49onPageFinished", e = it)
            }
            onHooked {
                loggerI(msg = "Hook.Member.Ended at hookWebViewClient\uD83D\uDC49onPageFinished as [$it]")
            }
            onConductFailure { hookParam, it ->
                loggerE(msg = "Hook.Member.ConductFailure at hookWebViewClient\uD83D\uDC49onPageFinished(${hookParam.args.joinToString(", ")})", e = it)
            }
        }
    }.result {
        onHookClassNotFoundFailure {
            loggerE(msg = "Hook.Class.NotFound at hookWebViewClient\uD83D\uDC49$Class_WebViewClient", e = it)
        }
        onPrepareHook {
            loggerI(msg = "Hook.Class.Started at hookWebViewClient\uD83D\uDC49$Class_WebViewClient")
        }
    }
}