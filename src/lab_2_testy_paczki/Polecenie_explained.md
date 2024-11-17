Na podstawie dokumentu masz za zadanie zasymulować działanie małego punktu pocztowego. Oto kluczowe elementy systemu i wymagania:

1. **System składa się z:**
- Stanowisk obsługi (okienek pocztowych)
- Kolejki interesantów (o ograniczonej długości L)
- Interesantów napływających do systemu

2. **Zachowanie systemu:**
- **Napływ interesantów:**
    - Interesanci przybywają w losowych odstępach czasu
    - Czasy między przybyciem kolejnych interesantów mają rozkład wykładniczy (strumień Poissona) z parametrem lambda

- **Kolejka:**
    - Ma ograniczoną długość L
    - Działa na zasadzie FIFO (First In First Out)

- **Obsługa interesantów:**
    - Czas obsługi każdego interesanta jest losowy z rozkładem normalnym N(mi, sigma)
    - Stanowisko obsługuje kolejnych interesantów bez przerwy

- **Niecierpliwość interesantów:**
    - Każdy interesant ma indywidualny maksymalny czas oczekiwania
    - Czas ten jest losowany przy przybyciu z rozkładu równomiernego U(a,b)
    - Jeśli interesant czeka dłużej niż jego maksymalny czas - opuszcza kolejkę
    - Jeśli rozpocznie się jego obsługa przed upływem tego czasu - zostaje do końca

3. **Wymagane pomiary i statystyki:**
- Średnia liczba interesantów w systemie (używając Statistics.weightedMean)
- Średni czas przebywania interesanta w systemie (używając Statistics.arithmeticMean)

4. **Wymagane wykresy:**
- Wykres zmian liczby interesantów w czasie (DiagramType.TIME)
- Dystrybuanta czasu przebywania interesanta w systemie (DiagramType.DISTRIBUTION)

5. **Implementacja:**
- Należy stworzyć trzy klasy zdarzeń dziedziczące po BasicSimEvent:
    - Zdarzenie generujące nowych interesantów
    - Zdarzenie obsługujące interesantów w okienkach
    - Plus jedno dodatkowe zdarzenie (prawdopodobnie do obsługi niecierpliwości)

Jest to typowy przykład systemu kolejkowego z elementami losowymi i dodatkowymi ograniczeniami (niecierpliwość klientów, ograniczona długość kolejki).