#!/usr/bin/env bash
# Regression tests for the color picker without requiring a real terminal.
set -euo pipefail

# English explanatory comment: Load shared output helpers before extracting the editor functions.
TEST_ROOT="$(mktemp -d)"
trap 'rm -rf "$TEST_ROOT"' EXIT
export HOME="$TEST_ROOT/home"
export BAKALARI_CONFIG="$TEST_ROOT/config.toml"
mkdir -p "$HOME"
# shellcheck source=../lib/common.sh
source "$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)/lib/common.sh"

# English explanatory comment: Extract only pure/input functions so the interactive editor is not started.
# shellcheck disable=SC1090
source <(sed -n '48,190p' "$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.." && pwd)/modules/config.sh")

# English explanatory comment: Reject invalid palette values in the preview helper.
[[ "$(color_preview 256)" == "-       " ]]
[[ "$(color_preview abc)" == "-       " ]]

# English explanatory comment: Accept valid non-TTY input after rejecting an out-of-range value.
value="$(printf '999\n42\n' | select_color_value 34 2>/dev/null)"
[[ "$value" == "42" ]]

# English explanatory comment: Return promptly when non-TTY input reaches EOF.
if printf '' | timeout 2 bash -c 'source <(sed -n "48,190p" modules/config.sh); select_color_value 34' >/dev/null 2>&1; then
    printf 'EOF should not be accepted as a color\n' >&2
    exit 1
fi

printf 'All color-picker tests passed.\n'
