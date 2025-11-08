package controller;

import model.TextAnalyzerModel;
import view.FormPenghitungKata;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.FileWriter;

public class TextAnalyzerController {

    private final FormPenghitungKata view;
    private final TextAnalyzerModel model;

    public TextAnalyzerController(FormPenghitungKata view, TextAnalyzerModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        view.getBtnHitung().addActionListener(e -> hitungSemua());
        view.getBtnCari().addActionListener(e -> cariKata());
        view.getBtnSimpan().addActionListener(e -> simpanFile());

        // Real-time update
        view.getTxtInput().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { hitungSemua(); }
            public void removeUpdate(DocumentEvent e) { hitungSemua(); }
            public void changedUpdate(DocumentEvent e) { hitungSemua(); }
        });
    }

    private void hitungSemua() {
        try {
            String teks = view.getTxtInput().getText().trim();

            // 🔍 Abaikan placeholder
            if (teks.equalsIgnoreCase("Ketik teks di sini...") || teks.isBlank()) {
                view.getLblKata().setText("Kata : 0");
                view.getLblKarakter().setText("Karakter : 0");
                view.getLblKalimat().setText("Kalimat : 0");
                view.getLblParagraf().setText("Paragraf : 0");
                return;
            }

            view.getLblKata().setText("Kata : " + model.hitungKata(teks));
            view.getLblKarakter().setText("Karakter : " + model.hitungKarakter(teks));
            view.getLblKalimat().setText("Kalimat : " + model.hitungKalimat(teks));
            view.getLblParagraf().setText("Paragraf : " + model.hitungParagraf(teks));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Terjadi kesalahan saat menghitung: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cariKata() {
        try {
            String teks = view.getTxtInput().getText().trim();
            String kata = view.getTxtCari().getText().trim();

            // 🔍 Validasi teks dan placeholder
            if (teks.equalsIgnoreCase("Ketik teks di sini...") || teks.isBlank()) {
                JOptionPane.showMessageDialog(view,
                        "Teks masih kosong! Ketik sesuatu dulu sebelum mencari.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (kata.isBlank()) {
                JOptionPane.showMessageDialog(view,
                        "Masukkan kata yang ingin dicari!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int jumlah = model.cariKata(teks, kata);
            view.getLblHasilCari().setText("Kata \"" + kata + "\" ditemukan " + jumlah + " kali");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                "Terjadi kesalahan saat mencari kata: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void simpanFile() {
        try {
            String teks = view.getTxtInput().getText();

            if (teks.isBlank() || teks.equalsIgnoreCase("Ketik teks di sini...")) {
                JOptionPane.showMessageDialog(view,
                        "Teks masih kosong! Tidak bisa disimpan.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 🔥 Buat folder hasil otomatis kalau belum ada
            java.io.File folder = new java.io.File("hasil");
            if (!folder.exists()) {
                folder.mkdir();
            }

            // Simpan file ke dalam folder hasil
            java.io.File file = new java.io.File(folder, "hasil_penghitungan.txt");
            java.io.FileWriter fw = new java.io.FileWriter(file);

            fw.write(teks + "\n\n=== HASIL PERHITUNGAN ===\n" + model.hasilLengkap(teks));
            fw.close();

            JOptionPane.showMessageDialog(view,
                    "File berhasil disimpan di folder 'hasil'!\n\nLokasi: " + file.getAbsolutePath(),
                    "Berhasil", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Terjadi kesalahan saat menyimpan file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
