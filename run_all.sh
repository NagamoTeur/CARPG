#!/bin/bash
# Régénère toutes les données du site à partir du dossier du modpack
cd "$(dirname "$0")"
python3 index_mods.py > out/idx.txt && python3 extract.py && python3 merge.py && \
python3 build_skilltree.py && python3 build_gear.py && python3 build_origins.py && python3 build_spells.py && \
python3 build_quests.py && python3 build_items.py && python3 build_attrs.py && python3 build_presets.py && python3 build_bosses.py && python3 pack_data.py
# (optionnel, long) builds optimisés : TREE=auto node sim/gen_builds.js
python3 build_wiki.py && python3 check_links.py
