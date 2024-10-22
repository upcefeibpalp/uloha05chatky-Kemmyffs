package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {
	static final int MAX_VELIKOST_CHATKY = 10;
	static final int VELIKOST_KEMPU = 5;
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Hlavni metoda zajistujici cyklus ziskavani operace od uzivatele a zavolani potrebnych metod
	 * pro dane operace
	 * @param args
	 */
    public static void main(String[] args) {
        // Konstanty pro definovani jednotlivych operaci (pouze pro cisty kod)
        final int KONEC_PROGRAMU = 0;
        final int VYPIS_CHATEK = 1;
        final int VYPIS_KONKRETNI_CHATKU = 2;
        final int PRIDANI_NAVSTEVNIKU = 3;
        final int ODEBRANI_NAVSTEVNIKU = 4;
        final int CELKOVA_OBSAZENOST = 5;
        final int VYPIS_PRAZDNE_CHATKY = 6;
        


        // Definovani pole podle velikosti kempu (poctu chatek)
        int[] chatky = new int[VELIKOST_KEMPU];
        int operace;

        do {
            System.out.println("""
                    MENU:
                                        
                    1 - vypsani vsech chatek
                    2 - vypsani konkretni chatky
                    3 - Pridani navstevniku
                    4 - Odebrani navstevniku
                    5 - Celkova obsazenost kempu
                    6 - Vypis prazdnych chatek
                    0 - Konec programu
                    """);

            // Ziskani operace od uzivatele
            System.out.print("Zadej volbu: ");
            operace = sc.nextInt();

            switch (operace) {
                case VYPIS_CHATEK -> {
                	vypisChatek(chatky);
                }

                case VYPIS_KONKRETNI_CHATKU -> {
                	vypisJedneChatky(chatky);
                }

                case PRIDANI_NAVSTEVNIKU -> {
                	if(!pridatNavstevniky(chatky)) {
                		continue;
                	}
                }

                case ODEBRANI_NAVSTEVNIKU -> {
                    if(!odebratNavstevniky(chatky)) {
                    	continue;
                    }
                }

                case CELKOVA_OBSAZENOST -> {
                    celkovaObsazenost(chatky);
                }

                case VYPIS_PRAZDNE_CHATKY -> {
                    prazdneChatky(chatky);
                }

                case KONEC_PROGRAMU -> {
                    System.out.println("Konec programu");
                }

                default -> {
                    System.err.println("Neplatna volba");
                }
            }
        } while (operace != 0);
    }
    
    public static int ziskaniCisla(int[] chatky) {
    	// Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");
        // Odecteni 1 protoze uzivatel cisluje chatky o 1, ale program od 0
        int cisloChatky = sc.nextInt() - 1;
        // Zaporne nebo cislo vetsi nez je pocet chatek je nevalidni vstup
        if (cisloChatky < 0 || cisloChatky >= chatky.length) {
            System.err.println("Tato chatka neexistuje");
            return -1;
        }
        return cisloChatky;
    }
    
    /**
     * Vypis vsech chatek.
     * Vypise cislo chatky a pocet jejich navstevniku.
     * @param chatky pole chatek, pro ktere chceme metodu pouzit.
     */
    public static void vypisChatek(int[] chatky) {
    	// Projdi cele pole od <0, VELIKOST) a vypise kazdy index
        for (int i = 0; i < chatky.length; i++) {
        	System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
        }
    }
    
    /**
     * Metoda pro vypsani jedne konkretni chatky.
     * Nelze vypsat neexistujici chatku
     * @param chatky pole chatek, pro ktere chceme metodu pouzit.
     * @return vraci boolean podle toho, jestli byly vsechny inputy validni a metoda se provedla spravne.
     */
    public static boolean vypisJedneChatky(int[] chatky) {
    	if(ziskaniCisla(chatky) == -1) {
    		return false;
    	}
    	int cisloChatky = ziskaniCisla(chatky);
        System.out.println("Chatka [" + (cisloChatky + 1) + "] = " + chatky[cisloChatky]);
        return true;
    }
    
    /**
     * Metoda na pridani navstevniku do jedne konkretni chatky.
     * Nelze pridat mene nez jednoho navstevnika.
     * Nelze pridat vice nez maximalni pocet navstevniku.
     * Nelze pridat navstevniky, pokud by jejich celkovy pocet po provedeni metody byl vyssi nez maximalni pocet navstevniku.
     * @param chatky pole chatek, pro ktere chceme metodu pouzit.
     * @return vraci boolean podle toho, jestli byly vsechny inputy validni a metoda se provedla spravne.
     */
    public static boolean pridatNavstevniky(int[] chatky) {
    	if(ziskaniCisla(chatky) == -1) {
    		return false;
    	}
    	int cisloChatky = ziskaniCisla(chatky);

        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = sc.nextInt();

        // Zaporne cislo nebo prilis velky nevalidni vstup
        if (pocetNavstevniku <= 0 || pocetNavstevniku > MAX_VELIKOST_CHATKY) {
            System.err.println("Neplatna hodnota pro pocet navstevniku");
            return false; // Zacni novou iteraci cyklu
        }

        // Pokud je pocet uz ubytovanych plus ty co se chteji ubytovat vetsi nez kapacita chatky je to nevalidni vstup
        if ((chatky[cisloChatky] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {
            System.err.println("Prekrocen maximalni pocet navstevniku chatky");
            return false; // Zacni novou iteraci cyklu
        }

        // Pridej nove ubytovane do chatky k tem co uz tam jsou
        chatky[cisloChatky] = pocetNavstevniku + chatky[cisloChatky];
        
        return true;
    }
    
    /**
     * Metoda na odebrani navstevniku z jedne konkretni chatky
     * Nelze:
     * <ul>
     * <li>Odebrat z neexistujici chatky</li>
     * <li>Odebrat mene nez jednoho navstevnika</li>
     * <li>Odebrat vice nez aktualni pocet navstevniku</li>
     * </ul>
     * @param chatky pole chatek, pro ktere chceme metodu pouzit.
     * @return vraci boolean podle toho, jestli byly vsechny inputy validni a metoda se provedla spravne.
     */
    public static boolean odebratNavstevniky(int[] chatky) {
    	if(ziskaniCisla(chatky) == -1) {
    		return false;
    	}
    	int cisloChatky = ziskaniCisla(chatky);
    	
    	// Ziskani poctu navstevniku, ktere chceme odebrat
        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = sc.nextInt();

        // Nevalidni vstup
        if (pocetNavstevniku <= 0) {
            System.err.println("Nelze odebrat mene nez jednoho navstevnika");
            return false; // Zacni novou iteraci cyklu
        } else if(pocetNavstevniku > chatky[cisloChatky]) {
        	System.err.println("Nelze odebrat vice navstevniku nez je aktualni pocet navstevniku");
        	return false;
        }
        chatky[cisloChatky] -= pocetNavstevniku;
        return true;
    }
    
    
    /**
     * Metoda vypisujici soucet navstevniku ve vsech chatkach.
     * @param chatky pole chatek, pro ktere chceme metodu pouzit.
     */
    public static void celkovaObsazenost(int[] chatky) {
    	int pocet = 0;
    	for(int i : chatky) {
    		pocet += i;
    	}
    	System.out.println("Celkovy pocet ubytovanych lidi je: " + pocet);
    }
    
    /**
     * Metoda vypisujici vsechny chatky s nulovym pocetm navstevniku.
     * @param chatky pole chatek, pro ktere chceme metodu pouzit.
     */
    public static void prazdneChatky(int[] chatky) {
    	boolean isEmpty = true;
    	for (int i = 0; i < chatky.length; i++) {
    		if(chatky[i] == 0) {
    			System.out.println("Chatka [" + (i + 1) + "] = " + chatky[i]);
    			isEmpty = false;
    		}
		}
    	if(isEmpty) {
    		System.out.println("Zadna chatka neni prazdna");
    	}
    }
}

