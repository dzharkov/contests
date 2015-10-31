package codejam.round_c_2014.b

import common.DataReader
import common.solveAll

val MOD = 1000000007L

fun fact(n: Int) : Long {
    return (1..n).fold(1L) { x, y -> x * y.toLong() % MOD }
}

fun isValid(s: String) : Boolean {
    var i = 0
    val was = BooleanArray(255)
    while (i < s.length) {
        if (was[s[i].toInt()]) {
            return false
        }
        was[s[i].toInt()] = true
        while (i + 1 < s.length && s[i+1] == s[i])i++
        i++
    }
    return true
}

fun solveCase(input: DataReader) : String {
    val n = input.nextInt()
    var cars = Array(n, { Pair(input.nextToken(), 1L) })

    fun isThereSomethingToReduce() : Boolean {
        return ('a'..'z').any {
            c -> cars.count { it.first.contains(c.toString()) } > 1
        }
    }

    fun reduceOne() : Boolean {
        val c = ('a'..'z').first {
            c_ -> cars.count { it.first.contains(c_.toString()) } > 1
        }

        var (carsToMerge, others) = cars.partition { it.first.contains(c+"") }
        var multiplier = carsToMerge.fold(1L) { x,y -> x*y.second % MOD }

        val sizeWas = carsToMerge.size
        carsToMerge = carsToMerge.filter { it.first.any { it != c } }

        if (carsToMerge.size > 2) {
            return false
        }

        if (sizeWas > carsToMerge.size) {
            multiplier *= fact(sizeWas - carsToMerge.size)
            multiplier %= MOD
        }

        var newCar : String

        if (carsToMerge.size == 2) {
            if (isValid(carsToMerge[0].first + carsToMerge[1].first))  {
                newCar = carsToMerge[0].first + carsToMerge[1].first
            }
            else if (isValid(carsToMerge[1].first + carsToMerge[0].first))  {
                newCar = carsToMerge[1].first + carsToMerge[0].first
            }
            else return false
        } else if (carsToMerge.size == 1) {
            if (!isValid(carsToMerge[0].first)) {
                return false
            }

            newCar = carsToMerge[0].first
        } else if (carsToMerge.isEmpty()) {
            newCar = c.toString()
        } else {
            throw RuntimeException("bad case")
        }

        cars = Array(others.size + 1, {
                i -> if (i <= others.lastIndex) others[i] else Pair(newCar, multiplier)
            }
        )

        return true
    }


    while (isThereSomethingToReduce()) {
        if (!reduceOne()) {
            return "0"
        }
    }

    for (car in cars) {
        if (!isValid(car.first)) return "0"
    }

    return (
            (
                    (
                            cars.fold(1L) { x, y -> (x * y.second) % MOD }
                    ) * fact(cars.size)
            )
            % MOD
    ).toString()
}

fun main(args: Array<String>) {
    solveAll(solver = ::solveCase)
}



