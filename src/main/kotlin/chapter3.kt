sealed class List<out A> {
    companion object {
        fun <A> of(vararg aa: A): List<A> {
            val tail = aa.sliceArray(1 until aa.size)
            return if (aa.isEmpty()) Nil else Cons(aa[0], of(*tail))
        }

        fun sum(xs: List<Int>): Int =
            when (xs) {
                is Nil -> 0
                is Cons -> xs.head + sum(xs.tail)
            }

        fun product(xs: List<Double>): Double =
            when (xs) {
                is Nil -> 1.0
                is Cons ->
                    xs.head * product(xs.tail)
            }

        fun <A> tail(xs: List<A>): List<A> =
            when (xs) {
                is Nil -> Nil
                is Cons -> xs.tail
            }

        fun <A> setHead(xs: List<A>, x: A): List<A> =
            when (xs) {
                is Nil -> Cons(x, Nil)
                is Cons -> Cons(x, xs.tail)
            }

        tailrec fun <A> drop(l: List<A>, n: Int): List<A> =
            when (l) {
                is Nil -> Nil
                is Cons ->
                    if (n <= 0) l else drop(l.tail, n - 1)
            }

        tailrec fun <A> dropWhile(l: List<A>, f: (A) -> Boolean): List<A> =
            when (l) {
                is Nil -> Nil
                is Cons ->
                    if (f(l.head))
                        dropWhile(l.tail, f)
                    else
                        l
            }

        fun <A> append(a1: List<A>, a2: List<A>): List<A> =
            when (a1) {
                is Nil -> a2
                is Cons -> Cons(a1.head, append(a1.tail, a2))
            }

        fun <A> init(l: List<A>): List<A> =
            when (l) {
                is Nil -> Nil
                is Cons ->
                    if (l.tail is Nil)
                        Nil
                    else
                        Cons(l.head, init(l.tail))
            }

        fun <A, B> foldRight(xs: List<A>, z: B, f: (A, B) -> B): B =
            when (xs) {
                is Nil -> z
                is Cons -> f(xs.head, foldRight(xs.tail, z, f))
            }

        fun sum2(ints: List<Int>): Int =
            foldRight(ints, 0, { a, b -> a + b })

        fun product2(dbs: List<Double>): Double =
            foldRight(dbs, 1.0, { a, b -> a * b })

        fun <A> length(xs: List<A>): Int =
            foldRight(xs, 0, { _, acc -> acc + 1 })

        tailrec fun <A, B> foldLeft(xs: List<A>, z: B, f: (B, A) -> B): B =
            when (xs) {
                is Nil -> z
                is Cons -> foldLeft(xs.tail, f(z, xs.head), f)
            }

        fun sum3(xs: List<Int>) =
            foldLeft(xs, 0, { x, y -> x + y })

        fun product3(xs: List<Double>): Double =
            foldLeft(xs, 1.0, { x, y -> x * y })

        fun <A> length2(xs: List<A>): Int =
            foldLeft(xs, 0, { acc, _ -> acc + 1 })

        fun <A> reverse(xs: List<A>): List<A> =
            foldLeft(xs, Nil as List<A>, { acc, a -> Cons(a, acc) })

        fun <A> append2(a1: List<A>, a2: List<A>): List<A> =
            foldRight(a1, a2, { a, acc -> Cons(a, acc) })

        fun <A> flatten(xs: List<List<A>>): List<A> =
            foldRight(xs, Nil as List<A>, { ys, acc -> append(ys, acc) })

        fun add1(xs: List<Int>): List<Int> =
            foldRight(xs, Nil as List<Int>, { i, acc -> Cons(i + 1, acc) })

        fun doublesToStrings(xs: List<Double>): List<String> =
            foldRight(xs, Nil as List<String>, { x, acc -> Cons(x.toString(), acc) })

        fun <A, B> map(xs: List<A>, f: (A) -> B): List<B> =
            foldRight(xs, Nil as List<B>, { x, acc -> Cons(f(x), acc) })

        fun <A> filter(xs: List<A>, p: (A) -> Boolean): List<A> =
            foldRight(xs, Nil as List<A>, { a, acc -> if (p(a)) Cons(a, acc) else acc })

        fun <A, B> flatMap(xa: List<A>, f: (A) -> List<B>): List<B> =
            flatten(map(xa, f))

        fun <A> filter2(xs: List<A>, p: (A) -> Boolean): List<A> =
            flatMap(xs) { a -> if (p(a)) List.of(a) else Nil }

        fun <A> zipWith(xs: List<A>, ys: List<A>, f: (A, A) -> A): List<A> =
            when (xs) {
                is Nil -> Nil
                is Cons -> when (ys) {
                    is Nil -> Nil
                    is Cons -> Cons(
                        f(xs.head, ys.head),
                        zipWith(xs.tail, ys.tail, f)
                    )
                }
            }

        tailrec fun <A> hasSubsequence(xs: List<A>, sub: List<A>): Boolean = TODO()

    }
}

object Nil : List<Nothing>()
data class Cons<out A>(val head: A, val tail: List<A>) : List<A>()
