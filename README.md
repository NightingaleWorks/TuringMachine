*** Turing Machine Simulator ***
This is a simple Turing machine simulator that allows users to understand how Turing machines work.
The program implements a single-tape Turing machine that uses an infinite tape to store and manipulate data.
Users can provide an input word and a transition file that defines how the machine works.
The program also supports step-by-step execution and full execution, so users can see how the tape and state change during operations.

The transitions can be specified in a plain text file in the following format and separated by spaces:
'current state of the machine' 'the symbol read' -> 'the symbol written to the tape' 'the direction of the write head movement' 'the next state'

For example:
q0 a -> _ R qa
the machine is in state q0, it reads a symbol 'a', then the machine writes a symbol '_' to the tape, the write head moves to the right, and the machine goes to state qa.

The following models can be implemented:
1. There are two final states: ACCEPT and REJECT. The machine stops when it reaches these states.
2. There is only one accepting state 'ACCEPT', and the machine stops when it reaches this state. In all other cases, it remains in an infinite loop.
3. There is a stop 'HALT' state, we only need to specify this state in the transition function

Use the File->Open menu item to load the .txt file containing the transitions.
Enter the word to be tested in the Test Word field, then press the 'Start' button.
This will load our Turing machine.

On the new screen that appears, we can step the machine, or by selecting run, the machine will step until it stops or reaches 200 steps.
If we press the button again, another 200 cycles will run. We can keep trying, but the machine will probably not stop.

Requirements:
Widows 10/11 64 bit

Installation:
1. Run the .msi installer - TuringMachine-1.0.exe
2. You can find the Windows executable C:\Program Files\TuringMachine\TuringMachine.exe
3. Right click to the icon -> Pin to the Start Menu or Send To -> create Desktop icon

Examples:

1. Recognizing the word 'abba', note 2.2. figure (turing_abba.txt) 

q0 a -> _ R qa 
q0 b -> _ R qb 
qa a -> a R qa 
qa b -> b R qa 
qa _ -> _ L q2 
q2 a -> _ L q3 
q2 b -> b R REJECT 
q3 a -> a L q3 
q3 b -> b L q3 
q3 _ -> _ R q0 
qb a -> a R qb 
qb b -> b R qb 
qb _ -> _ L q4 
q4 b -> _ L q3 
q4 a -> a L REJECT 
q0 _ -> _ R ACCEPT 


2. '101101' acceptance after 9 steps (turing_101101.txt) 

q0 0 -> 0 R q1 
q0 1 -> 1 R q2 
q1 0 -> 0 R q1 
q1 1 -> 1 R q2 
q2 0 -> 0 R q3 
q2 1 -> 1 R q3
q3 0 -> 0 R q3
q3 1 -> 1 R q4
q4 0 -> 1 L q0
q4 1 -> 0 L q0
q0 _ -> _ R ACCEPT
q1 _ -> _ R ACCEPT
q2 _ -> _ R ACCEPT
q3 _ -> _ R ACCEPT
q4 _ -> _ R ACCEPT

3. '10' - in step 3 HALT (halt.txt)
q0 1 -> 0 R q1
q1 0 -> 1 L q0
q0 0 -> _ L HALT

4. and an infinite loop for input '10' (infinite_loop.txt)
q0 1 -> 0 R q1
q0 0 -> 1 R q1
q1 0 -> 1 L q0
q1 1 -> 0 L q0

Happy learning!
Dr. Szabolcs Szlávik, 2026.


*** Turing gép szimulátor ***
    Ez egy egyszerű Turing gép szimulátor, amely lehetővé teszi a felhasználók számára, hogy megértsék a Turing gépek működését.
    A program egyszalagos Turing gépet valósít meg, amely egy végtelen hosszú szalagot használ adat tárolására és manipulálására.
    A felhasználók megadhatnak egy bemeneti szót és egy átmenet fájlt, amely meghatározza a gép működését.
    A program lépésenkénti végrehajtást és teljes futtatást is támogat, így a felhasználók láthatják, hogyan változik a szalag és az állapot a műveletek során.

    Az átmeneteket egyszerű szöveges fájlban adhatjuk meg a következő formátumban és szóközökkel elválasztva:
    'a gép jelenlegi állapota'  'a leolvasott szimbólum' -> 'a szalagra írt szimbólum' 'az írófej mozgási iránya' 'a következő állapot'

    Például:
    q0 a -> _ R qa
    a gép a q0 állapotban van, leolvas egy 'a' szimbólumot, akkor a gép egy '_' szimbólumot ír a szalagra, az írófej jobbra mozog, és a gép átmegy a qa állapotba.

    A következő modelleket valósíthatjuk meg:
    1. Két végállapot van: ACCEPT és REJECT. A gép akkor áll meg, ha eléri ezeket az állapotokat.
    2. Csak egy elfogadó 'ACCEPT' állapot van, és a gép akkor áll meg, ha eléri ezt az állapotot. Minden más esetben végtelen ciklusban marad.
    3. Egy megállás 'HALT' állapot van, csak ezt az állapotot kell megadnunk az átmenetfüggvényben

    A Fájl->Megnyitás menüpont segítségével töltsük be az átmeneteket tartalmazó .txt kiterjesztésű fájlt.
    A vizsgált szó mezőbe írjuk be a vizsgálandó szót, majd nyomjuk meg az 'Indítás' gombot.
    Ekkor betöltődik a Turing gépünk.

    A megjelenő új képernyőn léptethetjük a gépet, vagy a futtatást választva a gép addig fog lépkedni, amíg meg nem áll, vagy el nem éri a 200 lépést.
    Ha ismét megnyomjuk a gombot, akkor lefut még 200 ciklus. Tovább próbálkozhatunk, de valószínűleg a gép már nem fog megállni.

    Követelmények:
    Widows 10/11 64 bit

    Telepítés:
    1. Futtassa az .msi telepítőt - TuringMachine-1.0.exe
    2. A Windows futtatható fájlt a C:\Program Files\TuringMachine\TuringMachine.exe útvonalon találja
    3. Kattintson jobb gombbal az ikonra -> Rögzítés a Start menühöz vagy Küldés ide -> Asztal parancsikon létrehozása    
    
    Példák:

    1. Az 'abba' szó felismerése, jegyzet 2.2. ábra (turing_abba.txt)

    q0 a -> _ R qa
    q0 b -> _ R qb
    qa a -> a R qa
    qa b -> b R qa
    qa _ -> _ L q2
    q2 a -> _ L q3
    q2 b -> b R REJECT
    q3 a -> a L q3
    q3 b -> b L q3
    q3 _ -> _ R q0
    qb a -> a R qb
    qb b -> b R qb
    qb _ -> _ L q4
    q4 b -> _ L q3
    q4 a -> a L REJECT
    q0 _ -> _ R ACCEPT


    2. '101101' 9 lépés után elfogadás (turing_101101.txt)

    q0 0 -> 0 R q1
    q0 1 -> 1 R q2
    q1 0 -> 0 R q1
    q1 1 -> 1 R q2
    q2 0 -> 0 R q3
    q2 1 -> 1 R q3
    q3 0 -> 0 R q3
    q3 1 -> 1 R q4
    q4 0 -> 1 L q0
    q4 1 -> 0 L q0
    q0 _ -> _ R ACCEPT
    q1 _ -> _ R ACCEPT
    q2 _ -> _ R ACCEPT
    q3 _ -> _ R ACCEPT
    q4 _ -> _ R ACCEPT

    3. '10' - 3. lépésben HALT (halt.txt)
    q0 1 -> 0 R q1
    q1 0 -> 1 L q0
    q0 0 -> _ L HALT

    4. és egy végtelen ciklus '10' bemenetre (infinite_loop.txt)
    q0 1 -> 0 R q1
    q0 0 -> 1 R q1
    q1 0 -> 1 L q0
    q1 1 -> 0 L q0

    Jó tanulást!
    Dr. Szlávik Szabolcs, 2026.

    MIT License

    Copyright (c) 2026 Dr. Szlávik Szabolcs

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
    to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
    and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
