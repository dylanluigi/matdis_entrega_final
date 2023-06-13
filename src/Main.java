import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1:
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
    /*
     * Aquí teniu els exercicis del Tema 1 (Lògica).
     *
     * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
     * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
     * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
     * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
     * similarment s'avaluen com `p.test(x, y)`.
     *
     * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
     * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
     * petit com per poder provar tots els casos que faci falta).
     */
    static class Tema1 {

        /*
         * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
         */
        static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
            // Itera sobre cada element de 'universe'
            for(int x : universe){
                // Si el predicat 'p' és cert per a 'x'
                if(p.test(x)){
                    // Contarem el nombre de 'y' per al qual 'q(x, y)' és cert. Inicialment, el comptador està a 0.
                    int contadorUnicoY = 0;
                    // Itera sobre cada element de 'universe' per a possibles 'y'
                    for(int y : universe){
                        // Si 'q(x, y)' és cert, incrementa el comptador
                        if(q.test(x, y)){
                            contadorUnicoY++;
                            // Si es troba més d'un 'y' per al qual 'q(x, y)' és cert, retorna false (ja que ha d'existir exactament un 'y')
                            if(contadorUnicoY > 1){
                                return false;
                            }
                        }
                    }
                    // Si no es troba cap 'y' per al qual 'q(x, y)' és cert, retorna false (ja que ha d'existir exactament un 'y')
                    if(contadorUnicoY != 1) {
                        return false;
                    }
                }
            }
            // Si no es troben excepcions en la comprovació, retorna true.
            return true;
        }


        /*
         * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
         */
        static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
            int contadorUnicoX = 0;
            int ultimoXTrue = -1;

            for(int x : universe) {
                boolean todosValidos = true;
                for(int y : universe) {
                    if(p.test(y) && !q.test(x, y)){
                        todosValidos = false;
                        break;
                    }
                }

                if(todosValidos){
                    contadorUnicoX++;
                    if(contadorUnicoX > 1){
                        return false;
                    }
                    ultimoXTrue = x;
                }
            }

            if(contadorUnicoX != 1){
                return false;
            }

            // check that there exists some y where P(y) is true
            for(int y : universe){
                if(p.test(y)){
                    return true;
                }
            }

            return false;
        }

        /*
         * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
         */
        static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
            for(int x : universe) {
                for(int y : universe) {
                    boolean todoZvalido = true;
                    for(int z : universe) {
                        // XOR
                        if((p.test(x,z) && q.test(y,z)) || (!p.test(x,z) && !q.test(y,z))){
                            todoZvalido = false;
                            break;
                        }
                    }

                    if(todoZvalido) {
                        return true;
                    }
                }
            }
            return false;
        }


        /*
         * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
         */
        static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
            boolean todoPSatisfecho = true;
            for(int x : universe) {
                if(!p.test(x)){
                    todoPSatisfecho = false;
                    break;
                }
            }

            if(todoPSatisfecho){
                for(int x : universe){
                    if(!q.test(x)){
                        return false;
                    }
                }
            }

            return true;
        }


        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // ∀x ∃!y. P(x) -> Q(x,y) ?

            assertThat(
                    exercici1(
                            new int[] { 2, 3, 5, 6 },
                            x -> x != 4,
                            (x, y) -> x == y
                    )
            );

            assertThat(
                    !exercici1(
                            new int[] { -2, -1, 0, 1, 2, 3 },
                            x -> x != 0,
                            (x, y) -> x * y == 1
                    )
            );

            // Exercici 2
            // ∃!x ∀y. P(y) -> Q(x,y) ?

            assertThat(
                    exercici2(
                            new int[] { -1, 1, 2, 3, 4 },
                            y -> y <= 0,
                            (x, y) -> x == -y
                    )
            );

            assertThat(
                    !exercici2(
                            new int[] { -2, -1, 1, 2, 3, 4 },
                            y -> y < 0,
                            (x, y) -> x * y == 1
                    )
            );

            // Exercici 3
            // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

            assertThat(
                    exercici3(
                            new int[] { 2, 3, 4, 5, 6, 7, 8 },
                            (x, z) -> z % x == 0,
                            (y, z) -> z % y == 1
                    )
            );

            assertThat(
                    !exercici3(
                            new int[] { 2, 3 },
                            (x, z) -> z % x == 1,
                            (y, z) -> z % y == 1
                    )
            );

            // Exercici 4
            // (∀x. P(x)) -> (∀x. Q(x)) ?

            assertThat(
                    exercici4(
                            new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
                            x -> x % 2 == 0, // x és múltiple de 2
                            x -> x % 4 == 0 // x és múltiple de 4
                    )
            );

            assertThat(
                    !exercici4(
                            new int[] { 0, 2, 4, 6, 8, 16 },
                            x -> x % 2 == 0, // x és múltiple de 2
                            x -> x % 4 == 0 // x és múltiple de 4
                    )
            );
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 2 (Conjunts).
     *
     * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
     * conjunt de conjunts d'enters tendrà tipus int[][].
     *
     * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
     * només té dos elements. Per exemple
     *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
     * i també donarem el conjunt on està definida, per exemple
     *   int[] a = {0,1,2};
     *
     * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
     * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
     * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
     */
    static class Tema2 {
        /*
         * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
         *
         * Podeu soposar que `a` està ordenat de menor a major.
         */
        static boolean exercici1(int[] a, int[][] rel) {
            // Reflexivo
            for (int i : a) {
                if (!hayRelacion(i, i, rel)) {
                    return false;
                }
            }

            // Simetria
            for (int[] par : rel) {
                if (!hayRelacion(par[1], par[0], rel)) {
                    return false;
                }
            }

            // Transitivo
            for (int i : a) {
                for (int j : a) {
                    if (hayRelacion(i, j, rel)) {
                        for (int k : a) {
                            if (hayRelacion(j, k, rel) && !hayRelacion(i, k, rel)) {
                                return false;
                            }
                        }
                    }
                }
            }

            return true;
        }

        // Relacion
        static boolean hayRelacion(int x, int y, int[][] rel) {
            for (int[] par : rel) {
                if (par[0] == x && par[1] == y) {
                    return true;
                }
            }
            return false;
        }


        /*
         * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
         * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
         *
         * Podeu soposar que `a` està ordenat de menor a major.
         */
        static int exercici2(int[] a, int[][] rel) {
            //usamos Ej 1 para mirar si hay equivalencia
            if (!exercici1(a, rel)) {
                return -1;
            }


            List<List<Integer>> claseEquivalencia = new ArrayList<>();
            for (int i : a) {
                claseEquivalencia.add(new ArrayList<>(Arrays.asList(i)));
            }


            for (int[] pair : rel) {
                int i = buscarEquiva(pair[0], claseEquivalencia);
                int j = buscarEquiva(pair[1], claseEquivalencia);


                if (i != j) {
                    claseEquivalencia.get(i).addAll(claseEquivalencia.get(j));
                    claseEquivalencia.remove(j);
                }
            }


            return claseEquivalencia.size();
        }

        static int buscarEquiva(int i, List<List<Integer>> classeEquival) {
            for (int j = 0; j < classeEquival.size(); j++) {
                if (classeEquival.get(j).contains(i)) {
                    return j;
                }
            }
            return -1;
        }



        /*
         * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
         *
         * Podeu soposar que `a` i `b` estan ordenats de menor a major.
         */
        static boolean exercici3(int[] a, int[] b, int[][] rel) {
            for (int i = 0; i < a.length; i++) {
                int cont = 0;
                for (int[] par : rel) {
                    if (par[0] == a[i]) {
                        cont++;
                    }
                }
                if (cont != 1) {
                    return false;
                }
            }
            return true;
        }



        /*
         * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
         * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
         * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de `codom`.
         * - En qualsevol altre cas, retornau 0.
         *
         * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
         */
        static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
            int[] img = new int[dom.length];

            for (int i = 0; i < dom.length; i++) {
                img[i] = f.apply(dom[i]);
            }

            boolean esExhaustiva = true;
            int maxAntiImg = 0;

            for (int y : codom) {
                int contAntiImg = 0;
                for (int imagen : img) {
                    if (y == imagen) {
                        contAntiImg++;
                    }
                }

                if (contAntiImg == 0) {
                    esExhaustiva = false;
                } else {
                    maxAntiImg = Math.max(maxAntiImg, contAntiImg);
                }
            }

            if (esExhaustiva) {
                return maxAntiImg;
            }


            boolean esInyectiva = true;
            for (int i = 0; i < img.length; i++) {
                for (int j = i + 1; j < img.length; j++) {
                    if (img[i] == img[j]) {
                        esInyectiva = false;
                        break;
                    }
                }
            }

            if (esInyectiva) {
                int imgCardinal = 0;
                for (int i : img) {
                    boolean trobat = false;
                    for (int y : codom) {
                        if (i == y) {
                            trobat = true;
                            break;
                        }
                    }

                    if (trobat) {
                        imgCardinal++;
                    }
                }

                return imgCardinal - codom.length;
            }


            return 0;
        }



        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            // Exercici 1
            // `rel` és d'equivalencia?

            assertThat(
                    exercici1(
                            new int[] { 0, 1, 2, 3 },
                            new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
                    )
            );

            assertThat(
                    !exercici1(
                            new int[] { 0, 1, 2, 3 },
                            new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
                    )
            );

            // Exercici 2
            // si `rel` és d'equivalència, quants d'elements té el seu quocient?

            final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

            assertThat(
                    exercici2(
                            int09,
                            generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
                    )
                            == 3
            );

            assertThat(
                    exercici2(
                            new int[] { 1, 2, 3 },
                            new int[][] { {1, 1}, {2, 2} }
                    )
                            == -1
            );

            // Exercici 3
            // `rel` és una funció?

            final int[] int05 = { 0, 1, 2, 3, 4, 5 };

            assertThat(
                    exercici3(
                            int05,
                            int09,
                            generateRel(int05, int09, (x, y) -> x == y)
                    )
            );

            assertThat(
                    !exercici3(
                            int05,
                            int09,
                            generateRel(int05, int09, (x, y) -> x == y/2)
                    )
            );

            // Exercici 4
            // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
            // sino, |im f| - |codom| si és injectiva
            // sino, 0

            assertThat(
                    exercici4(
                            int09,
                            int05,
                            x -> x / 4
                    )
                            == 0
            );

            assertThat(
                    exercici4(
                            int05,
                            int09,
                            x -> x + 3
                    )
                            == int05.length - int09.length
            );

            assertThat(
                    exercici4(
                            int05,
                            int05,
                            x -> (x + 3) % 6
                    )
                            == 1
            );
        }

        /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
        static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
            ArrayList<int[]> rel = new ArrayList<>();

            for (int a : as) {
                for (int b : bs) {
                    if (pred.test(a, b)) {
                        rel.add(new int[] { a, b });
                    }
                }
            }

            return rel.toArray(new int[][] {});
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 3 (Grafs).
     *
     * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
     * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
     * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
     *
     *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
     *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
     *
     * Podeu suposar que cap dels grafs té llaços.
     */
    static class Tema3 {
        /*
         * Retornau l'ordre menys la mida del graf (no dirigit).
         */
        static int exercici1(int[][] g) {
            int ordre = g.length;

            int tam = 0;
            for (int i = 0; i < ordre; i++) {
                tam += g[i].length;
            }
            tam /= 2;
            return ordre - tam;
        }




        /*
         * Suposau que el graf (no dirigit) és connex. És bipartit?
         */
        static boolean exercici2(int[][] g) {
            int n = g.length;
            int[] colors = new int[n];
            Arrays.fill(colors, -1);

            for (int i = 0; i < n; ++i) {
                if (colors[i] == -1) {
                    if (!esColorValid(g, colors, i, 0)) {
                        return false;
                    }
                }
            }
            return true;
        }

        static boolean esColorValid(int[][] g, int[] colors, int v, int c) {
            if (colors[v] != -1) {
                return colors[v] == c;
            }
            colors[v] = c;

            for (int veïnat : g[v]) {
                if (!esColorValid(g, colors, veïnat, 1 - c)) {
                    return false;
                }
            }
            return true;
        }


        /*
         * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
         * vèrtex i-èssim.
         */
        static int exercici3(int[][] g, int i) {
            int n = g.length;
            boolean[] visitado = new boolean[n];
            Arrays.fill(visitado, false);

            return dfs(g, visitado, i);
        }

        static int dfs(int[][] g, boolean[] visitado, int v) {
            visitado[v] = true;

            int cont = 0;
            boolean conHijo = false;

            for (int vecino : g[v]) {
                if (!visitado[vecino]) {
                    conHijo = true;
                    cont += dfs(g, visitado, vecino);
                }
            }

            if (!conHijo) {
                cont++;
            }

            return cont;
        }



        /*
         * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
         * del graf subjacent. Suposau que totes les arestes tenen pes 1.
         */

        static int exercici4(int[][] g) {
            int n = g.length;
            int[] prof = new int[n];
            int[] profMaxHijo = new int[n];

            boolean[] visitado = new boolean[n];
            Arrays.fill(visitado, false);

            for(int i = 0; i < n; i++) {
                if(!visitado[i]) {
                    dfs(i, g, prof, profMaxHijo, visitado);
                }
            }

            return Arrays.stream(prof).max().getAsInt();
        }

        static void dfs(int v, int[][] g, int[] prof, int[] profMaxHijo, boolean[] visitado) {
            visitado[v] = true;
            int max1 = -1, max2 = -1;

            for(int i : g[v]) {
                if(!visitado[i]) {
                    dfs(i, g, prof, profMaxHijo, visitado);

                    if(profMaxHijo[i] >= max1) {
                        max2 = max1;
                        max1 = profMaxHijo[i];
                    }
                    else if(profMaxHijo[i] > max2) {
                        max2 = profMaxHijo[i];
                    }
                }
            }

            profMaxHijo[v] = max1 + 1;
            prof[v] = max1 + max2 + 2;
        }



        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            final int[][] undirectedK6 = {
                    { 1, 2, 3, 4, 5 },
                    { 0, 2, 3, 4, 5 },
                    { 0, 1, 3, 4, 5 },
                    { 0, 1, 2, 4, 5 },
                    { 0, 1, 2, 3, 5 },
                    { 0, 1, 2, 3, 4 },
            };

      /*
         1
      4  0  2
         3
      */
            final int[][] undirectedW4 = {
                    { 1, 2, 3, 4 },
                    { 0, 2, 4 },
                    { 0, 1, 3 },
                    { 0, 2, 4 },
                    { 0, 1, 3 },
            };

            // 0, 1, 2 | 3, 4
            final int[][] undirectedK23 = {
                    { 3, 4 },
                    { 3, 4 },
                    { 3, 4 },
                    { 0, 1, 2 },
                    { 0, 1, 2 },
            };

      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
            final int[][] directedG1 = {
                    { 1, 2 }, // 0
                    { 3 },    // 1
                    { 3, 8 }, // 2
                    { 4 },    // 3
                    { 5, 6 }, // 4
                    {},       // 5
                    {},       // 6
                    { 0 },    // 7
                    {},
            };


      /*
              0
         1    2     3
            4   5   6
           7 8
      */

            final int[][] directedRTree1 = {
                    { 1, 2, 3 }, // 0 = r
                    {},          // 1
                    { 4, 5 },    // 2
                    { 6 },       // 3
                    { 7, 8 },    // 4
                    {},          // 5
                    {},          // 6
                    {},          // 7
                    {},          // 8
            };

      /*
            0
            1
         2     3
             4   5
                6  7
      */

            final int[][] directedRTree2 = {
                    { 1 },
                    { 2, 3 },
                    {},
                    { 4, 5 },
                    {},
                    { 6, 7 },
                    {},
                    {},
            };

            assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
            assertThat(exercici1(undirectedW4) == 5 - 2*4);

            assertThat(exercici2(undirectedK23));
            assertThat(!exercici2(undirectedK6));

            assertThat(exercici3(directedG1, 0) == 3);
            assertThat(exercici3(directedRTree1, 2) == 3);

            assertThat(exercici4(directedRTree1) == 5);
            assertThat(exercici4(directedRTree2) == 4);
        }
    }

    /*
     * Aquí teniu els exercicis del Tema 4 (Aritmètica).
     *
     * Per calcular residus podeu utilitzar l'operador %, però anau alerta amb els signes.
     * Podeu suposar que cada vegada que se menciona un mòdul, és major que 1.
     */
    static class Tema4 {
        /*
         * Donau la solució de l'equació
         *
         *   ax ≡ b (mod n),
         *
         * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu suposar que n > 1.
         *
         * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
         * Si no en té, retornau null.
         */
        static int gcd(int a, int b) {
            if (b == 0) {
                return a;
            }
            return gcd(b, a % b);
        }

        static int[] extGCD(int a, int b) {
            if (a == 0) {
                return new int[]{b, 0, 1};
            } else {
                int[] resultado = extGCD(b % a, a);
                int x = resultado[2] - (b / a) * resultado[1];
                int y = resultado[1];
                return new int[]{resultado[0], x, y};
            }
        }

        static int[] exercici1(int a, int b, int n) {
            a = (a % n + n) % n;
            b = (b % n + n) % n;
            if (gcd(a, n) != 1) {
                if (b % gcd(a, n) != 0) {
                    return null;
                } else {
                    a /= gcd(a, n);
                    b /= gcd(a, n);
                    n /= gcd(a, n);
                }
            }
            int[] res = extGCD(a, n);
            int x = res[1];
            x = (x % n + n) % n;
            int result = (x * b) % n;
            return new int[]{result, n};
        }

        /*
         * Donau la solució (totes) del sistema d'equacions
         *
         *  { x ≡ b[0] (mod n[0])
         *  { x ≡ b[1] (mod n[1])
         *  { x ≡ b[2] (mod n[2])
         *  { ...
         *
         * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També podeu suposar
         * que els dos arrays tenen la mateixa longitud.
         *
         * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
         * Si no en té, retornau null.
         */
        static int[] exercici2a(int[] b, int[] n) {
            int longitud = b.length;
            int N = 1;
            for(int i = 0; i < longitud; i++) {
                N *= n[i];
            }

            int resultado = 0;
            for(int i = 0; i < longitud; i++) {
                int Ni = N / n[i];
                int[] inverso = extGCD(Ni, n[i]);
                if(inverso[0] != 1) {
                    // Check if solution exists
                    if(b[i] % inverso[0] != 0) {
                        return null;
                    } else {
                        Ni /= inverso[0];
                        b[i] /= inverso[0];
                        inverso = extGCD(Ni, n[i]);
                    }
                }
                resultado += ((long) b[i] * Ni % N * inverso[1] % N);
                resultado %= N;
            }
            if(resultado < 0) resultado += N;

            return new int[]{resultado, N};
        }

        /*
         * Donau la solució (totes) del sistema d'equacions
         *
         *  { a[0]·x ≡ b[0] (mod n[0])
         *  { a[1]·x ≡ b[1] (mod n[1])
         *  { a[2]·x ≡ b[2] (mod n[2])
         *  { ...
         *
         * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que n[i] > 1. També
         * podeu suposar que els tres arrays tenen la mateixa longitud.
         *
         * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
         * Si no en té, retornau null.
         */
        static int[] exercici2b(int[] a, int[] b, int[] n) {
            int longitud = a.length;
            int[] newB = new int[longitud];
            int[] newN = new int[longitud];

            // Transform the equation a[i]*x ≡ b[i] (mod n[i]) into x ≡ b'[i] (mod n'[i])
            for(int i = 0; i < longitud; i++) {
                if(a[i] < 0) {
                    a[i] += n[i];
                }
                if(b[i] < 0) {
                    b[i] += n[i];
                }

                int gcd = gcd(a[i], n[i]);
                if(gcd != 1) {
                    // Check if solution exists
                    if(b[i] % gcd != 0) {
                        return null;
                    } else {
                        a[i] /= gcd;
                        b[i] /= gcd;
                        n[i] /= gcd;
                    }
                }

                int[] inverso = extGCD(a[i], n[i]);
                newB[i] = (int)(((long)b[i] * inverso[1] % n[i] + n[i]) % n[i]);
                newN[i] = n[i];
            }

            return exercici2a(newB, newN);
        }

        /*
         * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers, ordenada de menor a
         * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
         *
         * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
         *
         * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar la força bruta
         * (el que coneixeu com el mètode manual d'anar provant).
         */
        static ArrayList<Integer> exercici3a(int n) {
            ArrayList<Integer> resultado = new ArrayList<>();
            for(int i = 2; i <= n; i++) {
                while(n % i == 0) {
                    resultado.add(i);
                    n /= i;
                }
            }
            return resultado;
        }

        /*
         * Retornau el nombre d'elements invertibles a Z mòdul n³.
         *
         * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però n³ no té perquè.
         * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
         *
         * No podeu utilitzar `long` per solucionar aquest problema. Necessitareu l'exercici 3a.
         */
        static ArrayList<Integer> factorizacionPrimero(int n) {
            ArrayList<Integer> factores = new ArrayList<>();
            for(int i = 2; i <= n; i++) {
                while(n % i == 0) {
                    factores.add(i);
                    n /= i;
                }
            }
            return factores;
        }

        static int phiOfPower(int p, int k) {
            // φ(p^k) = p^k - p^(k-1) per a un primer p
            return (int) (Math.pow(p, k) - Math.pow(p, k-1));
        }

        static int exercici3b(int n) {
            ArrayList<Integer> factoresPrimeros = factorizacionPrimero(n);
            int resultado = 1;
            int contador = 0;
            int previo = -1;

            for (int p : factoresPrimeros) {
                if (p == previo) {
                    contador++;
                } else {
                    if (contador > 0) {
                        resultado *= phiOfPower(previo, 3 * contador);
                    }
                    previo = p;
                    contador = 1;
                }
            }

            if (contador > 0) {
                resultado *= phiOfPower(previo, 3 * contador);
            }
            return resultado;
        }



        /*
         * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
         */
        static void tests() {
            assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
            assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
            assertThat(exercici1(2, 3, 6) == null);

            assertThat(
                    exercici2a(
                            new int[] { 1, 0 },
                            new int[] { 2, 4 }
                    )
                            == null
            );

            assertThat(
                    Arrays.equals(
                            exercici2a(
                                    new int[] { 3, -1, 2 },
                                    new int[] { 5,  8, 9 }
                            ),
                            new int[] { 263, 360 }
                    )
            );

            assertThat(
                    exercici2b(
                            new int[] { 1, 1 },
                            new int[] { 1, 0 },
                            new int[] { 2, 4 }
                    )
                            == null
            );

            assertThat(
                    Arrays.equals(
                            exercici2b(
                                    new int[] { 2,  -1, 5 },
                                    new int[] { 6,   1, 1 },
                                    new int[] { 10,  8, 9 }
                            ),
                            new int[] { 263, 360 }
                    )
            );

            assertThat(exercici3a(10).equals(List.of(2, 5)));
            assertThat(exercici3a(1291).equals(List.of(1291)));
            assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19 )));

            assertThat(exercici3b(10) == 400);

            // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense calcular n³.
            assertThat(exercici3b(1292) == 961_496_064);

            // Aquest exemple té el resultat fora de rang
            //assertThat(exercici3b(1291) == 2_150_018_490);
        }
    }

    /*
     * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
     * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
     * compte, però és molt recomanable).
     *
     * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
     */
    public static void main(String[] args) {
        Tema1.tests();
        Tema2.tests();
        Tema3.tests();
        Tema4.tests();
    }

    /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
    static void assertThat(boolean b) {
        if (!b)
            throw new AssertionError();
    }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :