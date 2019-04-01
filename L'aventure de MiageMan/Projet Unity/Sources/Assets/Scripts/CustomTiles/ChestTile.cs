//-------------------------------------------------------
// MiageMan PROJECT
// ChestTile.cs SCRIPT
//
// Written at 31/12/2018 12:00
// Written by DinoDev - tacktylappzservices@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.Tilemaps;

namespace Scripts.CustomTiles
{
    /// <summary>
    /// Custom Tile contenant le comportement d'un coffre.
    /// </summary>
	public class ChestTile : Tile 
	{
        public int chestId;
        public bool state = false;
        public Vector3Int position;
        public Vector3Int topPosition;

        private ITilemap tilemap;

        public override void GetTileData(Vector3Int position, ITilemap tilemap, ref TileData tileData)
        {
            base.GetTileData(position, tilemap, ref tileData);
            this.tilemap = tilemap;
        }

        private void ChangeState (bool state)
        {
            this.state = state;
        }

        public bool State
        {
            get { return state; }
            set { ChangeState(value); }
        }

        // Créé les menu contextuel dans l'éditeur unity pour créer un ChestTile.
#if UNITY_EDITOR
        [MenuItem("Assets/Create/Chest Tile")]
        public static void CreateAnimatedTile()
        {
            string path = EditorUtility.SaveFilePanelInProject("Save Chest Tile", "New Chest Tile", "asset", "Save Chest Tile", "Assets");
            if (path == "")
                return;

            AssetDatabase.CreateAsset(ScriptableObject.CreateInstance<ChestTile>(), path);
        }
#endif
    }
}

