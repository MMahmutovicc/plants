package com.example.spirala

fun getPLants(): MutableList<Biljka> {
    return mutableListOf(
        Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
            MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Nana (Mentha spicata)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Jogurt sa voćem", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
        ),
        Biljka(
            naziv = "Kamilica (Matricaria chamomilla)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Čaj od kamilice"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
        ),
        Biljka(
            naziv = "Ružmarin (Rosmarinus officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,
            MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Pečeno pile", "Grah","Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.SLJUNOVITO, Zemljiste.KRECNJACKO)
        ),
        Biljka(
            naziv = "Lavanda (Lavandula angustifolia)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE,
            MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Jogurt sa voćem"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
        ),
        //1.
        Biljka(
            naziv = "Nar (Punica granatum)",
            porodica = "Lythriceae (vrbičevke)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba. Kora stabla, grana i korijena je otrovna.",
            medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU,MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa =  ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Sirup od nara", "Džem od nara", "Kolač od čokolade i nara"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA,KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Peršun (Petroselinum crispum)",
            porodica = "Apiceae (štitarice)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba. Pretjerana upotreba može uzrokovati probleme sa jetrene i bubrežne probleme.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO,MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.KORIJENASTO,
            jela = listOf("Pesto od peršuna"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA,Zemljiste.ILOVACA)
        ),
        Biljka(
            naziv = "Đumbir (Zingiber officinale)",
            porodica = "Zingiberaceae (đumbirovke)",
            medicinskoUpozorenje = "Moguće posljedice upotrebe su stomačni problemi i povećanje krvarenja.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTIVBOLOVA,MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Sok od đumbira", "Krem juha od mrkve i đumbira"),
            klimatskiTipovi = listOf(KlimatskiTip.SUBTROPSKA,KlimatskiTip.TROPSKA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA,Zemljiste.PJESKOVITO)
        ),
        Biljka(
            naziv = "Aronija (Aronia)",
            porodica = "Rosaceae (Ružovke)",
            medicinskoUpozorenje = "Moguće posljedice upotrebe su problemi sa probavom",
            medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPRITISKA,MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Džem od aronije", "Sok od aronije", "Sirup od aronije"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA,KlimatskiTip.PLANINSKA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA,Zemljiste.PJESKOVITO)
        ),
        Biljka(
            naziv = "Kopriva (Urtica dioica)",
            porodica = "Urticaceae (Koprivovke)",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTIVBOLOVA,MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Krem juha od koprive", "Rižoto od koprive", "Čaj od koprive"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA,KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA,Zemljiste.PJESKOVITO),
            medicinskoUpozorenje = "Može uzrokovati stomačne probleme, te iritaciju kože prilikom dodira. Ne preporučuje se trudnicama."
        )
    )
}