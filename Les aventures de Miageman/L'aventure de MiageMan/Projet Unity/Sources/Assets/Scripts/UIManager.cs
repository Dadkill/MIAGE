//-------------------------------------------------------
// MiageMan PROJECT
// UIManager.cs SCRIPT
//
// Written at 21/02/2019 19:53
// Written by JordanLacroix - jordan.lacroix.etu@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace Scripts
{
	public class UIManager : MonoBehaviour 
	{
        public List<RawImage> scrolls;
        public GameObject textWindows;
        public Text speaker;
        public Text message;

        public Button choice_1;
        public Text choice_1_text;
        public Button choice_2;
        public Text choice_2_text;

        private float timer = 10f;
        private float currentTime = 0;
        private bool messageSended = false;
        private bool messageChoiceSended = false;

        public Image gameOver_Background;
        public GameObject gameOver_text;
        public GameObject gameOver_button;

        //      private Dictionary<int, List<string>> eventMessages;
        public static UIManager Instance { get; private set; }
        private void Awake()
        {
            InitSingleton();
        }

        private void InitSingleton()
        {
            /*if (Instance != null && Instance != this)
                Destroy(gameObject);*/
            Instance = this;
            //DontDestroyOnLoad(gameObject);
        }

        public void Restart ()
        {
            messageSended = false;
            messageChoiceSended = false;
            ReinitializeChoice();
            ReinitializeWindows();
            ReinitScroll();
            currentTime = 0;
        }

        // Use this for initialization
        void Start () 
		{
  //          eventMessages = new Dictionary<int, List<string>>();
		}
	
		// Update is called once per frame
		void Update () 
		{
            // Logique lorsqu'un message est envoyé.
            if(messageSended)
            {
                // Fais tourner un timer.
                currentTime += Time.deltaTime;
                if(currentTime >= timer)
                {
                    // Lorsque le timer est terminé, réinitialise le timer et cache la boite de message (UI).
                    messageSended = false;
                    currentTime = 0;
                    timer = 10; // Attribut le temps par defaut dans le cas ou le timer est modifié.
                    ReinitializeWindows();
                }
            }
		}

        /// <summary>
        /// Affiche le parchemin via son id passé en paramètre.
        /// </summary>
        /// <param name="scrollnum"></param>
        public void SetScrollVisible (int scrollnum)
        {
            scrolls[scrollnum].color = new Color(255, 255, 255, 255);
            SendEventMessage("MiageMan", "J'ai récupéré le parchemin " + (scrollnum + 1).ToString() + ".");
        }

        /// <summary>
        /// Réinitialisation des parchemins (Alpha à 0, invisible).
        /// </summary>
        private void ReinitScroll ()
        {
            for(int i = 0; i < scrolls.Count; i++)
            {
                scrolls[i].color = new Color(255, 255, 255, 0);
            }
        }

        /// <summary>
        /// Envoi un simple message.
        /// </summary>
        /// <param name="speaker">Celui qui parle</param>
        /// <param name="message">Le message</param>
        public void SendEventMessage (string speaker, string message)
        {
            messageSended = true;
            currentTime = 0; // Réinit le timer pour chaque nouveau message
            ActivateWindows(speaker, message);
        }

        /// <summary>
        /// Envoi un message avec un temps déterminé.
        /// </summary>
        /// <param name="speaker">Celui qui parle</param>
        /// <param name="message">Le message</param>
        /// <param name="duration">La durée</param>
        public void SendEventMessage (string speaker, string message, float duration)
        {
            timer = duration;
            SendEventMessage(speaker, message);
        }

        /// <summary>
        /// Envoi un message à choix (2 choix).
        /// </summary>
        /// <param name="speaker">Celui qui parle</param>
        /// <param name="message">Le message</param>
        /// <param name="choice_1">Choix 1</param>
        /// <param name="choice_2">Choix 2</param>
        /// <param name="enigme_id">ID de l'énigme à choix.</param>
        public void SendEventMessageChoice (string speaker, string message, string choice_1, string choice_2, int enigme_id)
        {
            messageChoiceSended = true;
            currentTime = 0;
            ActivateWindows(speaker, message);
            ActivateChoice(choice_1, choice_2, enigme_id);
        }

        /// <summary>
        /// Envoi un message à choix (2 choix) avec durée.
        /// </summary>
        /// <param name="speaker">Celui qui parle</param>
        /// <param name="message">Le message</param>
        /// <param name="choice_1">Choix 1</param>
        /// <param name="choice_2">Choix 2</param>
        /// <param name="duration">Durée</param>
        /// <param name="enigme_id">ID de l'énigme à choix</param>
        public void SendEventMessageChoice (string speaker, string message, string choice_1, string choice_2, float duration, int enigme_id)
        {
            timer = duration;
            SendEventMessageChoice(speaker, message, choice_1, choice_2, enigme_id);
        }
        
        /// <summary>
        /// Affiche la fenêtre contenant le message en jeu.
        /// </summary>
        /// <param name="speaker">Lui qui parle</param>
        /// <param name="message">Le message</param>
        private void ActivateWindows (string speaker, string message)
        {
            this.speaker.text = speaker;
            this.message.text = message;
            textWindows.SetActive(true);
        }

        /// <summary>
        /// Affiche les choix en jeu.
        /// </summary>
        /// <param name="choice_1">Choix 1</param>
        /// <param name="choice_2">Choix 2</param>
        /// <param name="enigme_id">ID de l'enigme</param>
        private void ActivateChoice (string choice_1, string choice_2, int enigme_id)
        {
            choice_1_text.text = choice_1;
            choice_2_text.text = choice_2;
            switch(enigme_id)
            {
                case 1:
                    this.choice_1.onClick.AddListener(() => 
                    {
                        GameManager.Instance.TilesManager.UnlockSecondDoor();
                        ReinitializeChoice();
                        SendEventMessage("MiageMan", "Un bruit sourd s'est fait entendre...La porte à coté d'Olivettom s'est ouverte.");
                    });

                    this.choice_2.onClick.AddListener(() =>
                    {
                        ReinitializeChoice();
                        SendEventMessage("MiageMan", "Rien ne se passe...La porte est toujours verrouillée.");
                    });
                    break;
                case 2:
                    this.choice_1.onClick.AddListener(() =>
                    {
                        GameManager.Instance.TilesManager.UnlockThirdDoor(1);
                        ReinitializeChoice();
                        SendEventMessage("MiageMan", "Un bruit sourd s'est fait entendre...La porte Nord d'Olivettom s'est ouverte.");
                    });

                    this.choice_2.onClick.AddListener(() =>
                    {
                        GameManager.Instance.TilesManager.UnlockThirdDoor(2);
                        ReinitializeChoice();
                        SendEventMessage("MiageMan", "Un bruit sourd s'est fait entendre...La porte Ouest d'Olivettom s'est ouverte.");
                    });
                    break;
            }
            this.choice_1.gameObject.SetActive(true);
            this.choice_2.gameObject.SetActive(true);
        }

        /// <summary>
        /// Réinitialise les choix (Suppression string, désactivation GameObject et Suppression Listener).
        /// </summary>
        private void ReinitializeChoice ()
        {
            choice_1_text.text = string.Empty;
            choice_2_text.text = string.Empty;
            this.choice_1.onClick.RemoveAllListeners();
            this.choice_2.onClick.RemoveAllListeners();
            this.choice_1.gameObject.SetActive(false);
            this.choice_2.gameObject.SetActive(false);
        }

        private IEnumerator DisplayMessage (string speaker, string message)
        {
            /*
            int index = 0;
            cr_running = true;
            while (eventMessages.Count > 0)
            {
                Debug.Log("Count Coroutine : " + eventMessages.Count);
                ActivateWindows(eventMessages[index][0], eventMessages[index][1]);

                yield return new WaitForSeconds(timer);
                eventMessages.Remove(index);
                Debug.Log("Corountine - Key : " + index + " deleted");
                index++;
            }

            ReinitializeWindows();
            cr_running = false;
            StopCoroutine(DisplayMessage());*/
 //           cr_running = true;
            ActivateWindows(speaker, message);
            yield return new WaitForSeconds(timer);
            StopMessageCoroutine(DisplayMessage("", ""));
        }

        private void StopMessageCoroutine (IEnumerator coroutine)
        {
            ReinitializeWindows();
//            cr_running = false;
            StopCoroutine(DisplayMessage("", ""));
        }

        /// <summary>
        /// Fait disparaitre la fenêtre contenant le message.
        /// </summary>
        private void ReinitializeWindows ()
        {
            textWindows.SetActive(false);
            speaker.text = string.Empty;
            message.text = string.Empty;
        }

        /// <summary>
        /// Coroutine permettant l'animation du Fade GameOver.
        /// </summary>
        /// <returns></returns>
        public IEnumerator ShowGameOver ()
        {
            Color colorToReach = gameOver_Background.color;
            colorToReach.a = 0.65f;
            float progress = 0f;
            bool fade = true;
            while(fade)
            {
                progress += 0.005f;
                if (progress >= 1) fade = false;

                gameOver_Background.color = Color.Lerp(gameOver_Background.color, colorToReach, progress);
                yield return new WaitForSeconds(0.2f);
            }
        }

        /// <summary>
        /// Active l'interface du Game Over.
        /// </summary>
        public void ActivateGameOverUI ()
        {
            gameOver_text.SetActive(true);
            gameOver_button.SetActive(true);
        }

        public Coroutine lerpBackground;
        /// <summary>
        /// Coroutine permettant une interpolation linéaire d'une couleur vers une autre.
        /// </summary>
        /// <returns></returns>
        public IEnumerator LerpColorBackground()
        {
            float progress = 0;
            float increment = 0.02f / 5;
            while (progress <= 1)
            {
                gameOver_Background.color = Color.Lerp(new Color(0,0,0,0), new Color(0, 0, 0, 1), progress);
                progress += increment;
                yield return new WaitForSeconds(0.002f);
            }
            lerpBackground = null;
        }
    }
}

