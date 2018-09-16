package ua.kpi.dongumen.compgraph.lab3

import ua.kpi.dongumen.compgraph.extensions.fori

class Sphere3D(val radius: Double) {
    val count = 20
    var point = mutableListOf<Point3D>()

    init {
        fori(0.0, { it <= Math.PI * 2 }, { it + Math.PI / count })
        { alpha ->
            point.add(Point3D())
            val p = point.last()

            p.x = Math.cos(alpha) * this.radius
            p.y = 0.0
            p.z = Math.sin(alpha) * this.radius
        }

        fori(1, { it >= -1 }, { it - 2 }) { direction ->
            fori(Math.PI / count, { it < 1.445 }, { it + Math.PI / count }) { beta ->

                val radius = Math.cos(beta) * this.radius
                val fixedY = Math.sin(beta) * this.radius * direction

                fori(0.0, { it < Math.PI * 2 }, { it + Math.PI / count }) { alpha ->
                    point.add(Point3D())
                    val p = point.last()

                    p.x = Math.cos(alpha) * radius
                    p.y = fixedY
                    p.z = Math.sin(alpha) * radius
                }
            }
        }
    }
}
