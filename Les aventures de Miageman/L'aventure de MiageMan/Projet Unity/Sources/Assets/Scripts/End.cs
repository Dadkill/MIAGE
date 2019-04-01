//-------------------------------------------------------
// MiageMan PROJECT
// End.cs SCRIPT
//
// Written at 02/02/2019 23:21
// Written by JordanLacroix - jordan.lacroix.etu@gmail.com
//-------------------------------------------------------

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace Scripts
{
	public class End : MonoBehaviour 
	{
        public GameObject window;
        public Image fadeImage;
        public Text title;
        public Text version;
        public Text creator;
        public Text troll;

        private float progressFade = 0;
        private float progressText = 0;
        private float timer;
        public CharacterMovement playerMove;
        float duration = 15;
        bool fade = false;
        Color colorToReach = Color.black;
        // Méthode générique utilisée pour l'initialisation de la scène
        void Start () 
		{
			
		}
	
		// Update est appelé une fois par frame
		void Update () 
		{
            // Si le script de déplacement du joueur est désactivé. (afin que le personnage s'arrête devant le roi).
            if (!playerMove.isActiveAndEnabled)
            {
                window.SetActive(true);
                timer += Time.deltaTime; // Timer avant le Fade de fin.
                if(timer >= 5)
                {
                    fade = true;
                    if (fade)
                    {
                        StartCoroutine(LerpColorBackground()); // Fade du background.
                        if (timer >= 8)
                        {
                            StartCoroutine(LerpColorText()); // Fade d'apparition du texte.
                        }

                    }
                }

            }

		}

        public void Fade ()
        {


        }

        IEnumerator LerpColorBackground()
        {
            float progress = 0;
            float increment = 0.002f / duration;
            while (progress <= 1)
            {
                fadeImage.color = Color.Lerp(fadeImage.color, new Color(0, 0, 0, 1), progress);
                progress += increment;
                yield return new WaitForSeconds(0.002f);
            }
        }

        IEnumerator LerpColorText()
        {
            float progress = 0;
            float increment = 0.002f / duration; 
            while (progress <= 1)
            {
                title.color = Color.Lerp(title.color, Color.white, progress);
                version.color = Color.Lerp(version.color, Color.white, progress);
                creator.color = Color.Lerp(creator.color, Color.white, progress);
                troll.color = Color.Lerp(troll.color, Color.white, progress);
                progress += increment;
                yield return new WaitForSeconds(0.002f);
            }
        }
    }
}

