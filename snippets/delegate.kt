import kotlin.reflect.KProperty

class Delegate {
    private var value: String? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}'" +
        "with the value ${value} to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class Example {
    var p: String by Delegate()
}

fun main() {
   	val e = Example()
    e.p = "test"
	println(e.p)
}