//-------------------------------------------------------
// MiageMan PROJECT
// TriggerTile.cs SCRIPT
//
// Written at 31/12/2018 17:01
// Written by DinoDev - tacktylappzservices@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using UnityEditor;
using UnityEngine;
using UnityEngine.Tilemaps;

namespace Scripts.CustomTiles
{
	public class Trigger : MonoBehaviour 
	{
        public bool state;
        public int id;
        public string message;

        public void Start()
        {
            state = false;
        }
        private void ChangeState(bool state)
        {
            this.state = state;
        }
        public int ID
        { get { return id; } }

        public bool State
        {
            get { return state; }
            set { ChangeState(value); }
        }
    }
}

