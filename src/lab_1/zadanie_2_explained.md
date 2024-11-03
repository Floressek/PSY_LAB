Aby zastosować metodę odwracania dystrybuanty dla tego przykładu, musimy najpierw zidentyfikować funkcję gęstości prawdopodobieństwa $f(x)$ i skonstruować dystrybuantę $F(x)$, a następnie wyznaczyć jej funkcję odwrotną $F^{-1}(u)$.

### Krok 1: Analiza funkcji gęstości prawdopodobieństwa $f(x)$

Z wykresu widzimy, że funkcja gęstości $f(x)$ przyjmuje wartości:

- $f(x) = \frac{1}{2}$ dla $x \in [-1, 0]$
- $f(x) = \frac{1}{4}$ dla $x \in [0, 2]$
- $f(x) = 0$ poza tymi przedziałami

### Krok 2: Obliczenie dystrybuanty $F(x)$

Dystrybuanta $F(x)$ to całka z funkcji gęstości od $-\infty$ do $x$:

$[F(x) = \int_{-\infty}^{x} f(t) \, dt]$

Obliczamy $F(x)$ w przedziałach, gdzie $f(x)$ ma niezerową wartość:

1. **Dla $x \leq -1$:**

   $[
   F(x) = 0
   $]

2. **Dla $x \in [-1, 0]$:**

   $[
   F(x) = \int_{-1}^{x} \frac{1}{2} \, dt = \frac{1}{2}(x + 1)
   $]

3. **Dla $x \in [0, 2]$:**

   $[
   F(x) = F(0) + \int_{0}^{x} \frac{1}{4} \, dt
   $]

   Wiemy, że $F(0) = \frac{1}{2}$, więc

   $[
   F(x) = \frac{1}{2} + \frac{1}{4}x
   $]

4. **Dla $x > 2$:**

   $[
   F(x) = 1
   $]

Podsumowując:

$[
F(x) =
\begin{cases}
0 & \text{dla } x \leq -1 \\[8pt]
\frac{1}{2}(x + 1) & \text{dla } x \in [-1, 0] \\[8pt]
\frac{1}{2} + \frac{1}{4}x & \text{dla } x \in [0, 2] \\[8pt]
1 & \text{dla } x > 2
\end{cases}
$]

### Krok 3: Znalezienie odwrotności $F^{-1}(u)$

1. **Dla $u \in [0, \frac{1}{2}]$:**

   Mamy $u = \frac{1}{2}(x + 1)$. Rozwiązując równanie dla $x$, otrzymujemy:

   $[
   x = 2u - 1
   $]

2. **Dla $u \in [\frac{1}{2}, 1]$:**

   Mamy $u = \frac{1}{2} + \frac{1}{4}x$. Rozwiązując równanie dla $x$, otrzymujemy:

   $[
   x = 4u - 2
   $]

Podsumowując, funkcja odwrotna $F^{-1}(u)$ ma postać:

$[
F^{-1}(u) =
\begin{cases}
2u - 1 & \text{dla } u \in [0, \frac{1}{2}] \\[8pt]
4u - 2 & \text{dla } u \in [\frac{1}{2}, 1]
\end{cases}
$]

### Generowanie zmiennych losowych

Aby wygenerować zmienną losową $X$ z rozkładu opisanego przez $f(x)$:

1. Generujemy liczbę losową $U$ z przedziału $[0, 1]$.
2. Jeśli $U \in [0, \frac{1}{2}]$, to $X = 2U - 1$.
3. Jeśli $U \in [\frac{1}{2}, 1]$, to $X = 4U - 2$.

W ten sposób otrzymujemy wartości $X$ zgodne z rozkładem zadanym przez funkcję gęstości $f(x)$.