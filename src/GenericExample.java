/* A példák nested classként lettek kidolgozva, azért, hogy egy kódban átnézhetők legyenek. Valós szituációkban az
osztályok külön fájlokba kerülnek.
 */

public class GenericExample {

    /* 1. példa equals függvényt készítünk, ami két input paraméter egyenlőségét dönti el, ha nnem használunk genericet,
    akkor külön meg kell írnunk minden típusra.
     */
    static boolean equals(int a,int b){
        return a==b;
    }

    static boolean equals(String a,String b){
        return a.equals(b);
    }

    static void main11(){
        int a=5;
        int b=6;
        System.out.println(equals(a,b));
        String sa="Hello";
        String sb="Hi";
        System.out.println(equals(sa,sb)); // a függvény paramétereinek típusa alapján dönti el, melyik függvényt hívja
    }

    /* A generic más néven típus paraméter arra szolgál, hogy ne kelljen hasonló a kódot sokszor leírni,
    mégis szigoruan tipusos maradjon a kód.
    - Az egész függvénydefiníció elé kell írni a típus paramétert.
    - Majd ezt használhatod a függvény paramétereiben, a függvény törzsében.
     */

    static <T> boolean equals_generic(T a,T b){
        return a.equals(b);
    }

    static void main12(){
        int a=5;
        int b=6;
        System.out.println(equals_generic(a,b));
        String sa="Hello";
        String sb="Hi";
        System.out.println(equals_generic(sa,sb)); // a függvény paramétereinek típusa alapján dönti el, hogy T mi lesz
    }

    /* 2. példa Sokszor jó lenne tudni valamit a T osztályról, például, hogy milyen műveleteket lehet vele végezni, mert
    a függvény implementációjában ezt fel kellene használni. Például, ha max függvényt akarunk készíteni, akkor össze
    kell tudnunk hasonlítani a típus két értékét. Ehhez interface-eket tudunk használni.
    - Az extends Comarable-vel megadjuk, hogy csak olyan típusokra működik a függvény, amelynek értékei
    összehasonlíthatók egymással, azaz a Comparable interface-t implementálja az osztály.
    - Ezután használhatjuk a Comparable interface compareTo függvényét
     */

    static <T extends Comparable<T>> T max_generic(T a,T b) {
        if (a.compareTo(b) < 0) {
            return b;
        } else {
            return a;
        }
    }

    static void main2(){
        int a=5;
        int b=6;
        System.out.println(max_generic(a,b));
        String sa="Hello";
        String sb="Hi";
        System.out.println(max_generic(sa,sb)); // a függvény paramétereinek típusa alapján dönti el, hogy T mi lesz
    }

    /* 3. példa a genericek leggyakoribb használata az összetett adatszerkezetek, melyek különböző típusú elemekre értelmezhetők,
    de az implementációjuk minden elemtípusra ugyanolyan. Ezért az elemtípust típusparaméterként érdemes átadni.
    - Osztályok esetén az osztálynév végére kell rakni a típusparaméter(eke)t. Egy üres '<>' is elég.
    - Egy ilyen osztály objektumának létrehozásánál, a new híváésnál meg kell adni a generic paramétereket
    - Az objektum deklarációjánál ezt ?-el helyettesíthetjük
     */
    static class KeyValuePair<K,V> {
        K key;
        V value;
        KeyValuePair(K k,V v){
            key=k;
            value=v;
        }
    }

    static void main3(){
        KeyValuePair<?,?> kv1=new KeyValuePair<Integer,String>(5,"Öt");
        KeyValuePair<String,Integer> kv2=new KeyValuePair<String,Integer>("Öt",5);
        KeyValuePair<String,Integer> kv3=new KeyValuePair<>("Öt",5);
    }

    /* 4. példa.Implementálhatunk saját ízlésünk szerinti összehasonlítást is. A JAVA védekezik az ilyen galádságok ellen,
    pélául az Integer classból nem lehet örökölni.
    - Az osztály végén az implements Comparable<MyInteger> azt jelenti, hogy saját magával össze lehet hasonlítani
    - Ha ezt kihagyom,akkor ilyen hibaüzenetet kapok:
java: method max_generic in class GenericExample cannot be applied to given types;
  required: T,T
  found:    GenericExample.MyInteger,GenericExample.MyInteger
  reason: inference variable T has incompatible bounds
    lower bounds: java.lang.Comparable<T>
    lower bounds: GenericExample.MyInteger

    A fordító a megadott és a várt paramétertípusokat megpróbálja értelmesen összepárosítani és abból kitalálni, hogy
    mi lehet a T. Ha nem sikerül, akkor jön a hibaüzenet, amit lehet bogarászni.
     */

    static class MyInteger implements Comparable<MyInteger>{
        Integer value;

        public int compareTo(MyInteger b){
            return -value.compareTo(b.value);
        }

        public MyInteger(int a){
            value=a;
        }
    }

    static void main4(){
        System.out.println(max_generic(5,6));
        System.out.println(max_generic(new MyInteger(5),new MyInteger(6)).value); // hoppá, 5>6
        // System.out.println(max_generic(new MyInteger(5),6)); ez nem fordul le
    }

    public static void main(String[] args){
        main11();
        main12();
        main3();
        main4();
    }
}
