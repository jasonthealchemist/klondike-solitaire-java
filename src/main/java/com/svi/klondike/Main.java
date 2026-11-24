package com.svi.klondike;

import com.svi.klondike.app.KlondikeApp;
import com.svi.klondike.config.Settings;
import com.svi.klondike.console.GameSetup;

import java.util.Scanner;

public class Main {

       public static void main(String[] args) {
              System.exit(new KlondikeApp(Settings::defaults, System.in, System.out, System.err).run());
       }
}
