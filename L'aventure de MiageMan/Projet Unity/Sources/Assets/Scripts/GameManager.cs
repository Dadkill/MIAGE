//-------------------------------------------------------
// MiageMan PROJECT
// GameManager.cs SCRIPT
//
// Written at 21/02/2019 18:15
// Written by TommyPiacentino - piacentinotommy@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using Scripts.CustomTiles;
using UnityEngine;
using UnityEngine.Tilemaps;
using Cinemachine;
using UnityEngine.SceneManagement;

namespace Scripts
{
	public class GameManager : MonoBehaviour 
	{

        private int scrolls_finded = 0;
        public TilesManager TilesManager;
        public CinemachineBrain brain;
        public CinemachineVirtualCamera vCameraPlayer;
        public CinemachineVirtualCamera vCameraEnnemy;

        public GameObject ennemyBoss;
        public GameObject player;
        public CharacterMovement playerMove;

        private bool end;
        public bool End { set { end = value; } }
        public int ScrollsFinded
        {
            get { return scrolls_finded; }
            set
            {
                if(scrolls_finded < 4)
                {
                    scrolls_finded = value;
                    UIManager.Instance.SetScrollVisible(scrolls_finded - 1);

                    if (scrolls_finded == 4)
                    {
                        UIManager.Instance.SendEventMessage("MiageMan", "J'ai les quatre parchemins ! J'ai réussi !");
                        UIManager.Instance.lerpBackground = StartCoroutine(UIManager.Instance.LerpColorBackground());
                    }
                }
            }
        }

        public static GameManager Instance { get; private set; }
        private void Awake()
        {
            InitSingleton();
        }
        public void PlayerWin ()
        {
            playerMove.enabled = false;
            if(UIManager.Instance.lerpBackground == null)
                SceneManager.LoadScene("Win");
        }

        private void InitSingleton ()
        {
            /*if (Instance != null && Instance != this)
                Destroy(gameObject);*/
            Instance = this;
            //DontDestroyOnLoad(gameObject);
        }
        // Méthode générique utilisée pour l'initialisation
        void Start () 
		{
		}
	   
		// Update est appelé à chaque frame
		void Update () 
		{
            if (scrolls_finded == 4)
                PlayerWin();
        }

        public void GameOver ()
        {
            vCameraPlayer.enabled = false;
            playerMove.StopCharacter();
            playerMove.enabled = false;
            
            StartCoroutine(UIManager.Instance.ShowGameOver());
            UIManager.Instance.ActivateGameOverUI();
        }

        public void Restart ()
        {
            /*scrolls_finded = 0;
            UIManager.Instance.Restart();
            TilesManager.Restart();
            playerMove.enabled = true;
            playerMove.canMove = true;
            player.transform.position = new Vector3(-0.47f, -3.52f, 0);*/
           
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
	}
}

