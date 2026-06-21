# Axyz Gems - project notes

LittleFoot script that turns a ROLI Lightpad Block into a 5-finger high-res
multi-XYZ MIDI controller, plus integration "drivers" for Bitwig and Ableton.

## What ships (the source of truth)

- `Axyz Gems.littlefoot` - the device script uploaded via ROLI Dashboard. The
  metadata block at the top (description/details/variables/tooltips) is
  user-facing inside ROLI Dashboard, so language errors there are visible to users.
- `Bitwig/AxyzGems.control.js` - Bitwig JavaScript controller (the active driver).
- `Ableton/Axyz_Gems/` - Ableton MIDI Remote Script (Control Surface). Low-res
  (7-bit) only.
- `Bitwig/Extension/` - a Java/Maven Bitwig Extension (alternative driver, mostly
  left commented-out in the README). `target/` and the Eclipse `workspace/.metadata`
  are build/IDE cruft and are NOT tracked.

`.pyc` and `__pycache__/` are gitignored. Do not commit compiled Python.

## Releasing a new version (the full checklist)

The version string lives in THREE files (four spots) and must stay in sync:

1. `README.md` - the `# Axyz Gems vX.YZ` title.
2. `Axyz Gems.littlefoot` - line 2, in BOTH `description="vX.YZ ..."` and
   `details="vX.YZ ..."`.
3. `Bitwig/AxyzGems.control.js` - the `host.defineController(..., "X.YZ", ...)` arg
   (no `v` prefix here).

Versioning convention: linear single-minor bumps (`v1.29 -> v1.30 -> v1.31`).
Each release is:

- one commit whose message is just the version, e.g. `v1.31`;
- a matching **lightweight** git tag, e.g. `git tag v1.31` (prior tags are
  lightweight, not annotated - match that);
- pushed commit AND tag (`git push` does not push tags by default;
  use `git push origin v1.31` or `git push --tags`);
- a **GitHub Release** created from the tag.

IMPORTANT: a pushed tag does NOT become a GitHub Release on its own. The
"Releases" box / "Latest" badge / the README's "Source code (zip)" link all
depend on a published Release object. Create it explicitly:

```
gh release create vX.YZ --title "Axyz Gems vX.YZ" --latest --notes "..."
```

(GitHub auto-attaches "Source code (zip)"; no asset upload needed.)

Joris pushes and tags himself. Creating the GitHub Release is an outward-facing
publish - confirm before doing it.

## Bitwig controller API facts (learned, verified against installed Bitwig 6.1)

- `control.js` uses `loadAPI(7)`. Do NOT bump it to chase the latest API.
  `loadAPI(N)` declares the MINIMUM Bitwig API a script needs; lower = runs on
  more (older) Bitwig versions. Bitwig keeps full backward compatibility, so the
  2017-era `loadAPI(7)` still loads fine in 6.1. Bitwig's own bundled scripts
  prove the point: of 44 official `.control.js`, ~28 are `loadAPI(1)`, 7 are
  `loadAPI(2)`, and only 1 is on the latest. ROLI's own `Seaboard GRAND.control.js`
  is `loadAPI(1)`. Bumping would only break the script on older Bitwig for no gain.
- `host.setShouldFailOnDeprecatedUse(...)` must be `false` (was `true`, fixed in
  v1.31). No official bundled script sets it true; `true` turns a harmless
  deprecation warning into a hard load failure on future Bitwig updates.
- The script registers NO note input (`createNoteInput` is intentionally not
  called). So Axyz Gems does NOT appear in a track's MIDI-From / Note Inputs list,
  and that is correct - the gems send Control Changes, not notes. It runs in the
  background and drives the selected device's 8 remote-control (macro) knobs.
- `isChannelController(status)` is a Bitwig JS-only global MIDI helper (not defined
  in the file, not a bug). Not available to Java extensions.

## Local dev setup (machine-specific, NOT in the repo)

On Joris's machine, `~/Documents/Bitwig Studio/Controller Scripts/AxyzGems.control.js`
is a **symlink** to the repo's `Bitwig/AxyzGems.control.js`. So edits in the repo
are instantly live in Bitwig (reload the controller, or restart, to pick them up).
The symlink is local only; other users copy the file per the README. After a code
change, reload the controller and check the console (speech-bubble icon) for
`Axyz Gems initialized!` to confirm a clean load.

## House style for docs / user-facing text

Plain, human, no AI tells: no em/en dashes (use `-`), straight quotes only, no
corporate vocabulary. The script's own `counter parted` (LSB/MSB pairing) is a
deliberate coinage, not an error - leave it.
