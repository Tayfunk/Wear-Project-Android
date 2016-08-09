package matriks.wearproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Q on 03.08.2016.
 */
public class Haberler extends WearableActivity {
    public List<String> list_parent;
    public ExpandListViewAdapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> gs_list;
    public List<String> fb_list;
    public int last_position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.haberler);
        setAmbientEnabled();

        expandlist_view = (ExpandableListView)findViewById(R.id.expand_listview);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // Adapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter = new ExpandListViewAdapter(getApplicationContext(), list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view.setClickable(true);

        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String child_name = (String) expand_adapter.getChild(groupPosition, childPosition);


                AlertDialog.Builder builder = new AlertDialog.Builder(Haberler.this);
                builder.setMessage(child_name)
                        .setTitle("Haberler "+groupPosition+1)
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


                return false;
            }
        });

    }


    public void Hazırla()
    {
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("Haberler 1");  // ilk başlığı giriyoruz
        list_parent.add("Haberler 2");   // ikinci başlığı giriyoruz

        gs_list = new ArrayList<String>();  // ilk başlık için alt elemanları tanımlıyoruz
        gs_list.add("But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of");
        gs_list.add("At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti");


        fb_list = new ArrayList<String>(); // ikinci başlık için alt elemanları tanımlıyoruz
        fb_list.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim a");
        fb_list.add("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo");


        list_child.put(list_parent.get(0),gs_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), fb_list); // ikinci başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz


    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandlist_view.setIndicatorBounds(width - GetPixelFromDips(35), width - GetPixelFromDips(5));
        } else {
            expandlist_view.setIndicatorBoundsRelative(width - GetPixelFromDips(35), width - GetPixelFromDips(5));
        }
    }


}

