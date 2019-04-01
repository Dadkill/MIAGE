//-------------------------------------------------------
// MiageMan PROJECT
// CharacterMovement.cs SCRIPT
//
// Written at 27/01/2019 22:10
// Written by JordanLacroix - jordan.lacroix.etu@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using Scripts.CustomTiles;
using UnityEngine;
using UnityEngine.Tilemaps;

namespace Scripts
{
	public class CharacterMovement : MonoBehaviour 
	{
        public int speed = 5;
        public Animator animator;
        public GameObject obj;
        public State currentState;
        public bool canMove = true;
        public bool isScripted;

        private float timer = 2f;
        private float currentTimer;
        private GameObject king;

        private float startTime;
        private Vector3 end;
        private Transform _t;
        /// <summary>
        /// État de l'orientation du joueur selon la touche sur laquelle le joueur à appuyé en dernier.
        /// </summary>
        public enum State
        {
            Up, // Z
            Down, // S
            Left, // Q
            Right // D
        }
		// Use this for initialization
		void Start () 
		{
            _t = this.transform;
            end = new Vector3(-18.5f, -0.83f, 0);
        }
	
		// Update is called once per frame
		void Update () 
		{
            if(isScripted) // Booléen permet de savoir si le joueur est scripté (sert à la fin du jeu lorsqu'il avance tout seul).
            {
                _t.position = Vector3.MoveTowards(_t.position, new Vector3(-18.5f, -0.83f, 0), Time.deltaTime);
                Anim_Move(true);
                if (_t.position == end)
                {
                    isScripted = false;
                    Anim_Move(false);
                    this.enabled = false;
                }

            }
		}

        private void FixedUpdate()
        {
            GetInput();
        }

        /// <summary>
        /// Gère les touches claviers
        /// </summary>
        public void GetInput ()
        {
            if(canMove)
            {
                if (Input.GetKey(KeyCode.Z))
                {
                    Anim_Move(true);
                    Move(Vector2.up);
                    currentState = State.Up;
                }
                else if (Input.GetKey(KeyCode.S))
                {
                    Anim_Move(true);
                    Move(Vector2.down);
                    currentState = State.Down;
                }
                else if (Input.GetKey(KeyCode.Q))
                {
                    Anim_Move(true);
                    Move(Vector2.left);
                    transform.localScale = new Vector3(-0.18f, 0.18f, 1);
                    currentState = State.Left;
                }
                else if (Input.GetKey(KeyCode.D))
                {
                    Anim_Move(true);
                    Move(Vector2.right);
                    transform.localScale = new Vector3(0.18f, 0.18f, 1);
                    currentState = State.Right;
                }
                else if (Input.GetKeyDown(KeyCode.E))
                {
                    Use();
                }
                else
                {
                    Anim_Move(false);
                }
            }

            // Sert lorsque le joueur est bloqué. (Sorte d'animation qui bloque le joueur lorsque la porte se referme derrière lui).
            if(!canMove && !isScripted)
            {
                currentTimer += Time.deltaTime;
                if(currentTimer >= timer)
                {
                    canMove = true;
                    currentTimer = 0;
                }
            }

        }

        /// <summary>
        /// Gère la fonction 'utilisé' du joueur (touche E).
        /// </summary>
        private void Use ()
        {
            //GameManager.Instance.ScrollsFinded++;
            // Envoi un rayon à partir de la position du joueur vers la direction dans laquelle il regarde.
            RaycastHit2D hit = Physics2D.Raycast(transform.position, GetDirection());

            // Si le rayon touche quelque chose.
            if (hit.collider != null)
            {
                // Récupère la position de la collsion.
                Vector2 hitPoint = hit.point;
                hitPoint.x = Mathf.FloorToInt(hitPoint.x);
                hitPoint.y = Mathf.FloorToInt(hitPoint.y);


                //Debug.Log("Hit Point : " + hitPoint + " raycast hit : " + hit.point);
                //Debug.DrawLine(transform.position, hit.point, Color.red);

                // Si la distance entre le joueur et l'objet touché est <= à 1.5 alors il peut "l'utiliser".
                if (Vector2.Distance(transform.position, hit.point) <= 1.5f)
                {
                    //Debug.Log("Close");
                    // Récupère le Vecteur avec des valeurs entière.
                    Vector3Int pos = new Vector3Int((int)hitPoint.x, (int)hitPoint.y, 0);
                    //Debug.DrawLine(transform.position, hit.point, Color.red);

                    // Check le tag de l'objet touché par le rayon.
                    switch (hit.collider.tag)
                    {
                        case "Enigme_01":
                            ButtonTile btnTile = GameManager.Instance.TilesManager.enigm_button_tilemap.GetTile<ButtonTile>(pos);
                            if (btnTile != null)
                                GameManager.Instance.TilesManager.ActivateButton(pos, btnTile.id);
                            break;
                        case "Enigme_02":
                            ButtonTile colTile = GameManager.Instance.TilesManager.cols_enigm_tilemap.GetTile<ButtonTile>(pos);
                            if (colTile != null)
                                GameManager.Instance.TilesManager.ActivateCol(pos, colTile.id);
                            break;
                        case "Chests":
                            ChestTile tile = GameManager.Instance.TilesManager.chests_tilemap.GetTile<ChestTile>(pos);
                            if (tile != null)
                            {
                                if(tile.state == false) // Si le coffre n'est pas ouvert.
                                {
                                    GameManager.Instance.TilesManager.OpenChest(pos, tile.chestId);
                                    GameManager.Instance.ScrollsFinded++;
                                }
                                else
                                {
                                    UIManager.Instance.SendEventMessage("MiageMan", "Le coffre est déjà ouvert...");
                                }

                            }
                            break;
                        case "Locked_Doors":
                            UIManager.Instance.SendEventMessage("MiageMan", "La porte semble verrouillée...");
                            break;
                        case "Mage01":
                            if(!GameManager.Instance.TilesManager.Olivettom_Enigm_01_Finded)
                                UIManager.Instance.SendEventMessageChoice("Olivettom", "Buongiorno MiageMan. Voici l'énigme permettant de déverrouiller la porte :\n Quelle est la compléxité, dans le meilleur cas, d'un tri rapide ? ", "n.log(n)", "n²", 1);
                            else
                                UIManager.Instance.SendEventMessage("Olivettom", "Bravo MiageMan. Tu as trouvé l'énigme !");
                            break;
                        case "Mage02":
                            if (!GameManager.Instance.TilesManager.Olivettom_Enigm_02_Finded)
                                UIManager.Instance.SendEventMessageChoice("Olivettom", "Deux portes se trouvent à mes côtés. L'une mène à la richesse et à la popularité et l'autre mène aux savoirs et aux compétences.\n Quelle porte choisis-tu ? ", "La Savoir !", "La Richesse !", 2);
                            else
                                UIManager.Instance.SendEventMessage("Olivettom", "La porte que tu as choisis s'est ouverte !");
                            break;
                    }


                }
                //Debug.DrawLine(transform.position, hit.point, Color.green);
            }
        }

        /// <summary>
        /// Permet de détecter lorsque le joueur entre dans un objet "trigger"
        /// </summary>
        /// <param name="collision"></param>
        public void OnTriggerEnter2D(Collider2D collision)
        {
            Trigger trigger = collision.GetComponent<Trigger>();

            if(trigger != null)
            {
                if(!trigger.State)
                {
                    if(trigger.ID == 1) // Trigger.id 1 mauvais choix de porte.
                    {
                        StopCharacter();
                        trigger.State = true;
                        GameManager.Instance.TilesManager.LockBadThirdDoor();
                        SetRight();
                        UIManager.Instance.SendEventMessage("MiageMan", "La porte s'est refermée derrière moi ! Je suis bloqué !");
                    }
                    else if(trigger.ID == 2) // Trigger 2 message de Teros
                    {
                        GameManager.Instance.GameOver();
                        UIManager.Instance.SendEventMessage("Teros", trigger.message);
                    }
                    else // Sinon message simple (description de la salle).
                    {
                        trigger.State = true;
                        UIManager.Instance.SendEventMessage("MiageMan", trigger.message);
                    }
                }
            }
        }

        public void StopCharacter ()
        {
            canMove = false;
            Anim_Move(false);
        }
        private Vector2 GetDirection ()
        {
            switch(currentState)
            {
                case State.Up: return Vector2.up;
                case State.Down: return Vector2.down;
                case State.Left: return Vector2.left;
                case State.Right: return Vector2.right;
            }
            return Vector2.zero;
        }

        private void SetRight ()
        {
            if(currentState != State.Right)
            {
                currentState = State.Right;
                transform.localScale = new Vector3(0.18f, 0.18f, 1);
            }
        }

        private void Anim_Move (bool state)
        {
            animator.SetBool("walk", state);
        }

        // Déplace le joueur selon un vecteur normalisé.
        private void Move (Vector2 dir)
        {
            transform.Translate(dir.normalized * speed * Time.deltaTime);
        }

    }
}

