package com.xbenes2.pexeso;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import static com.xbenes2.pexeso.R.drawable.letter_a;
import static com.xbenes2.pexeso.R.drawable.letter_b;
import static com.xbenes2.pexeso.R.drawable.letter_c;
import static com.xbenes2.pexeso.R.drawable.letter_d;
import static com.xbenes2.pexeso.R.drawable.letter_e;
import static com.xbenes2.pexeso.R.drawable.letter_f;
import static com.xbenes2.pexeso.R.drawable.letter_g;
import static com.xbenes2.pexeso.R.drawable.letter_h;
import static com.xbenes2.pexeso.R.drawable.letter_i;
import static com.xbenes2.pexeso.R.drawable.letter_j;
import static com.xbenes2.pexeso.R.drawable.letter_k;
import static com.xbenes2.pexeso.R.drawable.letter_l;
import static com.xbenes2.pexeso.R.drawable.letter_m;
import static com.xbenes2.pexeso.R.drawable.letter_n;
import static com.xbenes2.pexeso.R.drawable.letter_o;
import static com.xbenes2.pexeso.R.drawable.letter_p;
import static com.xbenes2.pexeso.R.drawable.letter_q;
import static com.xbenes2.pexeso.R.drawable.letter_r;
import static com.xbenes2.pexeso.R.drawable.letter_s;
import static com.xbenes2.pexeso.R.drawable.letter_t;
import static com.xbenes2.pexeso.R.drawable.letter_u;
import static com.xbenes2.pexeso.R.drawable.letter_v;
import static com.xbenes2.pexeso.R.drawable.letter_w;
import static com.xbenes2.pexeso.R.drawable.letter_x;
import static com.xbenes2.pexeso.R.drawable.letter_y;
import static com.xbenes2.pexeso.R.drawable.letter_z;

public class MainActivity extends AppCompatActivity {
    public static final int IMAGE_SIZE_DP = 100;
    public static final int HIDE_TIMEOUT = 1000;

    // https://material.io/guidelines/patterns/navigation-drawer.html#navigation-drawer-specs
    private static final int HEADER_HEIGHT_DP = 24;

    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        System.out.println(metrics.widthPixels);
        System.out.println(metrics.heightPixels);
        return metrics;
    }

    private int getNumberOfFittingImages() {
        DisplayMetrics displayMetrics = this.getDisplayMetrics();
        return Utils.getNumberOfFittingSquares(
            displayMetrics.widthPixels / displayMetrics.density,
            (displayMetrics.heightPixels - HEADER_HEIGHT_DP) / displayMetrics.density,
            IMAGE_SIZE_DP
        );
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] letters = getLettersImageResources();
        int[] images = Utils.randomSubset(letters, this.getNumberOfFittingImages() / 2);

        final ImageAdapter imageAdapter = new ImageAdapter(this);
        final GameController gameController = new GameController(images, R.drawable.letter_unknown, R.drawable.letter_over);
        imageAdapter.setImages(gameController.getItems());

        final GridView gridview = (GridView) findViewById(R.id.imagesGrid);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GameStatus status = gameController.go(position);
                imageAdapter.setImages(gameController.getItems());

                if (status == GameStatus.SHOULD_FINISH_GO) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gameController.finishGo();
                            imageAdapter.setImages(gameController.getItems());
                        }
                    }, HIDE_TIMEOUT);
                }
            }
        });
    }

    private int[] getLettersImageResources() {
        return new int[] {
            letter_a, letter_b, letter_c, letter_d, letter_e, letter_f, letter_g, letter_h, letter_i,
            letter_j, letter_k, letter_l, letter_m, letter_n, letter_o, letter_p, letter_q, letter_r,
            letter_s, letter_t, letter_u, letter_v, letter_w, letter_x, letter_y, letter_z
        };
    }
}
