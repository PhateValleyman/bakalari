#!/usr/bin/env bash
# Run all repository regression tests.
set -euo pipefail
SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
"$SCRIPT_DIR/test_common.sh"
