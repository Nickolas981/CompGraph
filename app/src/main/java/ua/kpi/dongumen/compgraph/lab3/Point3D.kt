package ua.kpi.dongumen.compgraph.lab3

class Point3D {
    var x = 0.0
    var y = 0.0
    var z = 0.0


    fun copy() : Point3D {
        return Point3D()
                .also { it.x = x }
                .also { it.y = y }
                .also { it.z = z }
    }
}