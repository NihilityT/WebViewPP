package cn.wankkoree.xp.webviewpp.hook

import com.highcapable.yukihookapi.hook.core.YukiMemberHookCreator
import com.highcapable.yukihookapi.hook.core.finder.members.MethodFinder
import com.highcapable.yukihookapi.hook.type.java.*
import java.util.Locale

val methodRegex = Regex("^(?:\\( *(.*?) *\\))? *(.+?)(?: *\\( *(.*?) *\\))?\$")

fun YukiMemberHookCreator.MemberHookCreator.methodX(methodDefinition : String) = methodRegex.matchEntire(methodDefinition)!!.let {
    val retDecl = 1
    val argsDecl = 3
    val funcnameDecl = 2
    if (it.groups[retDecl] == null && it.groups[argsDecl] == null) {
        method { name = it.groupValues[funcnameDecl] }.all()
    } else if (it.groups[retDecl] != null && it.groups[argsDecl] == null) {
        method {
            name(it.groupValues[funcnameDecl])
            returnType(it.groupValues[retDecl].typeConvert())
        }
    } else if (it.groups[retDecl] == null && it.groups[argsDecl] != null) {
        val params = it.groupValues[argsDecl].split(',').map { v -> v.trim() }.filter { v -> v.isNotEmpty() }
        method {
            name(it.groupValues[funcnameDecl])
            if (params.isNotEmpty())
                param(*params.map { param -> param.typeConvert() }.toTypedArray())
            else
                emptyParam()
        }
    } else { // it.groups[1] != null && it.groups[3] != null
        val params = it.groupValues[argsDecl].split(',').map { v -> v.trim() }.filter { v -> v.isNotEmpty() }
        method {
            name(it.groupValues[funcnameDecl])
            if (params.isNotEmpty())
                param(*params.map { param -> param.typeConvert() }.toTypedArray())
            else
                emptyParam()
            returnType(it.groupValues[retDecl].typeConvert())
        }
    }
}

fun MethodFinder.methodX (methodDefinition : String) = methodRegex.matchEntire(methodDefinition)!!.let {
    name(it.groupValues[2])
    if (it.groups[3] != null) {
        val params = it.groupValues[3].split(',').map { v -> v.trim() }.filter { v -> v.isNotEmpty() }
        if (params.isNotEmpty())
            param(*params.map { param -> param.typeConvert() }.toTypedArray())
        else
            emptyParam()
    }
    if (it.groups[1] != null) {
        returnType(it.groupValues[1].typeConvert())
    }
}

fun String.typeConvert() : Any = when (this.lowercase(Locale.getDefault())) {
    "bool" -> BooleanType
    "boolean" -> BooleanType
    "byte" -> "java.lang.Byte"
    "char" -> CharType
    "character" -> CharType
    "class" -> "java.lang.Class"
    "double" -> DoubleType
    "enum" -> "java.lang.Enum"
    "float" -> FloatType
    "int" -> IntType
    "integer" -> IntType
    "long" -> LongType
    "num" -> "java.lang.Number"
    "number" -> "java.lang.Number"
    "obj" -> "java.lang.Object"
    "object" -> "java.lang.Object"
    "short" -> ShortType
    "str" -> StringClass
    "string" -> StringClass
    "unit" -> UnitType
    "void" -> UnitType
    else -> this
}
