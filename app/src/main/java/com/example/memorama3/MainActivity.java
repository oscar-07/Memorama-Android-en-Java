package com.example.memorama3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    ImageButton caja0, caja1, caja2, caja3, caja4, caja5, caja6, caja7, caja8, caja9, caja10, caja11;
    ImageButton[] Cajas = new ImageButton[12];
    Button botonReiniciar;
    TextView textoPuntuacion, textoErrores, ganasteoperdiste;
    int errores, aciertos;
    int a1, a2;
    int[] imagenes;
    int tapa;
    ArrayList<Integer> random;
    ImageButton primero;
    boolean bloqueo = false;
    final Handler handler = new Handler();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void cajaBotones(){
        caja0 = findViewById(R.id.Caja0);
        caja1 = findViewById(R.id.Caja1);
        caja2 = findViewById(R.id.Caja2);
        caja3 = findViewById(R.id.Caja3);
        caja4 = findViewById(R.id.Caja4);
        caja5 = findViewById(R.id.Caja5);
        caja6 = findViewById(R.id.Caja6);
        caja7 = findViewById(R.id.Caja7);
        caja8 = findViewById(R.id.Caja8);
        caja9 = findViewById(R.id.Caja9);
        caja10 = findViewById(R.id.Caja10);
        caja11 = findViewById(R.id.Caja11);


        Cajas[0] = caja0;
        Cajas[1] = caja1;
        Cajas[2] = caja2;
        Cajas[3] = caja3;
        Cajas[4] = caja4;
        Cajas[5] = caja5;
        Cajas[6] = caja6;
        Cajas[7] = caja7;
        Cajas[8] = caja8;
        Cajas[9] = caja9;
        Cajas[10] = caja10;
        Cajas[11] = caja11;

    }

    private void cargarBotones(){
        botonReiniciar = findViewById(R.id.Reiniciar);
        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ganasteoperdiste.setText(" ");
                textoPuntuacion.setText("0/6");
                textoErrores.setText("0/5");
                init();
            }
        });


    }

    private void cargarTexto(){
        textoPuntuacion = findViewById(R.id.CajaPuntaje);
        textoErrores = findViewById(R.id.CajaErrores);
        ganasteoperdiste = findViewById(R.id.ganasteoperdiste);
        errores = 0;
        aciertos = 0;
        textoPuntuacion.setText("0/6");
        textoErrores.setText("0/5");

    }

    private void cargarImagenes(){
        imagenes = new int[]{
                R.drawable.a1,
                R.drawable.a2,
                R.drawable.a3,
                R.drawable.a4,
                R.drawable.a5,
                R.drawable.a6
        };
        tapa = R.drawable.a0;
    }

    private ArrayList<Integer> Cambia(int longitud){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i=0; i<longitud*2; i++){
            result.add(i % longitud);
        }
        Collections.shuffle(result);
        return result;
    }

    private void comprobar(int i, final ImageButton imgb){
        if(primero == null){
            primero = imgb;
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[random.get(i)]);
            primero.setEnabled(false);
            a1 = random.get(i);
        } else {
            bloqueo = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imagenes[random.get(i)]);
            imgb.setEnabled(false);
            a2 = random.get(i);
            if(a1 == a2){
                primero = null;
                bloqueo = false;
                aciertos++;
                textoPuntuacion.setText(aciertos+"/6");
                if(aciertos == 6 && errores<5){
                    ganasteoperdiste.setText("Ganaste");
                }
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(tapa);
                        primero.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(tapa);
                        imgb.setEnabled(true);
                        bloqueo = false;
                        primero = null;
                        errores++;
                        textoErrores.setText(errores+"/5");
                        if (errores>=5){
                            textoErrores.setText(errores+"/"+errores);
                        }

                        if(errores == 5){
                            ganasteoperdiste.setText("Perdiste");
                        }

                    }
                }, 2000);
            }
        }
    }

    private void init(){
        cajaBotones();
        cargarBotones();
        cargarTexto();
        cargarImagenes();
        random = Cambia(imagenes.length);
        for(int i=0; i<Cajas.length; i++){
            Cajas[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            Cajas[i].setImageResource(imagenes[random.get(i)]);
            //tablero[i].setImageResource(fondo);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<Cajas.length; i++){
                    Cajas[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Cajas[i].setImageResource(tapa);
                }
            }
        }, 4000);
        for(int i=0; i<Cajas.length; i++) {
            final int j = i;
            Cajas[i].setEnabled(true);
            Cajas[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bloqueo)
                        comprobar(j, Cajas[j]);
                }
            });
        }

    }

}
