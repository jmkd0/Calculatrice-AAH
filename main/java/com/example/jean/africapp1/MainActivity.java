package com.example.jean.africapp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText edit;
    private TextView result;
    private Animation anim;
    boolean rep=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setSubtitle("Afrik apps home");
        TextView text=(TextView)findViewById(R.id.text);
        anim=(Animation) AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        text.startAnimation(anim);
        edit=(EditText)findViewById(R.id.edit);
        result=(TextView)findViewById(R.id.result);
        result.setText("0");
        Ecouteur();

    }
    public void Ecouteur(){
        TableLayout table=(TableLayout)findViewById(R.id.table);
        for(int i=0; i<table.getChildCount(); i++){
            View rowtab=table.getChildAt(i);
            if(rowtab instanceof TableRow){
                TableRow row=(TableRow)rowtab;
                for(int j=0; j<row.getChildCount(); j++){
                    View buttab=row.getChildAt(j);
                    if(buttab instanceof Button){
                        Button button=(Button)buttab;

                        button.setOnClickListener(new View.OnClickListener(){

                            public void onClick(View v){
                                String buttoncli=((Button)v).getText().toString();
                                if(buttoncli.equals("Asin")) buttoncli="Arcsin(";
                                if(buttoncli.equals("Acos")) buttoncli="Arccos(";
                                if(buttoncli.equals("Atan")) buttoncli="Arctan(";
                                if(buttoncli.equals("Sin")) buttoncli="Sin(";
                                if(buttoncli.equals("Cos")) buttoncli="Cos(";
                                if(buttoncli.equals("Tan")) buttoncli="Tan(";
                                if(buttoncli.equals("Ln")) buttoncli="Ln(";
                                if(buttoncli.equals("Exp")) buttoncli="Exp(";
                                Affichage(buttoncli);
                            }

                        });

                    }

                }
            }
        }

    }

    //Initialisation
    int k=0;
    private String chaine="0";

    String[] effacer=new String[50];
    public  void Affichage(String buttontext){
        //On clique sur autres boutons autre que effacer et egalité
        if(!(buttontext.equalsIgnoreCase("⇦"))&&!(buttontext.equalsIgnoreCase("="))){
            effacer[k]=buttontext;

            edit.append(buttontext);
            chaine=edit.getText().toString();
            k++;
        }
        //On clique sur le bouton effacer
        if(buttontext.equalsIgnoreCase("⇦")){
            result.setText("0");
            rep=true;
            if(k>0){
                k--;
                int l=chaine.length();
                int h=effacer[k].length();
                chaine=chaine.substring(0,l-h);
                edit.setText(chaine);
                if(k==0)	k=0;
                effacer[k]=null;
            }
        }
        //On clique sur AC
        if(buttontext.equalsIgnoreCase("AC")){
            edit.setText("");
            chaine="0";
            result.setText("0");
            rep=true;
            k=0;
        }

        // On clique sur le bouton egale
        if(buttontext.equalsIgnoreCase("=")&&chaine.length()>0){
            String str=chaine;
            int pos=0, ouv=0, fer=0;
            while(pos<str.length()){
                if(str.charAt(pos)=='(') ouv++;
                if(str.charAt(pos)==')') fer++;
                pos++;}
            double a;
            if(ouv<fer){ a=1; rep=false;
            }else a=eval(str);
            if(rep==false){
                result.setText("Erreur");
            }else{

                String s=String.format("%s",a);
                String rec=s.substring(s.length()-2,s.length());
                if(rec.equals(".0")){
                    String st=s.substring(0,s.length()-2);
                    result.setText(st);
                } else result.setText(s);
            }}
    }

    //La fonction qui calcule l'image en prenant la fonction str en argument avec le point à calculer d
    public  double eval(final String str) {
        final int l=str.length();
        class Parser {
            int pos = -1, c;
            void mangeChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            void mangeSpace() {
                while (Character.isWhitespace(c)) {
                    mangeChar();
                }
            }
            double parse() {
                mangeChar();
                double v = parseExpression();
                if (c != -1) {
                    throw new RuntimeException("Unexpected: " + (char) c);
                }
                return v;
            }


            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    mangeSpace();
                    if (c == '+') {
                        mangeChar();
                        v += parseTerm();
                    } else if (c == '-') {
                        mangeChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    mangeSpace();
                    if (c == '/') {
                        mangeChar();
                        v /= parseFactor();
                    } else if (c == '*' || c == '(') {
                        if (c == '*') {
                            mangeChar();
                        }
                        v *= parseFactor();
                    } else {

                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                mangeSpace();
                if (c == '(') {
                    mangeChar();
                    v = parseExpression();

                    if (c == ')') {

                        mangeChar();

                    }
                } else {
                    if (c == '+' || c == '-') {
                        negate = c == '-';
                        mangeChar();
                        mangeSpace();

                    }
                    if(!(c>='0'&&c<='9'||c=='.')){
                        v=1;
                        char q=str.charAt(0);
                        if(q=='²'||q=='³'||q=='!'||q=='^'||q==')'){mangeChar();  rep=false; int i=1;
                            while(i<l){mangeChar(); i++;}   }
                        char p=str.charAt(l-1);
                        if(p=='+'||p=='-'||p=='*'||p=='/'||p=='^'||p=='('||p=='√') rep=false;

                    }else{
                        StringBuilder sb= new StringBuilder();
                        while ((c >= '0' && c <= '9') || c == '.') {
                            sb.append((char) c);
                            mangeChar();
                        }
                        if (sb.length() == 0) {
                            throw new RuntimeException("Unexpected: " + (char) c);
                        }
                        String rec=sb.substring(0,1);
                        if(rec.equals(".")){ v=1; rep=false;
                        }else v=Double.parseDouble(sb.toString());

                    }
                }

                mangeSpace();
                if(c=='π'){
                    v=v*Math.PI;
                    mangeChar();
                    if(c=='π'){mangeChar(); v=v*Math.PI;
                        while(c=='π'){ mangeChar();v=v*Math.PI;
                        }}else{
                        if(c >= '0' && c <= '9'|| c == '.'){ mangeChar(); v=1.2;  rep=false;
                            while(c>='0'&&c<='9'||c=='.') mangeChar();
                        }}
                }

                if(c=='√'){
                    mangeChar();
                    v=v*Math.sqrt(parseFactor());
                }
                //Fonction carree
                if(c=='²'){
                    v=Math.pow(v,2);
                    mangeChar();
                    if(c=='²'){mangeChar(); v=Math.pow(v,2);
                        while(c=='²'){mangeChar(); v=Math.pow(v,2);
                        }}else{
                        if(c >= '0' && c <= '9'|| c == '.'||c=='π'){ mangeChar(); v=1.2;  rep=false;
                            while(c>='0'&&c<='9'||c=='.'||c=='π') mangeChar();
                        }}

                }
                //Fonction cube
                if(c=='³'){
                    v=Math.pow(v,3);
                    mangeChar();
                    if(c=='³'){mangeChar(); v=Math.pow(v,3);
                        while(c=='³'){mangeChar(); v=Math.pow(v,3);
                        }}else{
                        if(c >= '0' && c <= '9'|| c == '.'||c=='π'){ mangeChar(); v=1.2;  rep=false;
                            while(c>='0'&&c<='9'||c=='.'||c=='π') mangeChar();
                        }}
                }
                //Fonction factoriel
                if(c=='!'){
                    mangeChar();
                    if(c >= '0' && c <= '9'|| c == '.'||c=='π'||c=='!'){ mangeChar(); v=1.2;  rep=false;
                        while(c>='0'&&c<='9'||c=='.'||c=='π'||c=='!') mangeChar();
                    }else{
                        if(v<21&&v>=0) {

                            String s = String.format("%s", v);
                            String rec = s.substring(s.length() - 2, s.length());
                            if (rec.equals(".0")) {
                                int o = 1;
                                for (int i = 1; i <= (int) v; i++) {
                                    o *= i;
                                }
                                v = (double) o;
                            }else{
                                rep=false; v=1.2;
                            }
                        }else{
                            rep=false; v=1.2;
                        }
                    }}
                //Fonction sinus
                if(c=='S'){ mangeChar(); if(c=='i'){ mangeChar(); if(c=='n'){
                    mangeChar();
                    v=v*Math.sin(parseFactor());
                } }
                }
                //Fonction cosinus
                if(c=='C'){ mangeChar(); if(c=='o'){ mangeChar(); if(c=='s'){
                    mangeChar();
                    v=v*Math.cos(parseFactor());
                } }
                }
                //Fonction Tangente
                if(c=='T'){ mangeChar(); if(c=='a'){ mangeChar(); if(c=='n'){
                    mangeChar();
                    v=v*Math.tan(parseFactor());
                } }
                }
                //Fonction Arcsin Arccos et Arctan
                if(c=='A'){ mangeChar(); if(c=='r'){ mangeChar(); if(c=='c'){ mangeChar();
                    if(c=='s'){ mangeChar(); if(c=='i'){ mangeChar(); if(c=='n'){ mangeChar(); 	v=v*Math.asin(parseFactor());
                    }}}
                    if(c=='c'){ mangeChar(); if(c=='o'){ mangeChar(); if(c=='s'){ mangeChar(); 	v=v*Math.acos(parseFactor());
                    }}}
                    if(c=='t'){ mangeChar(); if(c=='a'){ mangeChar(); if(c=='n'){ mangeChar(); 	v=v*Math.atan(parseFactor());
                    }}}}}}


                //Fonction Logarithme
                //Fonction Logarithme
                if(c=='L'){ mangeChar(); if(c=='n'){
                    mangeChar();
                    double h=parseFactor();
                    if(h>0){
                        v=v*Math.log(h);
                    }
                }}
                //Fonction Exponentielle
                if(c=='E'){ mangeChar(); if(c=='x'){ mangeChar(); if(c=='p'){
                    mangeChar();
                    v=v*Math.exp(parseFactor());
                }}}
                if (c == '^') { // exponentiation
                    mangeChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) {
                    v = -v; // exponentiation has higher priority than unary minus: -3^2=-9
                }

                return v;
            }
        }
        return new Parser().parse();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit){
            Sortir();
            return true;
        }
        if (id == R.id.description) {
            Nous();
            return true;
        }
        if (id == R.id.contact) {
            Appelle();
            return true;
        }
        if(id==R.id.act){
            return true;
        }
        if(id==R.id.off){
            Sortir();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Sortir(){
        System.exit(0);
    }
    public void Appelle(){
        Uri uri=Uri.parse("tel:+33751500834");
        Intent intent=new Intent(Intent.ACTION_CALL,uri);
       startActivity(intent);
    }
    public void Nous(){
        Intent affiche=new Intent(MainActivity.this,Propos.class);
        startActivity(affiche);
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
