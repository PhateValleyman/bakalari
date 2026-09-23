#!/usr/bin/env bash
# Regression tests for the shared library; network access is intentionally not used.
set -euo pipefail

# English explanatory comment: Create an isolated HOME so tests never touch the user's configuration.
TEST_ROOT="$(mktemp -d)"
trap 'rm -rf "$TEST_ROOT"' EXIT
export HOME="$TEST_ROOT/home"
export BAKALARI_CONFIG="$TEST_ROOT/config.toml"
export BAKALARI_CACHE_DIR="$TEST_ROOT/cache"
mkdir -p "$HOME"

# English explanatory comment: Load the library after test-specific paths are configured.
# shellcheck source=../lib/common.sh
source "$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)/lib/common.sh"

# English explanatory comment: Write representative TOML including quoted values and comments.
cat >"$BAKALARI_CONFIG" <<'EOF'
[general]
user01 = "alice"
user02 = "bob"

[alice]
host = "school.example  # not part of the host"
user = "student"
pass = "secret"
max_hours = 8 # timetable limit

[colors]
"Čj" = 34
EOF
chmod 600 "$BAKALARI_CONFIG"

# English explanatory comment: Assert that quoted values and inline comments are parsed correctly.
[[ "$(config_value alice host)" == "school.example  # not part of the host" ]]
[[ "$(config_value alice max_hours)" == "8" ]]

# English explanatory comment: Assert deterministic profile selection and required-field loading.
resolve_user ""
[[ "$BAKALARI_USER" == "alice" ]]
[[ "$BAKALARI_HOST" == "school.example  # not part of the host" ]]
[[ "$BAKALARI_MAX_HOURS" == "8" ]]

# English explanatory comment: Assert invalid profile names are rejected before file access.
if resolve_user '../escape'; then
    printf 'invalid profile was accepted\n' >&2
    exit 1
fi

# English explanatory comment: Assert URL normalization removes duplicate trailing slashes.
[[ "$(api_base_url https://school.example/)" == "https://school.example" ]]
[[ "$(api_base_url school.example/)" == "https://school.example" ]]

# English explanatory comment: Assert cache writes are valid, private, and atomically readable.
cache_save result.json '{"ok":true}'
[[ "$(cache_load result.json)" == '{"ok":true}' ]]
[[ "$(stat -c '%a' "$(cache_file result.json)")" == "600" ]]

# English explanatory comment: Assert token persistence updates the profile without exposing credentials.
save_token alice 'abc.def_123'
[[ "$(config_value alice token)" == 'abc.def_123' ]]
[[ "$(stat -c '%a' "$BAKALARI_CONFIG")" == "600" ]]

printf 'All common-library tests passed.\n'
