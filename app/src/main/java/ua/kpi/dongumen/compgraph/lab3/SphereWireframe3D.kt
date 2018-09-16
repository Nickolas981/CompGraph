package ua.kpi.dongumen.compgraph.lab3

import ua.kpi.dongumen.compgraph.extensions.fori

class SphereWireframe3D(val radius: Double) {
    var vertices = mutableListOf<Point3D>()
    var rings = 20
    var slices = 20

    var halfPi = Math.PI / 2
    var dTheta = (Math.PI * 2) / this.slices
    var dPhi = Math.PI / this.rings

    init {
        fori(0, { it < this.rings + 1 }, { it + 1 })
        {lat ->
            val phi = halfPi - lat * dPhi
            val cosPhi = Math.cos(phi)
            val sinPhi = Math.sin(phi)

            // Iterate over longitudes (slices)
            fori(0, { it < this.slices + 1 }, { it + 1 }) {lon ->
                val theta = lon * dTheta
                val cosTheta = Math.cos(theta)
                val sinTheta = Math.sin(theta)

                vertices.add(Point3D())
                val p = vertices.last()

                p.x = this.radius * cosTheta * cosPhi
                p.y = this.radius * sinPhi
                p.z = this.radius * sinTheta * cosPhi
            }
        }
    }

}
