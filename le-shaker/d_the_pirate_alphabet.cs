using System;
using System.Collections.Generic;

public class d_the_pirate_alphabet
{
    public static void Main (string[] args)
    {
        char[] pirate = {"a", "e", "i","o", "u","b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z"};
		int[] ff = new int[26];
        Dictionary<char, int>() tab = new Dictionary<char, int>();

        string entree = Console.ReadLine();


        // Initialise le dico avec l'alphabet pirate avec un compteur à 0
        for(int i = 0; i < pirate.Length; i++)
        {
            tab.add(pirate[0], 0);
        }

        // Si on est sur le bon caractère on incrémente.
        foreach(Char char in entree)
        {
            if(tab.KeyExist(char))
            {
                tab[char]++;
            }
        }
        
        // Compte le nombre de caractère toujours à 0
        int count =0;
        foreach(KeyValuePair<char, int> dico in tab)
        {
            if(dico.Value == 0)
                count++;
        }

        Console.WriteLine(count);
    }
}




