# bakalari-cli

`bakalari-cli` je konzolový klient pro [Bakaláře](https://www.bakalari.cz/) napsaný v Bashi. Zobrazuje rozvrh, známky, absenci, domácí úkoly a informace o profilu. Funguje i bez připojení, pokud byla data dříve uložena do lokální cache.

## Požadavky

- Bash 4+
- `curl`, `jq`, `awk`, `sed`, `sort`, `find`
- volitelně `gum` pro interaktivní editor konfigurace
- volitelně Termux:API (`termux-notification`) pro notifikace v Androidu

## Instalace

```sh
# Clone the repository and install the complete self-contained application tree.
git clone https://github.com/PhateValleyman/bakalari.git
cd bakalari
sudo install -d -m 755 /usr/local/lib/bakalari-cli
sudo cp -a bakalari-cli lib modules /usr/local/lib/bakalari-cli/
sudo chmod 755 /usr/local/lib/bakalari-cli/bakalari-cli /usr/local/lib/bakalari-cli/modules/*.sh
sudo ln -sf /usr/local/lib/bakalari-cli/bakalari-cli /usr/local/bin/bakalari-cli
```

> Instalace pomocí `install` zachovává spustitelná oprávnění a neinstaluje žádné přihlašovací údaje.

Pokud používáte instalaci mimo `/usr/local`, spusťte klienta přímo z klonu repozitáře. Dispatcher hledá `lib/` a `modules/` relativně ke své vlastní cestě.

## Konfigurace

```sh
# Create the per-user configuration directory with private permissions.
mkdir -p ~/.config/bakalari-cli
install -m 600 config.toml.example ~/.config/bakalari-cli/config.toml

# Open the interactive profile editor.
bakalari-cli config --new
```

Alternativně lze profil vytvořit přihlášením:

```sh
# Create or update a profile and verify credentials against the school API.
bakalari-cli login --user dzonny
```

Editor `bakalari-cli config --global` používá dvousloupcové rozhraní. Levý sloupec zůstává viditelný se seznamem položek a šipkami `↑/↓` se vybírá aktivní řádek. Po stisku `Enter` na položce barvy se v pravém sloupci rozvine vizuální paleta ANSI 256 barev; šipkami nebo `H/J/K/L` se vybere odstín a dalším `Enter` se potvrdí. `Esc` paletu zavře bez změny a `Q` uloží nastavení a skončí. Při použití bez interaktivního terminálu editor odmítne hodnoty mimo rozsah `0–255`.

Priorita konfiguračního souboru je:

1. `BAKALARI_CONFIG`, pokud je nastaveno;
2. `~/.bakalariclirc`, pokud existuje;
3. `~/.config/bakalari-cli/config.toml`.

Ukázka profilu:

```toml
[general]
user01 = "dzonny"

[dzonny]
host = "moje-skola.bakalari.cz"
user = "uzivatel"
pass = "heslo"
max_hours = 6
token = ""
name = ""
class = ""
```

Soubor obsahuje citlivé údaje a musí mít oprávnění `0600`. Token se po úspěšném přihlášení obnovuje automaticky. Do repozitáře nikdy neukládejte skutečný `config.toml`.

## Použití

```sh
# Show the command overview and configured profile.
bakalari-cli

# Display the current timetable.
bakalari-cli rozvrh

# Display marks, attendance, homework, or profile information.
bakalari-cli znamky
bakalari-cli absence
bakalari-cli ukoly
bakalari-cli info

# Select a specific configured profile.
bakalari-cli info --user dzonny

# Inspect or clear cached API responses.
bakalari-cli cache --path
bakalari-cli cache --list
bakalari-cli cache --clear
```

Každý modul podporuje `--help`. Při nedostupném API se používá pouze platná cache; pokud platná data nejsou k dispozici, klient skončí nenulovým návratovým kódem.

## Proměnné prostředí

| Proměnná | Význam |
|---|---|
| `BAKALARI_CONFIG` | Explicitní cesta ke konfiguraci |
| `BAKALARI_CACHE_DIR` | Explicitní cesta k cache; má přednost před nastavením v TOML |
| `BAKALARI_BASE_URL` | Přepsání základní URL API, vhodné pro testovací proxy |
| `XDG_CACHE_HOME` | Základ pro výchozí cache, pokud není nastavena `BAKALARI_CACHE_DIR` |

## Vývoj a testování

```sh
# Run Bash syntax checks, regression tests, and ShellCheck locally.
bash -n bakalari-cli lib/common.sh modules/*.sh tests/*.sh
tests/run.sh
shellcheck --severity=error bakalari-cli lib/common.sh modules/*.sh tests/*.sh
```

Testy nepoužívají síť ani skutečná přihlašovací data. Pokrývají načítání konfigurace, validaci profilu, normalizaci URL, privátní cache a ukládání tokenu.

## Návratové kódy

| Kód | Význam |
|---:|---|
| `0` | Úspěch |
| `2` | Chybná volba nebo konfigurace |
| `3` | Síť/API nebo nedostupná cache |
| `4` | Neplatná odpověď API nebo poškozená data |

## Licence

Licence v původním repozitáři nebyla při vytvoření této dokumentace uvedena. Před redistribucí doplňte vhodný licenční soubor.
