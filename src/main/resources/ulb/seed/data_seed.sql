-- attacks
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('fouet_liane', 'Vine Whip', 'Plant', 'Inflicts damage and slightly reduces the opponent''s defense.', 40, 0.97, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('pollen_sournois', 'Sneaky Pollen', 'Plant', 'Inflicts damage and reduces the opponent''s initiative on the next turn.', 35, 0.78, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('racines_vives', 'Quick Roots', 'Plant', 'Inflicts damage and slightly increases the user''s defense.', 35, 0.91, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('spore_collante', 'Sticky Spore', 'Plant', 'Inflicts damage and decreases the opponent''s initiative.', 30, 0.74, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('feuille_tranchante', 'Razor Leaf', 'Plant', 'Inflicts high damage.', 55, 0.86, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('vieux_ronce', 'Old Bramble', 'Plant', 'Inflicts medium damage and slightly reduces the opponent''s attack.', 40, 0.88, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('croissance_brutale', 'Brutal Growth', 'Plant', 'Inflicts damage and increases the user''s attack.', 40, 0.83, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('charge_sylvestre', 'Sylvan Charge', 'Plant', 'Inflicts heavy damage but reduces the user''s defense.', 60, 0.72, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('mur_de_lianes', 'Vine Wall', 'Plant', 'Inflicts little damage and significantly increases the user''s defense.', 15, 1.0, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('explosion_ardente', 'Fiery Explosion', 'Fire', 'Inflicts very high damage.', 70, 0.63, -1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('flammes_folles', 'Wild Flames', 'Fire', 'Inflicts damage and reduces the opponent''s defense.', 45, 0.76, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('choc_brulant', 'Burning Shock', 'Fire', 'Inflicts damage and slightly increases the user''s attack.', 40, 0.93, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('surchauffe', 'Overheat', 'Fire', 'Inflicts heavy damage but reduces the user''s attack.', 65, 0.69, -1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('brasier_sauvage', 'Wild Blaze', 'Fire', 'Inflicts damage and reduces the user''s initiative.', 50, 0.81, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('vague_rapide', 'Quick Wave', 'Ice', 'Inflicts damage and increases the user''s initiative on the next turn.', 40, 0.96, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('courant_croise', 'Cross Current', 'Ice', 'Inflicts damage and reduces the opponent''s initiative.', 40, 0.84, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('pluie_calme', 'Calm Rain', 'Ice', 'Inflicts little damage and slightly restores the user''s HP.', 20, 0.99, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('maree_stable', 'Steady Tide', 'Ice', 'Inflicts damage and increases the user''s defense.', 35, 0.94, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('eau_pure', 'Pure Water', 'Ice', 'Inflicts little damage and slightly restores the user''s team''s HP.', 15, 1.0, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('jet_de_pierres', 'Rock Throw', 'Mecha', 'Inflicts medium damage.', 45, 0.89, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('armure_minerale', 'Mineral Armor', 'Mecha', 'Inflicts little damage and significantly increases the user''s defense.', 20, 0.98, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('fracas_rocheux', 'Rock Smash', 'Mecha', 'Inflicts heavy damage but reduces the user''s defense.', 65, 0.71, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('avalanche', 'Avalanche', 'Mecha', 'Inflicts very high damage but reduces the user''s initiative.', 70, 0.58, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('pluie_de_graviers', 'Gravel Rain', 'Mecha', 'Inflicts damage and slightly reduces the opponent''s initiative.', 35, 0.87, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('langue_de_feu', 'Fire Tongue', 'Fire', 'Deals damage and slightly reduces the opponent''s attack.', 40, 0.88, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('cendres_volantes', 'Flying Ashes', 'Fire', 'Inflicts little damage and reduces the opponent''s initiative on the next turn.', 25, 0.95, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('embrasement', 'Blaze', 'Fire', 'Inflicts damage and significantly increases the user''s attack.', 35, 0.85, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('tornade_ardente', 'Blazing Storm', 'Fire', 'Inflicts heavy damage but reduces the user''s initiative.', 60, 0.75, -1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('crache_flammes', 'Fire Spit', 'Fire', 'Inflicts damage and reduces the opponent''s defense.', 45, 0.45, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('frappe_incandescente', 'Incandescent Strike', 'Fire', 'Inflicts high damage but reduces the user''s defense.', 55, 0.79, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('meteore_enflamme', 'Flaming Meteor', 'Fire', 'Inflicts extremely high damage.', 80, 0.55, -2, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('impact_metallique', 'Metal Impact', 'Mecha', 'Inflicts medium damage.', 50, 0.9, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('renfort_acier', 'Steel Reinforcement', 'Mecha', 'Inflicts little damage and increases the user''s defense.', 25, 0.95, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('frappe_piston', 'Piston Strike', 'Mecha', 'Inflicts heavy damage.', 60, 0.8, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('charge_lourde', 'Heavy Charge', 'Mecha', 'Inflicts damage and reduces the user''s initiative.', 55, 0.85, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('projection_boulon', 'Bolt Throw', 'Mecha', 'Inflicts light damage and reduces the opponent''s defense.', 30, 0.9, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('tir_precision', 'Precision Shot', 'Mecha', 'Inflicts medium damage with high accuracy.', 30, 0.98, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('cocon_protecteur', 'Protective Cocoon', 'Plant', 'Inflicts very little damage and greatly increases defense.', 18, 1.0, 2, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('tempete_feuilles', 'Leaf Storm', 'Plant', 'Inflicts high damage.', 60, 0.81, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('floraison_force', 'Power Bloom', 'Plant', 'Inflicts little damage and increases the user''s attack.', 28, 0.98, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('spores_sommeil', 'Sleep Spores', 'Plant', 'Inflicts damage and reduces the opponent''s initiative.', 33, 0.82, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('lancer_semence', 'Seed Barrage', 'Plant', 'Inflicts medium damage.', 47, 0.86, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('tranchant_bambou', 'Bamboo Slash', 'Plant', 'Inflicts heavy damage.', 64, 0.78, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('eclat_lumineux', 'Luminous Burst', 'Light', 'Inflicts medium damage and slightly increases the user''s initiative.', 42, 0.91, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('rayon_percant', 'Piercing Ray', 'Light', 'Inflicts heavy damage.', 58, 0.82, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('halo_protecteur', 'Protective Halo', 'Light', 'Inflicts little damage and significantly increases the user''s defense.', 18, 1.0, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('flash_aveuglant', 'Blinding Flash', 'Light', 'Inflicts damage and reduces the opponent''s initiative.', 36, 0.87, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('lame_radieuse', 'Radiant Blade', 'Light', 'Inflicts high damage but reduces the user''s defense.', 57, 0.8, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('aura_stimulante', 'Stimulating Aura', 'Light', 'Inflicts little damage and increases the user''s attack.', 25, 0.97, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('orbe_etincelant', 'Spark Orb', 'Light', 'Inflicts medium damage and slightly reduces the opponent''s defense.', 41, 0.89, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('cascade_claire', 'Clear Cascade', 'Light', 'Inflicts damage and slightly restores the user''s HP.', 28, 0.96, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('rafale_photonique', 'Photon Rush', 'Light', 'Inflicts very high damage but reduces the user''s initiative.', 72, 0.61, -1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('reflet_tranchant', 'Sharp Reflection', 'Light', 'Inflicts medium damage with very high accuracy.', 38, 0.99, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('malefice_noir', 'Black Curse', 'Shadow', 'Inflicts little damage and reduces the opponent''s attack.', 30, 0.95, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('drain_dame', 'Soul Drain', 'Shadow', 'Inflicts damage and reduces the opponent''s initiative.', 30, 0.85, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('rage_demoniaque', 'Demonic Rage', 'Shadow', 'Inflicts high damage but lowers the user''s defense.', 62, 0.72, -1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('poigne_infernale', 'Infernal Grip', 'Shadow', 'Inflicts very little damage and greatly increases attack.', 20, 0.98, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('ombre_foudroyante', 'Shadow Bolt', 'Shadow', 'Inflicts medium damage.', 33, 0.80, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('coup_cacher', 'Hidden Blade', 'Shadow', 'Inflicts heavy damage.', 62, 0.82, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('pic_glacial', 'Glacial Spike', 'Ice', 'Inflicts heavy damage.', 59, 0.81, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('brume_froide', 'Cold Mist', 'Ice', 'Inflicts damage and reduces the opponent''s initiative.', 34, 0.92, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('carapace_givree', 'Frozen Shell', 'Ice', 'Inflicts little damage and significantly increases the user''s defense.', 18, 1.0, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('torrent_polaire', 'Polar Torrent', 'Ice', 'Inflicts medium damage and slightly restores the user''s HP.', 26, 0.97, 1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('eclats_de_givre', 'Frost Shards', 'Ice', 'Inflicts damage and slightly reduces the opponent''s defense.', 39, 0.9, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('maree_gelee', 'Frozen Tide', 'Ice', 'Inflicts damage and increases the user''s defense.', 36, 0.93, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('rafale_cristalline', 'Crystal Gust', 'Ice', 'Inflicts high damage but reduces the user''s initiative.', 56, 0.79, -1, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('blizzard_dense', 'Dense Blizzard', 'Ice', 'Inflicts very high damage but has low accuracy.', 71, 0.62, -1, TRUE) ON CONFLICT DO NOTHING;

-- level_rewards attacks
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('rugissement_montagne', 'Rugissement de la Montagne', 'Mecha', 'Provoque une onde de choc magmatique, inflige des dégâts importants et réduit la défense de l''adversaire un tour.', 55, 0.80, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('colonne_de_cristal', 'Colonne de Cristal', 'Mecha', 'Pérce l''adversaire avec une colonne de cristal, inflige de gros dégâts et peut l''étourdir.', 70, 0.75, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('cascade_de_galets', 'Cascade de Galets', 'Mecha', 'Projette une pluie de petits galets qui désoriente l''adversaire et fait trébucher sa stratégie.', 45, 0.90, 0, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO attaque (id, nom, type, description, puissance, precision, priorite, est_magique) VALUES
  ('tempete_de_cailloux', 'Tempête de Cailloux', 'Mecha', 'Envoie une tempête de cailloux malpolis qui perturbe l''adversaire et lui casse un peu le moral.', 55, 0.82, 0, FALSE) ON CONFLICT DO NOTHING;

-- Effects for attaque: fouet_liane (id=1)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (1, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -1, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: pollen_sournois (id=2)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (2, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -4, '1') ON CONFLICT DO NOTHING;

-- Effects for attaque: racines_vives (id=3)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (3, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 1, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: spore_collante (id=4)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (4, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: vieux_ronce (id=5)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (5, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'attack', NULL, -1, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: croissance_brutale (id=6)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (6, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: charge_sylvestre (id=7)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (7, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: mur_de_lianes (id=8)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (8, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: flammes_folles (id=9)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (9, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: choc_brulant (id=10)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (10, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: surchauffe (id=11)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (11, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, -4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: brasier_sauvage (id=12)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (12, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: vague_rapide (id=13)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (13, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, 4, '1') ON CONFLICT DO NOTHING;

-- Effects for attaque: courant_croise (id=14)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (14, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: pluie_calme (id=15)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (15, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 15, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for attaque: maree_stable (id=16)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (16, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: eau_pure (id=17)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (17, 'heal', NULL, NULL, NULL, NULL, 'team', NULL, 10, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for attaque: armure_minerale (id=18)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (18, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: fracas_rocheux (id=19)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (19, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: avalanche (id=20)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (20, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, -4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: pluie_de_graviers (id=21)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (21, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: langue_de_feu (id=22)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (22, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'attack', NULL, -1, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: cendres_volantes (id=23)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (23, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '1') ON CONFLICT DO NOTHING;

-- Effects for attaque: embrasement (id=24)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (24, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: tornade_ardente (id=25)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (25, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: crache_flammes (id=26)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (26, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: frappe_incandescente (id=27)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (27, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: renfort_acier (id=28)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (28, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: charge_lourde (id=29)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (29, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: projection_boulon (id=30)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (30, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: cocon_protecteur (id=31)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (31, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: floraison_force (id=32)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (32, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: spores_sommeil (id=33)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (33, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: eclat_lumineux (id=34)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (34, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, 2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: halo_protecteur (id=35)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (35, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: flash_aveuglant (id=36)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (36, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: lame_radieuse (id=37)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (37, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: aura_stimulante (id=38)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (38, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: orbe_etincelant (id=39)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (39, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -1, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: cascade_claire (id=40)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (40, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 15, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for attaque: rafale_photonique (id=41)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (41, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, -4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: malefice_noir (id=42)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (42, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'attack', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: drain_dame (id=43)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (43, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: rage_demoniaque (id=44)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (44, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: poigne_infernale (id=45)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (45, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: brume_froide (id=46)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (46, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: carapace_givree (id=47)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (47, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 4, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: torrent_polaire (id=48)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (48, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 16, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for attaque: eclats_de_givre (id=49)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (49, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -1, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: maree_gelee (id=50)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (50, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 2, '-1') ON CONFLICT DO NOTHING;

-- Effects for attaque: rafale_cristalline (id=51)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (51, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for level_reward attaque: rugissement_montagne (id=52)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (52, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for level_reward attaque: colonne_de_cristal (id=53)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (53, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -4, '-1') ON CONFLICT DO NOTHING;

-- Effects for level_reward attaque: pluie_de_graviers (level 30) (id=54)
-- Note: pluie_de_graviers already exists in attaque table; effect re-linked
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (54, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for level_reward attaque: cascade_de_galets (id=55)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (55, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'initiative', NULL, -3, '-1') ON CONFLICT DO NOTHING;

-- Effects for level_reward attaque: tempete_de_cailloux (id=56)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (56, 'stat_modifier', NULL, NULL, NULL, NULL, 'opponent', 'defense', NULL, -2, '-1') ON CONFLICT DO NOTHING;

-- Effects for objet: invigorating_berry (id=57)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (57, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 20, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for objet: tonic_berry (id=58)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (58, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 50, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for objet: elixir (id=59)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (59, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'defense', NULL, 10, '-1') ON CONFLICT DO NOTHING;

-- Effects for objet: offensive_serum (id=60)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (60, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'attack', NULL, 10, '-1') ON CONFLICT DO NOTHING;

-- Effects for objet: restorative_balm (id=61)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (61, 'reset_malus', NULL, NULL, NULL, NULL, 'caster', NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for objet: vivifying_berry (id=62)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (62, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 30, NULL, NULL) ON CONFLICT DO NOTHING;

-- Effects for objet: accuracy_powder (id=63)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (63, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'precision', NULL, 15, '-1') ON CONFLICT DO NOTHING;

-- Effects for objet: speed_tonic (id=64)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (64, 'stat_modifier', NULL, NULL, NULL, NULL, 'caster', 'initiative', NULL, 10, '-1') ON CONFLICT DO NOTHING;

-- Effects for objet: mystic_berry (id=65)
INSERT INTO effet (id, type, valeur_pourcent, quantite, categorie, type_cible, cible, stat, valeur, modificateur, duree) VALUES
  (65, 'heal', NULL, NULL, NULL, NULL, 'caster', NULL, 40, NULL, NULL) ON CONFLICT DO NOTHING;

SELECT setval('effet_id_seq', 65);

-- =============================================================================
-- TABLE: effet_attaque
-- =============================================================================
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (1, 'fouet_liane') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (2, 'pollen_sournois') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (3, 'racines_vives') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (4, 'spore_collante') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (5, 'vieux_ronce') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (6, 'croissance_brutale') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (7, 'charge_sylvestre') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (8, 'mur_de_lianes') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (9, 'flammes_folles') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (10, 'choc_brulant') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (11, 'surchauffe') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (12, 'brasier_sauvage') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (13, 'vague_rapide') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (14, 'courant_croise') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (15, 'pluie_calme') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (16, 'maree_stable') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (17, 'eau_pure') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (18, 'armure_minerale') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (19, 'fracas_rocheux') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (20, 'avalanche') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (21, 'pluie_de_graviers') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (22, 'langue_de_feu') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (23, 'cendres_volantes') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (24, 'embrasement') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (25, 'tornade_ardente') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (26, 'crache_flammes') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (27, 'frappe_incandescente') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (28, 'renfort_acier') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (29, 'charge_lourde') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (30, 'projection_boulon') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (31, 'cocon_protecteur') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (32, 'floraison_force') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (33, 'spores_sommeil') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (34, 'eclat_lumineux') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (35, 'halo_protecteur') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (36, 'flash_aveuglant') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (37, 'lame_radieuse') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (38, 'aura_stimulante') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (39, 'orbe_etincelant') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (40, 'cascade_claire') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (41, 'rafale_photonique') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (42, 'malefice_noir') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (43, 'drain_dame') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (44, 'rage_demoniaque') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (45, 'poigne_infernale') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (46, 'brume_froide') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (47, 'carapace_givree') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (48, 'torrent_polaire') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (49, 'eclats_de_givre') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (50, 'maree_gelee') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (51, 'rafale_cristalline') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (52, 'rugissement_montagne') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (53, 'colonne_de_cristal') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (54, 'pluie_de_graviers') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (55, 'cascade_de_galets') ON CONFLICT DO NOTHING;
INSERT INTO effet_attaque (effet_id, attaque_id) VALUES (56, 'tempete_de_cailloux') ON CONFLICT DO NOTHING;

-- =============================================================================
-- TABLE: object
-- =============================================================================
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('invigorating_berry', 'Invigorating Berry', 'Restores 20 HP to the active Bugemon.', 'heal', 'baie_revigorante.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('tonic_berry', 'Tonic Berry', 'Restores 50 HP to the active Bugemon.', 'heal', 'baie_tonique.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('elixir', 'Elixir', 'Increases the active Bugemon''s defense by 10 until the end of battle.', 'boost', 'elixir.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('offensive_serum', 'Offensive Serum', 'Increases the active Bugemon''s attack by 10 until the end of battle.', 'boost', 'serum_offensif.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('restorative_balm', 'Restorative Balm', 'Clears all stat debuffs from the active Bugemon.', 'heal', 'antidote.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('vivifying_berry', 'Vivifying Berry', 'Restores 30 HP to the active Bugemon.', 'heal', 'baie_vivifiante.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('accuracy_powder', 'Accuracy Powder', 'Increases the active Bugemon''s accuracy by 15 until the end of battle.', 'boost', 'serum_precision.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('speed_tonic', 'Speed Tonic', 'Increases the active Bugemon''s speed by 10 until the end of battle.', 'boost', 'serum_vitesse.png') ON CONFLICT DO NOTHING;
INSERT INTO object (id, nom, description, categorie, sprite) VALUES
  ('mystic_berry', 'Mystic Berry', 'Restores 40 HP to the active Bugemon.', 'heal', 'baie_mystique.png') ON CONFLICT DO NOTHING;


-- effet_object
INSERT INTO effet_object (effet_id, object_id) VALUES (57, 'invigorating_berry') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (58, 'tonic_berry') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (59, 'elixir') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (60, 'offensive_serum') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (61, 'restorative_balm') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (62, 'vivifying_berry') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (63, 'accuracy_powder') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (64, 'speed_tonic') ON CONFLICT DO NOTHING;
INSERT INTO effet_object (effet_id, object_id) VALUES (65, 'mystic_berry') ON CONFLICT DO NOTHING;

-- domain_expansion

INSERT INTO domain_expansion (id, nom, description, background_sprite, boosted_element, cost) VALUES
  ('volcanic_caldera', 'Volcanic Caldera', 'A blazing territory erupts and empowers FIRE attacks.', '/ulb/assets/png/backgrounds/bg_volcan.png', 'Fire', 10) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion (id, nom, description, background_sprite, boosted_element, cost) VALUES
  ('frozen_moon', 'Frozen Moon', 'A silent Blue Moon settles over the arena and strengthens ICE attacks.', '/ulb/assets/png/backgrounds/bg_moon.png', 'Ice', 55) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion (id, nom, description, background_sprite, boosted_element, cost) VALUES
  ('verdant_labyrinth', 'Verdant Labyrinth', 'Roots and vines overrun the battlefield to amplify PLANT attacks.', '/ulb/assets/png/backgrounds/bg_forest.png', 'Plant', 45) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion (id, nom, description, background_sprite, boosted_element, cost) VALUES
  ('radiant_sanctuary', 'Radiant Sanctuary', 'Sacred light floods the battlefield to amplify LIGHT attacks.', '/ulb/assets/png/backgrounds/bg_light.png', 'Light', 45) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion (id, nom, description, background_sprite, boosted_element, cost) VALUES
  ('shadow_veil', 'Shadow Veil', 'Darkness shrouds the battlefield to amplify SHADOW attacks.', '/ulb/assets/png/backgrounds/bg_shadow.png', 'Shadow', 45) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion (id, nom, description, background_sprite, boosted_element, cost) VALUES
  ('iron_bastion', 'Iron Bastion', 'A heavy metallic fortress appears and empowers MECHA attacks.', '/ulb/assets/png/backgrounds/bg_mecha.jpg', 'Mecha', 70) ON CONFLICT DO NOTHING;

-- domain_expansion_bonus

INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('volcanic_caldera', 'attackMagic', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('frozen_moon', 'defense', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('frozen_moon', 'defenseMagic', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('verdant_labyrinth', 'defense', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('radiant_sanctuary', 'defense', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('shadow_veil', 'defense', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('iron_bastion', 'defense', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('iron_bastion', 'defenseMagic', 4) ON CONFLICT DO NOTHING;
INSERT INTO domain_expansion_bonus (expansion_id, stat, modificateur) VALUES ('iron_bastion', 'initiative', 4) ON CONFLICT DO NOTHING;

-- level_reward

INSERT INTO level_reward (niveau, attaque_id) VALUES (10, 'rugissement_montagne') ON CONFLICT DO NOTHING;
INSERT INTO level_reward (niveau, attaque_id) VALUES (20, 'colonne_de_cristal') ON CONFLICT DO NOTHING;
INSERT INTO level_reward (niveau, attaque_id) VALUES (30, 'pluie_de_graviers') ON CONFLICT DO NOTHING;
INSERT INTO level_reward (niveau, attaque_id) VALUES (40, 'cascade_de_galets') ON CONFLICT DO NOTHING;
INSERT INTO level_reward (niveau, attaque_id) VALUES (50, 'tempete_de_cailloux') ON CONFLICT DO NOTHING;

-- skill_tree_node

INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('start', 'Départ', 'Point de départ de l''arbre', 0, 1, 0, 0, TRUE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('hp_1', '+10 HP', 'Tous vos Bugémons commencent avec +10 HP maximum', 1, 3, -1, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('attaque_1', '+3 Attaque', 'Tous vos Bugémons commencent avec +3 Attaque', 1, 3, 1, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('defense_1', '+3 Défense', 'Tous vos Bugémons commencent avec +3 Défense', 1, 3, 0, 1, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('initiative_1', '+5 Initiative', 'Tous vos Bugémons commencent avec +5 Initiative', 1, 2, -1, 2, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('regen_combat', 'Régénération', 'Restaure 10% des PV max de tous vos Bugémons après chaque combat', 2, 1, 0, 2, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('xp_boost', 'Apprentissage rapide', '+20% XP gagnée après chaque combat', 2, 2, 1, 2, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('objets_depart', 'Préparation', 'Commencez avec +2 objets de soin au choix', 2, 1, -2, 2, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('critique_chance', 'Œil critique', '+5% de chance de coup critique', 3, 1, 2, 2, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('hp_2', '+15 HP', 'Tous vos Bugémons commencent avec +15 HP maximum supplémentaires', 2, 2, -1, 3, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('type_boost_flora', 'Maîtrise Plant', '+10% dégâts pour les attaques de type Plant', 3, 1, -2, 3, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('type_boost_pyro', 'Maîtrise Fire', '+10% dégâts pour les attaques de type Fire', 3, 1, 0, 3, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('type_boost_aqua', 'Maîtrise Ice', '+10% dégâts pour les attaques de type Ice', 3, 1, 1, 3, FALSE) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_node (id, nom, description, cout, max_niveau, position_x, position_y, deverrouille) VALUES
  ('recompense_extra', 'Chance du collectionneur', 'Chaque récompense de lvl up propose 4 choix au lieu de 3', 4, 1, 2, 3, FALSE) ON CONFLICT DO NOTHING;

-- skill_tree_prerequis

INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('hp_1', 'start') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('attaque_1', 'start') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('defense_1', 'start') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('initiative_1', 'hp_1') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('regen_combat', 'defense_1') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('xp_boost', 'attaque_1') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('objets_depart', 'hp_1') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('critique_chance', 'attaque_1') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('hp_2', 'initiative_1') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('hp_2', 'regen_combat') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('type_boost_flora', 'objets_depart') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('type_boost_pyro', 'regen_combat') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('type_boost_aqua', 'xp_boost') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('recompense_extra', 'xp_boost') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_prerequis (node_id, prerequis_id) VALUES ('recompense_extra', 'critique_chance') ON CONFLICT DO NOTHING;

-- skill_tree_effet
-- start: no effect
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('hp_1', 'stat_bonus', 'hp', 10, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('attaque_1', 'stat_bonus', 'attack', 3, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('defense_1', 'stat_bonus', 'defense', 3, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('initiative_1', 'stat_bonus', 'initiative', 5, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('regen_combat', 'regen_post_combat', NULL, NULL, 10, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('xp_boost', 'xp_multiplicateur', NULL, 1.2, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('objets_depart', 'objets_bonus', NULL, NULL, NULL, 2, 'heal', NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('critique_chance', 'critique_bonus', NULL, 5, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('hp_2', 'stat_bonus', 'hp', 15, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('type_boost_flora', 'type_multiplicateur', NULL, 1.1, NULL, NULL, NULL, 'Plant') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('type_boost_pyro', 'type_multiplicateur', NULL, 1.1, NULL, NULL, NULL, 'Fire') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('type_boost_aqua', 'type_multiplicateur', NULL, 1.1, NULL, NULL, NULL, 'Ice') ON CONFLICT DO NOTHING;
INSERT INTO skill_tree_effet (node_id, type, stat, valeur, valeur_pourcent, quantite, categorie, type_cible) VALUES
  ('recompense_extra', 'recompense_choix', NULL, 4, NULL, NULL, NULL, NULL) ON CONFLICT DO NOTHING;

-- floor
INSERT INTO floor (numero) VALUES (1) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (2) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (3) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (4) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (5) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (6) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (7) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (8) ON CONFLICT DO NOTHING;
INSERT INTO floor (numero) VALUES (9) ON CONFLICT DO NOTHING;


-- floor_team

-- Floor 1 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (1, 1, FALSE, 'Alpha Beta', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (2, 1, FALSE, 'Ahedo Beta', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (3, 1, FALSE, 'Well no idea', 3) ON CONFLICT DO NOTHING;
-- Floor 1 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (4, 1, TRUE, 'Mamamia', 1) ON CONFLICT DO NOTHING;

-- Floor 2 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (5, 2, FALSE, 'floor2_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (6, 2, FALSE, 'floor2_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (7, 2, FALSE, 'floor2_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 2 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (8, 2, TRUE, 'floor2_boss', 1) ON CONFLICT DO NOTHING;

-- Floor 3 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (9, 3, FALSE, 'floor3_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (10, 3, FALSE, 'floor3_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (11, 3, FALSE, 'floor3_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 3 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (12, 3, TRUE, 'floor3_boss', 1) ON CONFLICT DO NOTHING;

-- Floor 4 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (13, 4, FALSE, 'floor4_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (14, 4, FALSE, 'floor4_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (15, 4, FALSE, 'floor4_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 4 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (16, 4, TRUE, 'floor4_boss', 1) ON CONFLICT DO NOTHING;

-- Floor 5 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (17, 5, FALSE, 'floor5_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (18, 5, FALSE, 'floor5_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (19, 5, FALSE, 'floor5_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 5 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (20, 5, TRUE, 'floor5_boss', 1) ON CONFLICT DO NOTHING;

-- Floor 6 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (21, 6, FALSE, 'floor6_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (22, 6, FALSE, 'floor6_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (23, 6, FALSE, 'floor6_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 6 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (24, 6, TRUE, 'floor6_boss', 1) ON CONFLICT DO NOTHING;

-- Floor 7 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (25, 7, FALSE, 'floor7_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (26, 7, FALSE, 'floor7_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (27, 7, FALSE, 'floor7_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 7 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (28, 7, TRUE, 'floor7_boss', 3) ON CONFLICT DO NOTHING;

-- Floor 8 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (29, 8, FALSE, 'floor8_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (30, 8, FALSE, 'floor8_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (31, 8, FALSE, 'floor8_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 8 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (32, 8, TRUE, 'floor8_boss', 1) ON CONFLICT DO NOTHING;

-- Floor 9 enemy teams
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (33, 9, FALSE, 'floor9_enemy_room1', 1) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (34, 9, FALSE, 'floor9_enemy_room2', 2) ON CONFLICT DO NOTHING;
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (35, 9, FALSE, 'floor9_enemy_room3', 3) ON CONFLICT DO NOTHING;
-- Floor 9 boss
INSERT INTO floor_team (id, floor_numero, is_boss, nom, nombre_bugemons) VALUES (36, 9, TRUE, 'floor9_boss', 1) ON CONFLICT DO NOTHING;

SELECT setval('floor_team_id_seq', 36);

-- floor_team_bugemon

-- Team 1 (Alpha Beta, floor 1): numberOfBugemon=1
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (1, 'bulbovila', 1, 0, 25, 55, 40, 50, 25, 55, 55) ON CONFLICT DO NOTHING;

-- Team 2 (Ahedo Beta, floor 1): numberOfBugemon=2, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (2, 'tortobush', 1, 0, 45, 60, 45, 55, 45, 60, 50) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (2, 'icepinou', 1, 0, 25, 60, 50, 55, 25, 60, 50) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (2, 'cervipetale', 2, 15, 40, 54, 52, 63, 40, 54, 52) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (2, 'engrenon', 2, 15, 44, 59, 59, 54, 44, 59, 54) ON CONFLICT DO NOTHING;

-- Team 3 (Well no idea, floor 1): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (3, 'cervipetale', 2, 15, 40, 54, 52, 63, 40, 54, 52) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (3, 'engrenon', 2, 15, 44, 59, 59, 54, 44, 59, 54) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (3, 'vulcanite', 2, 15, 65, 54, 79, 44, 65, 54, 29) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (3, 'bulbovila', 1, 0, 25, 55, 40, 50, 25, 55, 55) ON CONFLICT DO NOTHING;

-- Team 4 (Mamamia, floor 1 boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (4, 'aiguillon', 3, 30, 80, 42, 48, 40, 80, 42, 42) ON CONFLICT DO NOTHING;

-- Team 5 (floor2_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (5, 'flameche', 2, 20, 83, 74, 39, 69, 83, 74, 59) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (5, 'cryoverne', 2, 20, 93, 54, 69, 49, 93, 54, 44) ON CONFLICT DO NOTHING;

-- Team 6 (floor2_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (6, 'cryoverne', 2, 20, 93, 54, 69, 49, 93, 54, 44) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (6, 'darkgon', 2, 20, 103, 59, 74, 39, 103, 59, 34) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (6, 'flameche', 2, 20, 83, 74, 39, 69, 83, 74, 59) ON CONFLICT DO NOTHING;

-- Team 7 (floor2_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (7, 'floricolosse', 3, 40, 112, 76, 36, 51, 112, 76, 66) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (7, 'infernoroc', 3, 40, 97, 66, 51, 61, 97, 66, 51) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (7, 'cyberfaon', 3, 40, 107, 51, 66, 56, 107, 51, 51) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (7, 'darkgon', 2, 20, 103, 59, 74, 39, 103, 59, 34) ON CONFLICT DO NOTHING;

-- Team 8 (floor2_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (8, 'drakoferno', 4, 60, 100, 58, 52, 52, 100, 58, 58) ON CONFLICT DO NOTHING;

-- Team 9 (floor3_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (9, 'icepinou', 3, 50, 102, 68, 58, 63, 102, 68, 58) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (9, 'cervipetale', 4, 70, 101, 66, 66, 76, 101, 66, 66) ON CONFLICT DO NOTHING;

-- Team 10 (floor3_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (10, 'mantissabre', 4, 70, 140, 82, 47, 47, 140, 82, 67) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (10, 'flameche', 4, 70, 91, 82, 47, 77, 91, 82, 67) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (10, 'icepinou', 3, 50, 102, 68, 58, 63, 102, 68, 58) ON CONFLICT DO NOTHING;

-- Team 11 (floor3_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (11, 'cryoverne', 4, 70, 101, 62, 77, 57, 101, 62, 52) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (11, 'aiguillon', 4, 70, 126, 82, 72, 37, 126, 82, 52) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (11, 'cervipetale', 4, 70, 101, 66, 66, 76, 101, 66, 66) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (11, 'mantissabre', 4, 70, 140, 82, 47, 47, 140, 82, 67) ON CONFLICT DO NOTHING;

-- Team 12 (floor3_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (12, 'floricolosse', 5, 90, 120, 72, 52, 62, 120, 72, 72) ON CONFLICT DO NOTHING;

-- Team 13 (floor4_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (13, 'champimort', 4, 80, 111, 61, 71, 56, 111, 61, 61) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (13, 'iron-blight', 5, 100, 130, 70, 100, 70, 130, 70, 65) ON CONFLICT DO NOTHING;

-- Team 14 (floor4_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (14, 'drakoferno', 5, 100, 120, 105, 60, 85, 120, 105, 90) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (14, 'iron-blight', 5, 100, 130, 70, 100, 70, 130, 70, 65) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (14, 'champimort', 4, 80, 111, 61, 71, 56, 111, 61, 61) ON CONFLICT DO NOTHING;

-- Team 15 (floor4_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (15, 'floricolosse', 5, 100, 130, 90, 50, 65, 130, 90, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (15, 'cyberfaon', 5, 100, 125, 65, 80, 70, 125, 65, 65) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (15, 'dogor', 5, 100, 150, 95, 75, 40, 150, 95, 65) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (15, 'iron-blight', 5, 100, 130, 70, 100, 70, 130, 70, 65) ON CONFLICT DO NOTHING;

-- Team 16 (floor4_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (16, 'snowarcher', 6, 120, 140, 85, 65, 72, 140, 85, 78) ON CONFLICT DO NOTHING;

-- Team 17 (floor5_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (17, 'vulcanite', 5, 110, 120, 80, 85, 50, 120, 80, 55) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (17, 'icepinou', 6, 130, 115, 85, 75, 80, 115, 85, 75) ON CONFLICT DO NOTHING;

-- Team 18 (floor5_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (18, 'pikoflam', 6, 130, 125, 105, 45, 75, 125, 105, 90) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (18, 'icepinou', 6, 130, 115, 85, 75, 80, 115, 85, 75) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (18, 'vulcanite', 5, 110, 120, 80, 85, 50, 120, 80, 55) ON CONFLICT DO NOTHING;

-- Team 19 (floor5_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (19, 'cervipetale', 6, 130, 115, 80, 80, 90, 115, 80, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (19, 'icemonster', 6, 130, 140, 95, 80, 65, 140, 95, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (19, 'noctheris', 6, 130, 135, 95, 95, 60, 135, 95, 65) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (19, 'icepinou', 6, 130, 115, 85, 75, 80, 115, 85, 75) ON CONFLICT DO NOTHING;

-- Team 20 (floor5_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (20, 'noctheris', 7, 150, 155, 95, 72, 80, 155, 95, 88) ON CONFLICT DO NOTHING;

-- Team 21 (floor6_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (21, 'cyberfaon', 6, 140, 125, 75, 90, 80, 125, 75, 75) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (21, 'cryoverne', 7, 160, 116, 77, 92, 72, 116, 77, 67) ON CONFLICT DO NOTHING;

-- Team 22 (floor6_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (22, 'mantissabre', 7, 160, 155, 105, 70, 70, 155, 105, 90) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (22, 'cryoverne', 7, 160, 116, 77, 92, 72, 116, 77, 67) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (22, 'cyberfaon', 6, 140, 125, 75, 90, 80, 125, 75, 75) ON CONFLICT DO NOTHING;

-- Team 23 (floor6_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (23, 'pikoflam', 7, 160, 135, 115, 55, 85, 135, 115, 100) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (23, 'champimort', 7, 160, 130, 80, 90, 75, 130, 80, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (23, 'aiguillon', 7, 160, 145, 105, 95, 60, 145, 105, 75) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (23, 'mantissabre', 7, 160, 155, 105, 70, 70, 155, 105, 90) ON CONFLICT DO NOTHING;

-- Team 24 (floor6_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (24, 'lightelf', 8, 180, 168, 100, 82, 85, 168, 100, 95) ON CONFLICT DO NOTHING;

-- Team 25 (floor7_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (25, 'floricolosse', 7, 170, 145, 105, 65, 80, 145, 105, 95) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (25, 'cyberfaon', 8, 190, 135, 85, 100, 90, 135, 85, 85) ON CONFLICT DO NOTHING;

-- Team 26 (floor7_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (26, 'drakoferno', 8, 190, 140, 125, 80, 105, 140, 125, 110) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (26, 'cyberfaon', 8, 190, 135, 85, 100, 90, 135, 85, 85) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (26, 'floricolosse', 7, 170, 145, 105, 65, 80, 145, 105, 95) ON CONFLICT DO NOTHING;

-- Team 27 (floor7_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (27, 'mantissabre', 8, 190, 160, 110, 75, 75, 160, 110, 95) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (27, 'icepinou', 8, 190, 125, 95, 85, 90, 125, 95, 85) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (27, 'dogor', 8, 190, 165, 110, 90, 55, 165, 110, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (27, 'drakoferno', 8, 190, 140, 125, 80, 105, 140, 125, 110) ON CONFLICT DO NOTHING;

-- Team 28 (floor7_boss): 3 membres
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (28, 'pikoflam', 9, 210, 145, 118, 68, 90, 145, 118, 105) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (28, 'icemonster', 9, 210, 152, 108, 92, 78, 152, 108, 92) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (28, 'noctheris', 9, 210, 148, 108, 108, 72, 148, 108, 78) ON CONFLICT DO NOTHING;

-- Team 29 (floor8_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (29, 'iron-blight', 8, 200, 140, 80, 110, 80, 140, 80, 75) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (29, 'infernoroc', 9, 220, 130, 95, 80, 90, 130, 95, 80) ON CONFLICT DO NOTHING;

-- Team 30 (floor8_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (30, 'floricolosse', 9, 220, 150, 110, 70, 85, 150, 110, 100) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (30, 'infernoroc', 9, 220, 130, 95, 80, 90, 130, 95, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (30, 'iron-blight', 8, 200, 140, 80, 110, 80, 140, 80, 75) ON CONFLICT DO NOTHING;

-- Team 31 (floor8_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (31, 'mantissabre', 9, 220, 165, 115, 80, 80, 165, 115, 100) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (31, 'cyberfaon', 9, 220, 140, 90, 105, 95, 140, 90, 90) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (31, 'aiguillon', 9, 220, 150, 110, 100, 65, 150, 110, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (31, 'floricolosse', 9, 220, 150, 110, 70, 85, 150, 110, 100) ON CONFLICT DO NOTHING;

-- Team 32 (floor8_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (32, 'iron-blight', 10, 240, 185, 128, 88, 108, 185, 128, 112) ON CONFLICT DO NOTHING;

-- Team 33 (floor9_enemy_room1): numberOfBugemon=1, pool of 2
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (33, 'noctheris', 10, 250, 155, 115, 115, 80, 155, 115, 85) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (33, 'pikoflam', 10, 250, 150, 130, 70, 100, 150, 130, 115) ON CONFLICT DO NOTHING;

-- Team 34 (floor9_enemy_room2): numberOfBugemon=2, pool of 3
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (34, 'mantissabre', 10, 250, 170, 120, 85, 85, 170, 120, 105) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (34, 'pikoflam', 10, 250, 150, 130, 70, 100, 150, 130, 115) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (34, 'noctheris', 10, 250, 155, 115, 115, 80, 155, 115, 85) ON CONFLICT DO NOTHING;

-- Team 35 (floor9_enemy_room3): numberOfBugemon=3, pool of 4
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'floricolosse', 10, 250, 155, 115, 75, 90, 155, 115, 105) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'icemonster', 10, 250, 165, 120, 105, 90, 165, 120, 105) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'dogor', 10, 250, 175, 120, 100, 65, 175, 120, 90) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'pikoflam', 10, 250, 150, 130, 70, 100, 150, 130, 115) ON CONFLICT DO NOTHING;

-- Team 36 (floor9_boss)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (36, 'afo', 15, 500, 380, 195, 155, 110, 380, 195, 155) ON CONFLICT DO NOTHING;

-- Additional floor_team_bugemon entries so every non-starter bugemon is reachable in the tower

-- Floor 1 additions (teams 2-3)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (2, 'moussil', 2, 15, 85, 65, 50, 60, 85, 65, 55) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (3, 'verdurion', 2, 15, 105, 75, 35, 50, 105, 75, 65) ON CONFLICT DO NOTHING;

-- Floor 2 additions (teams 5, 7)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (5, 'loopine', 2, 15, 90, 55, 55, 65, 90, 55, 55) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (7, 'exceflam', 3, 40, 95, 75, 50, 70, 95, 75, 60) ON CONFLICT DO NOTHING;

-- Floor 3 additions (teams 9, 11)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (9, 'pyricore', 4, 70, 90, 85, 50, 80, 90, 85, 70) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (11, 'commitide', 4, 70, 110, 70, 70, 65, 110, 70, 65) ON CONFLICT DO NOTHING;

-- Floor 4 additions (teams 13-15)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (13, 'mergeau', 5, 100, 110, 80, 70, 75, 110, 80, 70) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (14, 'refaquix', 5, 100, 105, 70, 85, 65, 105, 70, 60) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (15, 'testide', 5, 100, 120, 60, 90, 60, 120, 60, 55) ON CONFLICT DO NOTHING;

-- Floor 5 additions (teams 17-19)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (17, 'buildwave', 6, 130, 120, 70, 85, 75, 120, 70, 70) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (18, 'tidehunter', 6, 130, 135, 90, 75, 60, 135, 90, 75) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (19, 'hivernot', 6, 130, 105, 80, 75, 90, 105, 120, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (19, 'givrogre', 6, 130, 120, 95, 75, 85, 120, 75, 85) ON CONFLICT DO NOTHING;

-- Floor 6 additions (teams 21-23)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (21, 'rockachu', 7, 160, 130, 90, 95, 60, 130, 90, 65) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (22, 'granitron', 7, 160, 140, 100, 90, 55, 140, 100, 70) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (23, 'geodrift', 7, 160, 125, 85, 100, 65, 125, 85, 60) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (23, 'neoncobra', 7, 160, 125, 95, 105, 110, 125, 95, 85) ON CONFLICT DO NOTHING;

-- Floor 7 additions (teams 25-27)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (25, 'bouldax', 8, 200, 155, 110, 90, 55, 155, 110, 80) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (26, 'pebblit', 8, 200, 115, 85, 110, 75, 115, 85, 60) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (27, 'obsidian', 8, 200, 140, 100, 100, 65, 140, 100, 70) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (27, 'opticat', 8, 200, 110, 110, 90, 110, 110, 95, 85) ON CONFLICT DO NOTHING;

-- Floor 8 additions (teams 29-31)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (29, 'darkelf', 9, 220, 100, 115, 85, 80, 100, 130, 95) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (30, 'demonix', 9, 220, 120, 120, 105, 100, 120, 90, 95) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (31, 'lumino', 9, 220, 110, 105, 80, 70, 110, 115, 75) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (31, 'candlion', 9, 220, 125, 100, 70, 95, 125, 120, 80) ON CONFLICT DO NOTHING;

-- Floor 9 additions (teams 33-35)
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (33, 'bugzilla', 10, 250, 165, 115, 80, 80, 165, 115, 100) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (34, 'crasheon', 10, 250, 145, 125, 65, 95, 145, 125, 110) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (34, 'pyroxis', 10, 250, 130, 105, 90, 100, 130, 105, 90) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'lightfox', 10, 250, 135, 110, 85, 140, 135, 130, 85) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'araknolight', 10, 250, 140, 110, 100, 115, 140, 100, 110) ON CONFLICT DO NOTHING;
INSERT INTO floor_team_bugemon (team_id, bugemon_id, niveau, xp, hp, attaque, defense, initiative, max_hp, attack_magic, defense_magic) VALUES
  (35, 'firefox', 10, 250, 125, 125, 75, 115, 125, 125, 85) ON CONFLICT DO NOTHING;

-- starting_inventory
INSERT INTO starting_inventory (item_id, quantite) VALUES ('invigorating_berry', 3) ON CONFLICT DO NOTHING;
INSERT INTO starting_inventory (item_id, quantite) VALUES ('tonic_berry', 2) ON CONFLICT DO NOTHING;
INSERT INTO starting_inventory (item_id, quantite) VALUES ('elixir', 1) ON CONFLICT DO NOTHING;
INSERT INTO starting_inventory (item_id, quantite) VALUES ('offensive_serum', 1) ON CONFLICT DO NOTHING;

-- bugemons
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('florachu', 'Florachu', 'Plant', 90, 40, 40, 55, 55, 50, 'florachu.png', true, 'verdant_labyrinth') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('moussil', 'Moussil', 'Plant', 80, 60, 45, 60, 50, 55, 'moussil.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('verdurion', 'Verdurion', 'Plant', 100, 70, 30, 70, 60, 45, 'verdurion.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('loopine', 'Loopine', 'Plant', 85, 50, 50, 50, 50, 60, 'loopine.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('lichenox', 'Lichenox', 'Plant', 95, 45, 55, 45, 45, 40, 'lichenox.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('bugzilla', 'Bugzilla', 'Plant', 120, 70, 35, 70, 55, 35, 'bugzilla.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('exceflam', 'Exceflam', 'Fire', 85, 65, 40, 65, 50, 60, 'exceflam.png', false, 'volcanic_caldera') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('pyricore', 'Pyricore', 'Fire', 75, 70, 35, 70, 55, 65, 'pyricore.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('inferlin', 'Inferlin', 'Fire', 90, 75, 30, 75, 60, 55, 'inferlin.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('crasheon', 'Crasheon', 'Fire', 100, 80, 20, 80, 65, 50, 'crasheon.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('pyroxis', 'Pyroxis', 'Fire', 85, 60, 45, 60, 45, 55, 'pyroxis.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('finalboss', 'FinalBoss', 'Fire', 150, 85, 40, 85, 60, 45, 'finalboss.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('commitide', 'Commitide', 'Ice', 95, 55, 55, 55, 50, 50, 'commitide.png', false, 'frozen_moon') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('mergeau', 'Mergeau', 'Ice', 90, 60, 50, 60, 50, 55, 'mergeau.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('refaquix', 'Refaquix', 'Ice', 85, 50, 65, 50, 40, 45, 'refaquix.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('testide', 'Testide', 'Ice', 100, 40, 70, 40, 35, 40, 'testide.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('buildwave', 'Buildwave', 'Ice', 95, 45, 60, 45, 45, 50, 'buildwave.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('tidehunter', 'Tidehunter', 'Ice', 110, 65, 50, 65, 50, 35, 'tidehunter.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('rockachu', 'Rockachu', 'Mecha', 100, 60, 65, 60, 35, 30, 'rockachu.png', false, 'iron_bastion') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('granitron', 'Granitron', 'Mecha', 110, 70, 60, 70, 40, 25, 'granitron.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('geodrift', 'Geodrift', 'Mecha', 95, 55, 70, 55, 30, 35, 'geodrift.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('bouldax', 'Bouldax', 'Mecha', 120, 75, 55, 75, 45, 20, 'bouldax.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('pebblit', 'Pebblit', 'Mecha', 80, 50, 75, 50, 25, 40, 'pebblit.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('obsidian', 'Obsidian', 'Mecha', 105, 65, 65, 65, 35, 30, 'obsidian.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('darkgon', 'Darkgon', 'Shadow', 110, 75, 60, 75, 50, 35, 'darkgon.png', false, 'shadow_veil') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('scarabot', 'Scarabot', 'Mecha', 85, 55, 55, 55, 55, 55, 'scarabot.png', true, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('dogor', 'Dogor', 'Light', 90, 70, 35, 70, 45, 65, 'dogor.png', true, 'radiant_sanctuary') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('firefox', 'Firefox', 'Fire', 80, 80, 30, 80, 40, 70, 'firefox.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('icemonster', 'IceMonster', 'Ice', 105, 65, 60, 65, 55, 30, 'icemonster.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('iron-blight', 'Iron-Blight', 'Mecha', 90, 40, 40, 55, 55, 50, 'iron-blight.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('drakoferno', 'Drakoferno', 'Fire', 130, 90, 45, 95, 40, 70, 'drakoferno.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('flameche', 'Flameche', 'Fire', 70, 60, 60, 80, 40, 90, 'flameche.png', true, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('infernoroc', 'Infernoroc', 'Fire', 100, 90, 40, 80, 40, 50, 'infernoroc.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('pikoflam', 'Pikoflam', 'Fire', 85, 60, 40, 95, 30, 65, 'pikoflam.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('vulcanite', 'Vulcanite', 'Fire', 85, 40, 80, 55, 60, 55, 'vulcanite.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('tortobush', 'Tortobush', 'Plant', 95, 35, 60, 40, 70, 30, 'tortobush.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('floricolosse', 'Floricolosse', 'Plant', 135, 60, 95, 40, 70, 30, 'floricolosse.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('cervipetale', 'Cervipetale', 'Plant', 95, 70, 50, 55, 50, 80, 'cervipetale.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('mantissabre', 'Mantissabre', 'Plant', 70, 85, 45, 75, 55, 90, 'mantissabre.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('bulbovila', 'Bulbovila', 'Plant', 80, 45, 60, 50, 70, 60, 'bulbovila.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('lumino', 'Lumino', 'Light', 70, 65, 40, 75, 35, 30, 'lumino.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('lightfox', 'Lightfox', 'Light', 90, 65, 40, 85, 40, 95, 'lightfox.png', false, 'radiant_sanctuary') ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('candlion', 'Candlion', 'Light', 85, 60, 30, 80, 40, 55, 'candlion.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('araknolight', 'Araknolight', 'Light', 95, 65, 55, 55, 65, 70, 'araknolight.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('lightelf', 'Lightelf', 'Light', 140, 75, 40, 85, 40, 85, 'lightelf.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('icepinou', 'Icepinou', 'Ice', 60, 70, 45, 50, 55, 65, 'icepinou.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('hivernot', 'Hivernot', 'Ice', 80, 55, 50, 95, 55, 65, 'hivernot.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('snowarcher', 'Snowarcher', 'Ice', 135, 70, 60, 50, 70, 80, 'snowarcher.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('cryoverne', 'Cryoverne', 'Ice', 100, 40, 55, 45, 65, 55, 'cryoverne.png', true, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('givrogre', 'Givrogre', 'Ice', 95, 70, 50, 50, 60, 60, 'givrogre.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('afo', 'AFO', 'All', 300, 160, 120, 160, 120, 90, 'afo.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('corvax', 'Corvax', 'Shadow', 75, 65, 50, 65, 55, 45, 'corvax.png', true, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('darkelf', 'Darkelf', 'Shadow', 60, 75, 45, 90, 55, 40, 'darkelf.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('demonix', 'Demonix', 'Shadow', 80, 80, 65, 50, 55, 60, 'demonix.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('champimort', 'Champimort', 'Shadow', 100, 65, 70, 65, 45, 65, 'champimort.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('noctheris', 'Noctheris', 'Shadow', 125, 85, 65, 80, 60, 55, 'noctheris.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('engrenon', 'Engrenon', 'Mecha', 110, 45, 55, 45, 75, 50, 'engrenon.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('cyberfaon', 'Cyberfaon', 'Mecha', 85, 65, 55, 65, 55, 70, 'cyberfaon.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('neoncobra', 'Neoncobra', 'Mecha', 95, 65, 75, 65, 55, 80, 'neoncobra.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('opticat', 'Opticat', 'Mecha', 75, 75, 55, 60, 50, 75, 'opticat.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;
INSERT INTO bugemon(id, nom, type, pv, attaque, defense, magic_attaque, magic_defense, initiative, sprite, starter, domain_expansion_id) VALUES ('aiguillon', 'Aiguillon', 'Mecha', 125, 80, 75, 85, 65, 45, 'aiguillon.png', false, NULL) ON CONFLICT (id) DO UPDATE SET magic_attaque = EXCLUDED.magic_attaque, magic_defense = EXCLUDED.magic_defense, domain_expansion_id = EXCLUDED.domain_expansion_id;

-- bugemon_attaque
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('florachu', 'fouet_liane') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('florachu', 'pollen_sournois') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('florachu', 'racines_vives') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('moussil', 'spore_collante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('moussil', 'feuille_tranchante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('moussil', 'vieux_ronce') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('verdurion', 'croissance_brutale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('verdurion', 'charge_sylvestre') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('verdurion', 'fouet_liane') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('loopine', 'racines_vives') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('loopine', 'pollen_sournois') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('loopine', 'feuille_tranchante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lichenox', 'spore_collante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lichenox', 'vieux_ronce') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lichenox', 'mur_de_lianes') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bugzilla', 'charge_sylvestre') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bugzilla', 'croissance_brutale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bugzilla', 'spore_collante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('exceflam', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('exceflam', 'flammes_folles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('exceflam', 'choc_brulant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pyricore', 'flammes_folles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pyricore', 'surchauffe') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pyricore', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('inferlin', 'surchauffe') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('inferlin', 'choc_brulant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('inferlin', 'brasier_sauvage') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('crasheon', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('crasheon', 'flammes_folles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('crasheon', 'choc_brulant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pyroxis', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pyroxis', 'surchauffe') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pyroxis', 'brasier_sauvage') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('finalboss', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('finalboss', 'surchauffe') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('finalboss', 'flammes_folles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('commitide', 'vague_rapide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('commitide', 'courant_croise') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('commitide', 'pluie_calme') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('mergeau', 'courant_croise') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('mergeau', 'vague_rapide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('mergeau', 'maree_stable') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('refaquix', 'maree_stable') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('refaquix', 'pluie_calme') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('refaquix', 'vague_rapide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('testide', 'pluie_calme') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('testide', 'eau_pure') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('testide', 'maree_stable') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('buildwave', 'eau_pure') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('buildwave', 'vague_rapide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('buildwave', 'maree_stable') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('tidehunter', 'vague_rapide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('tidehunter', 'courant_croise') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('tidehunter', 'eau_pure') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('rockachu', 'jet_de_pierres') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('rockachu', 'armure_minerale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('rockachu', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('granitron', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('granitron', 'avalanche') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('granitron', 'jet_de_pierres') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('geodrift', 'armure_minerale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('geodrift', 'jet_de_pierres') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('geodrift', 'pluie_de_graviers') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bouldax', 'avalanche') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bouldax', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bouldax', 'armure_minerale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pebblit', 'pluie_de_graviers') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pebblit', 'armure_minerale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pebblit', 'jet_de_pierres') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('obsidian', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('obsidian', 'jet_de_pierres') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('obsidian', 'avalanche') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('darkgon', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('darkgon', 'avalanche') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('darkgon', 'armure_minerale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('scarabot', 'fouet_liane') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('scarabot', 'pollen_sournois') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('scarabot', 'feuille_tranchante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('dogor', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('dogor', 'choc_brulant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('dogor', 'brasier_sauvage') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('firefox', 'flammes_folles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('firefox', 'surchauffe') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('firefox', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('icemonster', 'eau_pure') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('icemonster', 'maree_stable') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('icemonster', 'vague_rapide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('iron-blight', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('iron-blight', 'avalanche') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('iron-blight', 'armure_minerale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('drakoferno', 'meteore_enflamme') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('drakoferno', 'frappe_incandescente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('drakoferno', 'embrasement') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('flameche', 'flammes_folles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('flameche', 'embrasement') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('flameche', 'langue_de_feu') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('infernoroc', 'crache_flammes') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('infernoroc', 'choc_brulant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('infernoroc', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pikoflam', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pikoflam', 'cendres_volantes') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('pikoflam', 'tornade_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('vulcanite', 'choc_brulant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('vulcanite', 'langue_de_feu') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('vulcanite', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('tortobush', 'cocon_protecteur') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('tortobush', 'floraison_force') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('tortobush', 'tempete_feuilles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('floricolosse', 'floraison_force') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('floricolosse', 'tranchant_bambou') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('floricolosse', 'tempete_feuilles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cervipetale', 'floraison_force') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cervipetale', 'spores_sommeil') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cervipetale', 'tempete_feuilles') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('mantissabre', 'lancer_semence') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('mantissabre', 'cocon_protecteur') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('mantissabre', 'floraison_force') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bulbovila', 'spores_sommeil') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bulbovila', 'floraison_force') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('bulbovila', 'lancer_semence') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lumino', 'eclat_lumineux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lumino', 'halo_protecteur') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lumino', 'flash_aveuglant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lightfox', 'aura_stimulante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lightfox', 'orbe_etincelant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lightfox', 'rafale_photonique') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('candlion', 'flash_aveuglant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('candlion', 'cascade_claire') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('candlion', 'halo_protecteur') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('araknolight', 'rafale_photonique') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('araknolight', 'aura_stimulante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('araknolight', 'reflet_tranchant') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lightelf', 'lame_radieuse') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lightelf', 'rafale_photonique') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('lightelf', 'halo_protecteur') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('icepinou', 'maree_gelee') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('icepinou', 'torrent_polaire') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('icepinou', 'eclats_de_givre') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('hivernot', 'brume_froide') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('hivernot', 'maree_gelee') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('hivernot', 'eclats_de_givre') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('snowarcher', 'blizzard_dense') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('snowarcher', 'rafale_cristalline') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('snowarcher', 'pic_glacial') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cryoverne', 'carapace_givree') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cryoverne', 'rafale_cristalline') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cryoverne', 'torrent_polaire') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('givrogre', 'eclats_de_givre') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('givrogre', 'pic_glacial') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('givrogre', 'carapace_givree') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('afo', 'explosion_ardente') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('afo', 'fouet_liane') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('afo', 'fracas_rocheux') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('corvax', 'malefice_noir') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('corvax', 'drain_dame') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('corvax', 'ombre_foudroyante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('darkelf', 'malefice_noir') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('darkelf', 'drain_dame') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('darkelf', 'ombre_foudroyante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('demonix', 'poigne_infernale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('demonix', 'coup_cacher') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('demonix', 'ombre_foudroyante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('champimort', 'malefice_noir') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('champimort', 'poigne_infernale') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('champimort', 'rage_demoniaque') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('noctheris', 'ombre_foudroyante') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('noctheris', 'rage_demoniaque') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('noctheris', 'drain_dame') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('engrenon', 'renfort_acier') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('engrenon', 'projection_boulon') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('engrenon', 'impact_metallique') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cyberfaon', 'charge_lourde') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cyberfaon', 'renfort_acier') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('cyberfaon', 'tir_precision') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('neoncobra', 'charge_lourde') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('neoncobra', 'renfort_acier') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('neoncobra', 'tir_precision') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('opticat', 'impact_metallique') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('opticat', 'projection_boulon') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('opticat', 'tir_precision') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('aiguillon', 'charge_lourde') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('aiguillon', 'frappe_piston') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
INSERT INTO bugemon_attaque(bugemon_id, attaque_id) VALUES ('aiguillon', 'impact_metallique') ON CONFLICT (attaque_id, bugemon_id) DO NOTHING;
