package com.example.guitart.blackjack;

import android.app.ActionBar;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    Deck deck;
    int dealer=0;
    int dealerAce=0;
    Bitmap dealerUnknown;
    int player=0;
    int playerAce=0;
    ArrayList<ImageView> playerExtraCards = new ArrayList();
    ArrayList<ImageView> dealerExtraCards = new ArrayList();
    float cardsMargin;
    boolean reStart = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageView player2 = (ImageView)findViewById(R.id.player2);
        deck = new Deck(true);
        cardsMargin = ((RelativeLayout.LayoutParams)player2.getLayoutParams()).leftMargin;
    }
    public void startGame(View view){
        dealer = 0;
        player = 0;
        dealerAce = 0;
        playerAce = 0;
        RelativeLayout playerLayout = (RelativeLayout)findViewById(R.id.playerRelativeLayout);
        RelativeLayout dealerLayout = (RelativeLayout)findViewById(R.id.dealerRelativeLayout);
        reStart = false;

        for(ImageView temp:playerExtraCards){
            playerLayout.removeView(temp);
        }
        for(ImageView temp:dealerExtraCards){
            dealerLayout.removeView(temp);
        }
        playerExtraCards.clear();
        dealerExtraCards.clear();

        ImageView dealer1 = (ImageView)findViewById(R.id.dealer1);
        ImageView dealer2 = (ImageView)findViewById(R.id.dealer2);
        ImageView player1 = (ImageView)findViewById(R.id.player1);
        ImageView player2 = (ImageView)findViewById(R.id.player2);

        //Known dealer's Card
        Card tempCard = deck.takeCard();
        if(tempCard.getValue() > 9) {
            dealer += 10;
        }else if(tempCard.getValue() == 1) {
            dealerAce++;
        }else {
            dealer += tempCard.getValue();
        }
        int resID = getResources().getIdentifier(tempCard.getName(), "drawable", getPackageName());
        Bitmap tempImg = BitmapFactory.decodeResource(getResources(),resID);
        dealer1.setImageBitmap(tempImg);

        //Unknown dealer's Card
        dealer2.setImageBitmap(null);
        tempCard = deck.takeCard();
        if(tempCard.getValue() > 9) {
            dealer += 10;
        }else if(tempCard.getValue() == 1) {
            dealerAce++;
        }else {
            dealer += tempCard.getValue();
        }
        resID = getResources().getIdentifier(tempCard.getName(), "drawable", getPackageName());
        dealerUnknown = BitmapFactory.decodeResource(getResources(),resID);

        //player 1st card
        tempCard = deck.takeCard();
        if(tempCard.getValue() > 9) {
            player += 10;
        }else if(tempCard.getValue() == 1) {
            playerAce++;
        }else {
            player += tempCard.getValue();
        }
        resID = getResources().getIdentifier(tempCard.getName(), "drawable", getPackageName());
        tempImg = BitmapFactory.decodeResource(getResources(), resID);
        player1.setImageBitmap(tempImg);

        //player 2nd card
        tempCard = deck.takeCard();
        if(tempCard.getValue() > 9) {
            player += 10;
        }else if(tempCard.getValue() == 1) {
            playerAce++;
        }else {
            player += tempCard.getValue();
        }
        resID = getResources().getIdentifier(tempCard.getName(), "drawable", getPackageName());
        tempImg = BitmapFactory.decodeResource(getResources(), resID);
        player2.setImageBitmap(tempImg);


    }
    public void stay(View view){
        if(player+playerAce == 0 || dealer+dealerAce == 0 || reStart){
            Toast.makeText(getApplicationContext(), "Press the Start button to play",
                    Toast.LENGTH_SHORT).show();
        }else {
            RelativeLayout dealerLayout = (RelativeLayout) findViewById(R.id.dealerRelativeLayout);

            //UNKKNOW DEALER CARD
            ImageView dealerUnknownView = (ImageView) findViewById(R.id.dealer2);
            dealerUnknownView.setImageBitmap(dealerUnknown);

            int playerStats = stats(player, playerAce);
            int dealerStats = stats(dealer, dealerAce);

            while (dealerStats <= playerStats && dealerStats < 21) {
                ImageView newCard = new ImageView(this);
                Card tempCard = deck.takeCard();
                if (tempCard.getValue() > 9) {
                    dealer += 10;
                }else if (tempCard.getValue() == 1) {
                        dealerAce++;
                }else{
                    dealer += tempCard.getValue();
                }
                int resID = getResources().getIdentifier(tempCard.getName(), "drawable", getPackageName());
                Bitmap tempImg = BitmapFactory.decodeResource(getResources(), resID);
                newCard.setImageBitmap(tempImg);

                RelativeLayout.LayoutParams margins = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                margins.setMargins((int) (cardsMargin * (dealerExtraCards.size() * 1.2 - 2.7) + 8), 0, 0, 0);
                newCard.setLayoutParams(margins);
                dealerLayout.addView(newCard);
                dealerExtraCards.add(newCard);
                dealerStats = stats(dealer, dealerAce);
            }
            Toast dealer = Toast.makeText(getApplicationContext(), "Dealer WON with " +dealerStats+" points",Toast.LENGTH_LONG);
            Toast player = Toast.makeText(getApplicationContext(), "Player WON with " + playerStats + " points", Toast.LENGTH_LONG);
            if (dealerStats < 22 && playerStats < 22) {
                if (dealerStats > playerStats) {
                    dealer.show();
                } else {
                    player.show();
                }
            } else {
                if (dealerStats < playerStats) {
                    dealer.show();
                } else {
                    player.show();
                }
            }
            reStart = true;
        }
    }
    public void takeCard(View view){
        if(player+playerAce < 21 && player+playerAce>0 && !reStart) {
            RelativeLayout playerLayout = (RelativeLayout) findViewById(R.id.playerRelativeLayout);
            ImageView newCard = new ImageView(this);
            Card tempCard = deck.takeCard();
            if (tempCard.getValue() > 9) {
                player += 10;
            }else if (tempCard.getValue() == 1) {
                playerAce++;
            }else {
                player += tempCard.getValue();
            }
            int resID = getResources().getIdentifier(tempCard.getName(), "drawable", getPackageName());
            Bitmap tempImg = BitmapFactory.decodeResource(getResources(), resID);
            newCard.setImageBitmap(tempImg);

            RelativeLayout.LayoutParams margins = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            margins.setMargins((int) (cardsMargin * (playerExtraCards.size() * 1.2 - 2.7) + 8), 0, 0, 0);
            newCard.setLayoutParams(margins);
            playerLayout.addView(newCard);
            playerExtraCards.add(newCard);
        }else{
            Toast.makeText(getApplicationContext(), "Can't take more cards",
                    Toast.LENGTH_SHORT).show();
            //stay(view);
        }
    }
    public int stats(int cards,int ace){
        int tempStats=cards+ace;
        for(int i = 1; i <= ace; i ++){
            if(cards+i*11+(ace-i) > 21 || tempStats > 21)
                break;
            if(cards+i*11+(ace-i) <= 21 && cards+i*11+(ace-i) > tempStats)
                tempStats = cards+i*11;
        }
        return tempStats;
    }
}
