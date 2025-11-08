package model;

public class TextAnalyzerModel {

    public int hitungKata(String teks) {
        if (teks == null || teks.isBlank()) return 0;
        return teks.trim().split("\\s+").length;
    }

    public int hitungKarakter(String teks) {
        if (teks == null) return 0;
        return teks.length();
    }

    public int hitungKalimat(String teks) {
        if (teks == null || teks.isBlank()) return 0;
        return teks.split("[.!?]+").length;
    }

    public int hitungParagraf(String teks) {
        if (teks == null || teks.isBlank()) return 0;
        return teks.split("\\n+").length;
    }

    public int cariKata(String teks, String kata) {
        if (teks == null || teks.isBlank() || kata.isBlank()) return 0;
        return teks.toLowerCase().split("\\b" + kata.toLowerCase() + "\\b").length - 1;
    }

    public String hasilLengkap(String teks) {
        return "Kata : " + hitungKata(teks) + "\n" +
               "Karakter : " + hitungKarakter(teks) + "\n" +
               "Kalimat : " + hitungKalimat(teks) + "\n" +
               "Paragraf : " + hitungParagraf(teks);
    }
}
