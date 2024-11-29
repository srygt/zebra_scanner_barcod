README für das Lagerverwaltungsprojekt
Lagerverwaltung

Ein Android-basiertes Lagerverwaltungssystem, das Barcodescanner und die Integration mit Zebra DataWedge nutzt, um Produkte effizient zu scannen und zu verwalten. Dieses Projekt kombiniert Flutter für die Frontend-Entwicklung und nativen Java/Kotlin-Code für die Barcode-Scanner-Integration.
Inhalt

    Voraussetzungen
    Installation
    Barcode-Scanner-Integration
    Testen der Anwendung
    Funktionen
    Fehlerbehebung

Voraussetzungen

Bevor Sie mit der Installation beginnen, stellen Sie sicher, dass folgende Tools und Technologien vorhanden sind:

    Flutter SDK (Version 3.10 oder höher)
    Android Studio (mit konfiguriertem Emulator oder Zebra-Gerät)
    Java Development Kit (JDK) (Version 11 oder höher)
    Ein Zebra-Scanner mit aktivierter DataWedge-Funktionalität
    Ein physisches Android-Gerät (falls kein Emulator verwendet wird)

Installation

    Repository klonen
    Klonen Sie das Repository lokal:

git clone <REPO_URL>
cd lagerverwaltung

Abhängigkeiten installieren
Installieren Sie alle erforderlichen Flutter-Pakete:

flutter pub get

Native Abhängigkeiten konfigurieren
Öffnen Sie das Android-Verzeichnis im Projekt:

cd android

Führen Sie das folgende Kommando aus, um sicherzustellen, dass alle nativen Abhängigkeiten korrekt heruntergeladen werden:

./gradlew clean build

DataWedge-Profil einrichten
Stellen Sie sicher, dass Ihr Zebra-Scanner ein aktives DataWedge-Profil hat, das auf die folgende Intent-Action eingestellt ist:

com.symbol.datawedge.DWDEMO

App ausführen
Starten Sie die App:

    flutter run

Barcode-Scanner-Integration

Die Barcode-Scanner-Integration erfolgt über folgende zwei Flutter-Kanäle:

    MethodChannel
    Wird verwendet, um den Scanner zu starten:
        Kanalname: com.tabakwelt.lagerverwaltung/scanner_command
        Methode: startScanning

    EventChannel
    Empfängt Scandaten in Echtzeit:
        Kanalname: com.tabakwelt.lagerverwaltung/scanner_scan

Die Konfiguration erfolgt in der Datei MainActivity.kt im android-Verzeichnis.
Testen der Anwendung

    Test auf einem Zebra-Gerät
    Verbinden Sie das Gerät per USB und starten Sie die App:

flutter run --release

Verwenden Sie den physischen Scanner oder den Soft-Trigger, um Barcodes zu scannen.

Test mit Emulator
Hinweis: Die Barcode-Scanner-Funktionalität kann nicht auf einem Emulator getestet werden. Verwenden Sie stattdessen eine Zebra-Testbibliothek oder Mock-Daten.

Unit-Tests
Führen Sie die Tests mit folgendem Befehl aus:

    flutter test

Funktionen

    Barcode-Scannen
    Scannen und Verwalten von Produkten mit Zebra DataWedge.

    Produktvalidierung
    Vergleicht gescannte Barcodes mit der erwarteten Produkt- und Mengeninformation.

    Dynamische UI
    Aktualisiert die Benutzeroberfläche in Echtzeit basierend auf Scandaten.

    Fehlermeldungen
    Zeigt dem Benutzer Warnungen an, wenn Barcodes nicht übereinstimmen oder die maximale Menge überschritten wird.

Fehlerbehebung

    Kein Scanner erkannt
        Stellen Sie sicher, dass Ihr Zebra-Scanner mit DataWedge korrekt konfiguriert ist.
        Überprüfen Sie die PROFILE_INTENT_ACTION in der Datei MainActivity.kt.

    Flutter-Abhängigkeiten fehlen
        Stellen Sie sicher, dass alle Pakete installiert sind:

        flutter pub get

    App stürzt ab
        Überprüfen Sie die AndroidManifest.xml auf korrekte Intent-Konfiguration.
        Stellen Sie sicher, dass alle nativen Berechtigungen für den Barcode-Scanner erteilt wurden.

    UI-Fehler (z. B. Overflow)
        Vergewissern Sie sich, dass die Benutzeroberfläche scrollfähig ist (siehe SingleChildScrollView im Dialog).

Mitwirkende

    Entwickler: Serdar Yigit
    Kontakt: [E-Mail-Adresse einfügen]

Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Weitere Informationen finden Sie in der Datei LICENSE.
