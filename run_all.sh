#!/bin/bash
# Régénère toutes les données du site à partir du dossier du modpack
cd "$(dirname "$0")"
python3 index_mods.py > out/idx.txt && python3 extract.py && \
for z in ../minecraft/config/paxi/datapacks/*.zip; do n=$(basename "$z" .zip | tr " '" "__"); mkdir -p "extract_paxi/$n"; unzip -qo "$z" 'data/*' -d "extract_paxi/$n" 2>/dev/null; done && \
python3 merge.py && \
python3 build_skilltree.py && python3 build_gear.py && python3 build_origins.py && python3 build_spells.py && \
python3 build_quests.py && python3 build_origin_rules.py && python3 build_caps.py && python3 build_relics.py && python3 build_recipes.py && python3 build_structures.py && python3 build_items.py && python3 build_attrs.py && python3 build_presets.py && python3 build_bosses.py && python3 pack_data.py
# (optionnel, long) builds optimisés : TREE=auto node sim/gen_builds.js && node sim/realistic.js && python3 split_builds.py
/home/nagamo/.local/graphify-venv/bin/python3 build_icons.py  # (nécessite Pillow ; sinon copie brute)
python3 build_wiki.py && python3 check_links.py && python3 tests/check_claims.py
