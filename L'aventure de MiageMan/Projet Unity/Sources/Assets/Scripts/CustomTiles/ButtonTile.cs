//-------------------------------------------------------
// MiageMan PROJECT
// ButtonTile.cs SCRIPT
//
// Written at 24/12/2018 15:31
// Written by DinoDev - tacktylappzservices@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.Tilemaps;

namespace Scripts.CustomTiles
{
	public class ButtonTile : Tile 
	{

        public int buttonCode;
        public bool state = false;
        public Vector3Int position;
        private ITilemap tilemap;
        public int id;

        public override void GetTileData(Vector3Int position, ITilemap tilemap, ref TileData tileData)
        {
            base.GetTileData(position, tilemap, ref tileData);
            this.position = position;
            this.tilemap = tilemap;
        }

        private void ChangeState (bool state)
        {
            this.state = state;
        }


        public int ButtonCode
        {
            get { return buttonCode; }
        }

        public bool State
        {
            get { return state; }
            set { ChangeState(value); }
        }



#if UNITY_EDITOR
        [MenuItem("Assets/Create/Button Tile")]
        public static void CreateAnimatedTile()
        {
            string path = EditorUtility.SaveFilePanelInProject("Save Button Tile", "New Button Tile", "asset", "Save Button Tile", "Assets");
            if (path == "")
                return;

            AssetDatabase.CreateAsset(ScriptableObject.CreateInstance<ButtonTile>(), path);
        }
#endif
    }
}

