#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT"

fail() {
  printf 'ERROR: %s\n' "$*" >&2
  exit 1
}

printf '== Verificación estática de Protocol v2168 ==\n'

grep -q '^version=1\.26\.40-R1-SNAPSHOT$' gradle.properties \
  || fail 'gradle.properties no contiene 1.26.40-R1-SNAPSHOT'

grep -q '\.protocolVersion(2168)' \
  bedrock-codec/src/main/java/org/cloudburstmc/protocol/bedrock/codec/v2168/Bedrock_v2168.java \
  || fail 'Bedrock_v2168 no declara protocolVersion 2168'

grep -q '\.minecraftVersion("1\.26\.40")' \
  bedrock-codec/src/main/java/org/cloudburstmc/protocol/bedrock/codec/v2168/Bedrock_v2168.java \
  || fail 'Bedrock_v2168 no declara Minecraft 1.26.40'

for dir in v630_netease v686_netease v766_netease v819_netease v860_netease; do
  test -d "bedrock-codec/src/main/java/org/cloudburstmc/protocol/bedrock/codec/$dir" \
    || fail "falta el códec NetEase $dir"
done

if grep -R --line-number 'org\.cloudburstmc\.protocol\.common' \
    bedrock-codec/src/main/java/org/cloudburstmc/protocol/bedrock/codec/v2168; then
  fail 'v2168 contiene imports de la estructura upstream no adaptados al fork'
fi

python3 - <<'PY'
from pathlib import Path
import re

root = Path('bedrock-codec/src/main/java')
vroot = root / 'org/cloudburstmc/protocol/bedrock/codec/v2168'
missing = []

for source in vroot.rglob('*.java'):
    text = source.read_text(encoding='utf-8')
    for imported in re.findall(r'^import\s+(org\.cloudburstmc\.protocol\.bedrock\.[\w.]+);', text, re.M):
        if imported.endswith('.*'):
            continue
        parts = imported.split('.')
        found = False
        # A Java import can target either a top-level class or a nested class.
        for cut in range(len(parts), 0, -1):
            candidate = root / Path(*parts[:cut]).with_suffix('.java')
            if candidate.exists():
                found = True
                break
        if not found:
            missing.append(f'{source}: {imported}')

if missing:
    raise SystemExit('Imports internos ausentes:\n' + '\n'.join(missing))

# Lightweight lexical delimiter check, ignoring comments and strings.
def strip_java(s: str) -> str:
    s = re.sub(r'/\*.*?\*/', '', s, flags=re.S)
    s = re.sub(r'//.*', '', s)
    s = re.sub(r'"(?:\\.|[^"\\])*"', '""', s)
    s = re.sub(r"'(?:\\.|[^'\\])*'", "''", s)
    return s

errors = []
for source in root.rglob('*.java'):
    text = strip_java(source.read_text(encoding='utf-8'))
    for left, right in [('(', ')'), ('{', '}'), ('[', ']')]:
        if text.count(left) != text.count(right):
            errors.append(f'{source}: {left}{right} {text.count(left)} != {text.count(right)}')
if errors:
    raise SystemExit('Delimitadores desbalanceados:\n' + '\n'.join(errors))

print('Imports internos y delimitadores: OK')
PY

if [[ "${SKIP_GRADLE:-0}" == "1" ]]; then
  printf 'SKIP_GRADLE=1: se omite la compilación.\n'
  exit 0
fi

command -v java >/dev/null 2>&1 || fail 'Java no está instalado'
JAVA_MAJOR="$(java -version 2>&1 | sed -n '1s/.*version "\([0-9]*\).*/\1/p')"
[[ "$JAVA_MAJOR" == "21" ]] || fail "se requiere Java 21; detectado: ${JAVA_MAJOR:-desconocido}"

test -x ./gradlew || fail 'gradlew no es ejecutable'

printf '== Compilación, pruebas y publicación local ==\n'
./gradlew clean test publishToMavenLocal

printf '\nOK: Protocol 1.26.40-R1-SNAPSHOT compiló, pasó pruebas y fue publicado en Maven Local.\n'
