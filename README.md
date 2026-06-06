# Meniny Widget 📅

Tmavý minimalistický Android widget na plochu, ktorý zobrazuje:
- **Deň v týždni** (Piatok, Sobota…)
- **Dátum** (6. júna 2026)
- **Meniny** podľa slovenského kalendára

Aktualizuje sa automaticky každú polnoc + po reštarte telefónu. Klik na widget = manuálny refresh. Nie je to notifikácia, takže sa nič nestratí — sedí to priamo na ploche ako počasie.

---

## 🚀 Ako z toho spraviť APK (bez Android Studia)

### 1. Vytvor GitHub repozitár
- Choď na https://github.com → **New repository**
- Názov napr. `meniny-widget`, daj **Public** (Actions sú zadarmo)
- Vytvor

### 2. Nahraj projekt
Najjednoduchšie cez web:
- V prázdnom repe klikni **uploading an existing file**
- Rozbaľ tento ZIP na PC a presuň **celý obsah** (nie nadradený priečinok — musí tam byť `app/`, `build.gradle`, `gradlew`, `.github/`…) do okna
- Commit

> Dôležité: `.github` a `gradle/wrapper/gradle-wrapper.jar` musia byť tiež nahraté.

### 3. Počkaj na build
- Záložka **Actions** → uvidíš bežiaci workflow "Build APK"
- Trvá ~2-4 min
- Po dokončení klikni na build → dole **Artifacts** → **meniny-app** → stiahne sa ZIP s `app-debug.apk`

### 4. Nainštaluj na telefón
- Prenes `app-debug.apk` do telefónu
- Otvor ho, povoľ inštaláciu z neznámych zdrojov
- Nainštaluj

### 5. Pridaj widget na plochu
- Dlho podrž prst na domovskej obrazovke → **Widgety**
- Nájdi **Meniny** → potiahni na plochu
- Hotovo ✅

---

## 🔧 Úpravy
- **Farby:** `app/src/main/res/drawable/widget_bg.xml` a `widget_meniny.xml` (modrá `#7AA2FF`)
- **Kalendár:** `app/src/main/java/sk/kubik/meniny/Meniny.kt`
