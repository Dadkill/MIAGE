//-------------------------------------------------------
// MiageMan PROJECT
// ButtonTilesManager.cs SCRIPT
//
// Written at 17/02/2019 19:34
// Written by Théobredoux - contact@warudo.fr
//-------------------------------------------------------

using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Tilemaps;

namespace Scripts.CustomTiles
{
    [Serializable]
	public class TilesManager : MonoBehaviour 
	{
        public Tilemap locked_doors_tilemap;
        public Tilemap enigm_button_tilemap;
        public List<ButtonTile> buttonsOff;
        public List<ButtonTile> buttonsOn;
        

        private List<Vector3Int> doors_first_enigm;
        private List<Vector3Int> doors_second_enigm;
        private List<Vector3Int> doors_choice_one_third_enigm; // Les portes du premier choix.
        private List<Vector3Int> doors_choice_two_third_enigm; // Les portes du deuxième choix.
        private List<Vector3Int> doors_fourth_enigm;
        private List<Tile> third_bad_door_to_lock;

        private bool[] buttons_state = new bool[3];
        public string button_playerCode;
        public string button_goodCode = "753";
        public bool button_enigm_finded = false;

        private bool olivettom_enigm_01_finded = false;
        private bool olivettom_enigm_02_finded = false;

        public Tilemap chests_tilemap;
        public List<ChestTile> chestsClose;
        public List<ChestTile> chestsOpen;
        public Tile topchest_tile;

        public Tilemap cols_enigm_tilemap;
        private bool[] cols_state = new bool[4];
        public string cols_playercode;
        public string cols_goodcode = "1324";
        public bool cols_enigm_finded = false;
        public List<ButtonTile> cols_off;
        public List<ButtonTile> cols_on;        // ButtonTile possède le même comportement qu'un pilier.
        
        public bool Olivettom_Enigm_01_Finded
        {
            get { return olivettom_enigm_01_finded; }
        }

        public bool Olivettom_Enigm_02_Finded
        {
            get { return olivettom_enigm_02_finded; }
        }

        /// <summary>
        /// Réinitialise les états des tiles et des énigmes.
        /// </summary>
        public void Restart ()
        {
            olivettom_enigm_01_finded = false;
            olivettom_enigm_02_finded = false;

            cols_state = new bool[4];
            cols_playercode = string.Empty;
            cols_enigm_finded = false;

            buttons_state = new bool[3];
            button_playerCode = string.Empty;
            button_enigm_finded = false;
        }
        /// <summary>
        /// Initialise chaque liste contenant la position des portes lié à chaque énigme. 
        /// </summary>
        private void SetDoorsPositions()
        {
            // Exemple : Toutes les positions de chaque tile (en jeu) des portes de l'énigme numéro 1. 
            // (Permet de les supprimer lorsque le joueur réussi une énigme).
            doors_first_enigm = new List<Vector3Int>();
            doors_first_enigm.Add(new Vector3Int(-4, 5, 0));
            doors_first_enigm.Add(new Vector3Int(-5, 5, 0));
            doors_first_enigm.Add(new Vector3Int(-6, 5, 0));
            doors_first_enigm.Add(new Vector3Int(-4, 9, 0));
            doors_first_enigm.Add(new Vector3Int(-5, 9, 0));
            doors_first_enigm.Add(new Vector3Int(-6, 9, 0));

            doors_second_enigm = new List<Vector3Int>();
            doors_second_enigm.Add(new Vector3Int(6, 1, 0));
            doors_second_enigm.Add(new Vector3Int(6, 0, 0));
            doors_second_enigm.Add(new Vector3Int(6, -1, 0));
            doors_second_enigm.Add(new Vector3Int(10, 1, 0));
            doors_second_enigm.Add(new Vector3Int(10, 0, 0));
            doors_second_enigm.Add(new Vector3Int(10, -1, 0));

            doors_choice_one_third_enigm = new List<Vector3Int>();
            doors_choice_one_third_enigm.Add(new Vector3Int(22, 28, 0));
            doors_choice_one_third_enigm.Add(new Vector3Int(23, 28, 0));
            doors_choice_one_third_enigm.Add(new Vector3Int(24, 28, 0));
            doors_choice_one_third_enigm.Add(new Vector3Int(22, 32, 0));
            doors_choice_one_third_enigm.Add(new Vector3Int(23, 32, 0));
            doors_choice_one_third_enigm.Add(new Vector3Int(24, 32, 0));

            doors_choice_two_third_enigm = new List<Vector3Int>();
            doors_choice_two_third_enigm.Add(new Vector3Int(20, 24, 0));
            doors_choice_two_third_enigm.Add(new Vector3Int(20, 25, 0));
            doors_choice_two_third_enigm.Add(new Vector3Int(20, 26, 0));
            doors_choice_two_third_enigm.Add(new Vector3Int(16, 24, 0));
            doors_choice_two_third_enigm.Add(new Vector3Int(16, 25, 0));
            doors_choice_two_third_enigm.Add(new Vector3Int(16, 26, 0));
            
            for(int i = 0; i < doors_choice_two_third_enigm.Count; i++)
            {
                // Récupére les Tiles des portes selon leur position dans une deuxième liste (Permet de bloquer le joueur pour le mauvais choix).
                third_bad_door_to_lock.Add(locked_doors_tilemap.GetTile<Tile>(doors_choice_two_third_enigm[i]));
            }

            doors_fourth_enigm = new List<Vector3Int>();
            doors_fourth_enigm.Add(new Vector3Int(38, 22, 0));
            doors_fourth_enigm.Add(new Vector3Int(38, 23, 0));
            doors_fourth_enigm.Add(new Vector3Int(38, 24, 0));
            doors_fourth_enigm.Add(new Vector3Int(42, 22, 0));
            doors_fourth_enigm.Add(new Vector3Int(42, 23, 0));
            doors_fourth_enigm.Add(new Vector3Int(42, 24, 0));
        }

        private void Start()
        {
            third_bad_door_to_lock = new List<Tile>();
            SetDoorsPositions();
        }
        public void Update()
        {
            // Effet "interupteur" si les boutons (énigme des "leviers").
            // Si les trois leviers sont à True (donc activés) et que l'énigme n'est pas trouvée
            
            if (buttons_state[0] && buttons_state[1] && buttons_state[2] && !button_enigm_finded)
            {
                Debug.Log("All Activated");
                // Alors check le code.
                if (button_playerCode == button_goodCode) // Chaque "levier" a un chiffre il faut trouver "753".
                {
                    UnlockFirstDoor();
                }
                else
                    ButtonReinitialisation();
            }

            // Même logique que les "levier" mais pour les colonnes.
            if (cols_state[0] && cols_state[1] && cols_state[2] &&
                cols_state[3] && !cols_enigm_finded)
            {
                Debug.Log("All Activated");
                if (cols_playercode == cols_goodcode)
                {
                    UnlockFourthDoor();
                }
                else
                    ColsReinitialisation();
            }
        }

        /// <summary>
        /// Active un bouton (un "levier").
        /// </summary>
        /// <param name="position">Position du levier</param>
        /// <param name="buttonID">ID du levier</param>
        public void ActivateButton (Vector3Int position, int buttonID)
        {
            // Si le bouton n'est pas activé.
            if(!buttons_state[buttonID])
            {
                // Remplace le Tile "désactivé" par le Tile "activé".
                SetTile(position, buttonsOn[buttonID], enigm_button_tilemap);
                buttons_state[buttonID] = true;
                // Concatène le code du bouton à celui du joueur.
                button_playerCode += buttonsOn[buttonID].ButtonCode.ToString();
            }
        }

        /// <summary>
        /// Active une colonne.
        /// </summary>
        /// <param name="position">Position de la colonne</param>
        /// <param name="colId">ID de la colonne</param>
        public void ActivateCol (Vector3Int position, int colId)
        {
            // Même logique que pour les "pilier" au dessus.
            if (!cols_state[colId])
            {
                SetTile(position, cols_on[colId], cols_enigm_tilemap);
                cols_state[colId] = true;
                cols_playercode += cols_on[colId].ButtonCode.ToString();
            }
        }

        /// <summary>
        /// Réinitialise les boutons et envoi un message en jeu.
        /// </summary>
        private void ButtonReinitialisation ()
        {
            button_playerCode = string.Empty;

            for (int i = 0; i < 3; i++)
            {
                buttons_state[i] = false;
                SetTile(buttonsOff[i].position, buttonsOff[i], enigm_button_tilemap);
            }
            UIManager.Instance.SendEventMessage("MiageMan", "Des cliquetis se font entendre...Le mécanisme semble s'être réinitialisé.");
        }

        /// <summary>
        /// Réinitialise les colonnes et envoi un message en jeu.
        /// </summary>
        private void ColsReinitialisation()
        {

            cols_playercode = string.Empty;

            for (int i = 0; i < 4; i++)
            {
                cols_state[i] = false;
                SetTile(cols_off[i].position, cols_off[i], cols_enigm_tilemap);
            }
            UIManager.Instance.SendEventMessage("MiageMan", "Des cliquetis se font entendre...Le mécanisme semble s'être réinitialisé.");
        }


        private void UnlockFirstDoor ()
        {
            button_enigm_finded = true;
            EraseTiles(doors_first_enigm, ref locked_doors_tilemap);
            UIManager.Instance.SendEventMessage("MiageMan", "Un bruit sourd s'est fait entendre...On dirait que quelque chose s'est déverrouillée.");
        }

        public void UnlockSecondDoor ()
        {
            olivettom_enigm_01_finded = true;
            EraseTiles(doors_second_enigm, ref locked_doors_tilemap);
        }

        public void UnlockThirdDoor (int choice)
        {
            olivettom_enigm_02_finded = true;
            if(choice == 1)
                EraseTiles(doors_choice_one_third_enigm, ref locked_doors_tilemap);
            else
                EraseTiles(doors_choice_two_third_enigm, ref locked_doors_tilemap);
        }

        /// <summary>
        /// Fait réapparaitre les portes derrière le joueur pour qu'il soit bloqué si mauvais choix 3e enigme.
        /// </summary>
        public void LockBadThirdDoor ()
        {
            for(int i = 0; i < 3; i++)
            {
                SetTileWithRotation(doors_choice_two_third_enigm[i], 90, third_bad_door_to_lock[i], locked_doors_tilemap);
            }

            for(int j = 3; j < 6; j++)
            {
                SetTileWithRotation(doors_choice_two_third_enigm[j], 270, third_bad_door_to_lock[j], locked_doors_tilemap);
            }
        }

        private void UnlockFourthDoor ()
        {
            cols_enigm_finded = true;
            EraseTiles(doors_fourth_enigm, ref locked_doors_tilemap);
            UIManager.Instance.SendEventMessage("MiageMan", "Un bruit sourd s'est fait entendre...On dirait que quelque chose s'est déverrouillée.");
        }

        /// <summary>
        /// Affiche la Tile à la position et sur la tilemap spécifié en paramètre.
        /// </summary>
        /// <param name="position">Position</param>
        /// <param name="tile">La tile à afficher</param>
        /// <param name="tilemap">La tilemap</param>
        private void SetTile (Vector3Int position, TileBase tile, Tilemap tilemap)
        {
            tilemap.SetTile(position, tile);
        }

        /// <summary>
        /// Affiche la tile à la position et sur la tilemap spécifié en paramètre avec une rotation.
        /// </summary>
        /// <param name="position">Position</param>
        /// <param name="zRotation">Rotation voulu</param>
        /// <param name="tile">Tile à afficher</param>
        /// <param name="tilemap">Tilemap</param>
        private void SetTileWithRotation (Vector3Int position, float zRotation, TileBase tile, Tilemap tilemap)
        {
            tilemap.SetTile(position, tile);
            Matrix4x4 matrix = Matrix4x4.TRS(Vector3.zero, Quaternion.Euler(0, 0, zRotation), Vector3.one);
            tilemap.SetTransformMatrix(position, matrix);
        }

        /// <summary>
        /// Ouvre le coffre à la position spécifié en paramètre.
        /// </summary>
        /// <param name="position">Position</param>
        /// <param name="chestId">Chest ID</param>
        public void OpenChest (Vector3Int position, int chestId)
        {
            SetTile(position, chestsOpen[chestId], chests_tilemap);
            SetTile(chestsOpen[chestId].topPosition, topchest_tile, chests_tilemap);
        }

        /// <summary>
        /// Efface les Tiles contenu dans la liste et sur la Tilemap spécifié en paramètre.
        /// </summary>
        /// <param name="vectors">Liste de tiles</param>
        /// <param name="tilemap">Tilemap</param>
        private void EraseTiles(List<Vector3Int> vectors, ref Tilemap tilemap)
        {
            foreach (Vector3Int pos in vectors)
            {
                tilemap.SetTile(pos, null);
            }

        }
    }
}

