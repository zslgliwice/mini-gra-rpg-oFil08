# mini-gra-rpg-konsolowa

### **Zadanie: "Przygoda w Tajemniczym Lesie" 🌲🎲**  

#### **Opis zadania**  
Twoim zadaniem jest stworzenie **tekstowej gry przygodowej** w języku **Python, C++ lub Java**. Gracz wciela się w **odkrywcę**, który zgubił się w tajemniczym lesie. Aby przetrwać i znaleźć drogę do domu, musi podejmować decyzje, eksplorować teren, walczyć z potworami i zbierać zasoby.  

### **Zasady gry**  
1. **Gracz rozpoczyna z**:
   - **100 punktami zdrowia** (`hp`).
   - **10 sztukami złota** (`gold`).
   - **Brakiem broni (początkowy atak = 5).**

2. **Każda tura to losowe wydarzenie** (mechanika losowania):  
   - 🏹 **Spotkanie z przeciwnikiem** (wilkiem, goblinem, bandytą) – walka lub ucieczka.  
   - 💰 **Skarb** – znalezienie złota lub przedmiotów (lekarstwa, broń).  
   - 🏕 **Obóz** – możliwość odpoczynku (leczenie za złoto).  
   - 🏠 **Wyjście z lasu** – losowe szansa (1/10) na znalezienie drogi do domu.  

3. **Walka z przeciwnikiem**:  
   - Przeciwnik ma **losową ilość HP (20-50)** i **losowy atak (5-15)**.  
   - Gracz może wybrać:
     - **Atakować** (zadaje losowe obrażenia od `atak/2` do `atak`).  
     - **Uciec** (50% szansy na sukces, inaczej traci turę).  
   - Jeśli HP gracza spadnie do **0**, gra kończy się przegraną.  

4. **Gracz może podjąć decyzję** w każdej turze:
   - **Eksplorować dalej**.
   - **Sprawdzić ekwipunek**.
   - **Odpocząć (jeśli jest w obozie)**.
   - **Zakończyć grę** (jeśli znajdzie wyjście).  

---

### **Wymagania techniczne**
✅ **Mechanika losowania liczb** (np. wydarzenia, przeciwnicy, łupy).  
✅ **Pętla gry** (gra toczy się do momentu śmierci gracza lub znalezienia wyjścia).  
✅ **Podejmowanie decyzji** (gracz wybiera akcję w każdej turze).  
✅ **Obiektowość** (można użyć klasy `Player`, `Enemy`, `Game`).  
✅ **Obsługa błędów** (np. niewłaściwy wybór akcji).  

---

### **Przykładowa rozgrywka (Python)**
```
🌲 Witaj w Tajemniczym Lesie! 🌲
Twoje statystyki: HP = 100 | Złoto = 10 | Atak = 5
---
Co chcesz zrobić?
1. Eksplorować dalej
2. Sprawdzić ekwipunek
3. Zakończyć grę

> 1

Losowe wydarzenie: 🏹 Spotkałeś wilka!
Wilk: HP = 30, Atak = 7
Co robisz?
1. Atakuj
2. Uciekaj

> 1
Zadałeś 8 obrażeń! Wilk ma teraz 22 HP.
Wilk atakuje! Otrzymujesz 7 obrażeń. Masz teraz 93 HP.

Co robisz?
> 1
Zadałeś 10 obrażeń! Wilk ma teraz 12 HP.
Wilk atakuje! Otrzymujesz 5 obrażeń. Masz teraz 88 HP.

Co robisz?
> 1
Zadałeś 12 obrażeń! Pokonałeś wilka! 🎉
Zdobywasz 5 sztuk złota.

Twoje statystyki: HP = 88 | Złoto = 15 | Atak = 5
---
Co chcesz zrobić?
> 1

Losowe wydarzenie: 💰 Znalazłeś 10 sztuk złota!
Twoje złoto: 25

Co chcesz zrobić?
> 1

Losowe wydarzenie: 🏠 Znalazłeś wyjście z lasu! 🎉
Gratulacje, udało Ci się przeżyć i wrócić do domu!
```

---

### **Dodatkowe wyzwania (dla chętnych)**
🔥 **System ekwipunku** – dodaj możliwość zdobywania i używania przedmiotów (np. lepsze bronie).  
🔥 **Więcej losowych zdarzeń** – dodaj handlarzy, sekretne lokacje, różne potwory.  
🔥 **System doświadczenia i poziomów** – gracz zdobywa XP i zwiększa atak/HP.  
🔥 **Tryb trudności** – łatwy/normalny/trudny (różne poziomy przeciwników).  

---

Czy takie zadanie Ci odpowiada? Jeśli chcesz, mogę przygotować **szkielet kodu** w wybranym języku! 🚀
