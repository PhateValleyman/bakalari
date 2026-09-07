# BakalariGuard - definice projektu

## Cíl

Aplikace pro rodičovskou kontrolu tabletu propojená se systémem Bakaláři.

Aplikace bude mít dva režimy:

- **Child mode** - dětské zařízení (např. NVIDIA Shield Tablet)
- **Parent mode** - rodičovský správce (např. Redmi)

Jedna APK, rozdílné role.

## Hlavní scénář

1. Dětské zařízení kontroluje domácí úkoly v Bakalářích.
2. Pokud nejsou úkoly, tablet je dostupný.
3. Pokud existují úkoly, zařízení přejde do omezeného režimu.
4. Rodič dostane požadavek na potvrzení splnění.
5. Po schválení se zařízení odemkne.

## Výchozí škola

URL je uložena v README projektu.

## Architektura

```
BakalariGuard APK
├── Parent mode
│   ├── správa zařízení
│   ├── schvalování úkolů
│   └── notifikace
│
├── Child mode
│   ├── lock screen
│   ├── launcher režim
│   └── synchronizace stavu
│
└── Shared core
    ├── Bakaláři API klient
    ├── kryptografie
    ├── databáze
    └── synchronizace
```

## Technologie

- Kotlin / Android
- Room database
- WorkManager
- Firebase Cloud Messaging (výchozí synchronizace)
- možnost pozdějšího Go serveru

## Bezpečnost

- heslo Bakalářů pouze v rodičovském režimu
- zařízení komunikují pomocí podepsaných příkazů
- lokální cache posledního známého stavu

## Plán verzí

### v0.1

- základ Android projektu
- přepnutí Parent / Child režimu
- lokální databáze
- základ zamykání

### v0.2

- Bakaláři API klient
- načítání domácích úkolů

### v0.3

- vzdálené schvalování
- push notifikace

### v0.4

- Device Owner integrace
- vlastní launcher
- časové limity aplikací
