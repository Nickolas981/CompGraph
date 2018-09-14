package ua.kpi.dongumen.compgraph.extensions

fun <T> Any.fori(startValue: T, compare: (T) -> Boolean, action: (T) -> T, body: (T) -> Unit) {
    var start = startValue
    while (compare.invoke(start)) {

        body.invoke(start)

        start = action.invoke(start)
    }
}