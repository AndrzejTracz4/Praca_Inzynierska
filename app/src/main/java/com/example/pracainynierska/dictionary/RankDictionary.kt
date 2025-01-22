package com.example.pracainynierska.dictionary

enum class RankDictionary(val level: Int, val displayName: String) {
    REKRUT(1, "Rekrut"),
    SZEREGOWY(2, "Szeregowy"),
    STARSZY_SZEREGOWY(3, "Starszy Szeregowy"),
    KAPRAL(4, "Kapral"),
    STARSZY_KAPRAL(5, "Starszy Kapral"),
    PLUTONOWY(6, "Plutonowy"),
    SIERZANT(7, "Sierżant"),
    STARSZY_SIERZANT(8, "Starszy Sierżant"),
    MAJOR_SIERZANT(9, "Major Sierżant"),
    CHORAZY(10, "Chorąży"),
    STARSZY_CHORAZY(11, "Starszy Chorąży"),
    SZTABOWY_CHORAZY(12, "Sztabowy Chorąży"),
    PODPORUCZNIK(13, "Podporucznik"),
    PORUCZNIK(14, "Porucznik"),
    STARSZY_PORUCZNIK(15, "Starszy Porucznik"),
    KAPITAN(16, "Kapitan"),
    SZTABOWY_KAPITAN(17, "Sztabowy Kapitan"),
    MAJOR(18, "Major"),
    STARSZY_MAJOR(19, "Starszy Major"),
    PODPULKOWNIK(20, "Podpułkownik"),
    PULKOWNIK(21, "Pułkownik"),
    STARSZY_PULKOWNIK(22, "Starszy Pułkownik"),
    GENERAL_BRYGADY(23, "Generał Brygady"),
    GENERAL_DYWIZJI(24, "Generał Dywizji"),
    GENERAL_BRONI(25, "Generał Broni"),
    GENERAL_ARMII(26, "Generał Armii"),
    MARSZALEK(27, "Marszałek"),
    MARSZALEK_POLOWY(28, "Marszałek Polowy"),
    NACZELNY_WODZ(29, "Naczelny Wódz"),
    MISTRZ_STRATEGII(30, "Mistrz Strategii");

    companion object {
        fun fromLevel(level: Int): RankDictionary? {
            return entries.find { it.level == level }
        }
    }
}
