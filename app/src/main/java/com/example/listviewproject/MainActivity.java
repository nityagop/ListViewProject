package com.example.listviewproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList <Icecream> arrayList;
    TextView textPort, textLand;
    Button button;
    public static final String KEY_SAVED = "key1";
    public static final String KEY2_SAVED = "key2";
    public static final String KEY3_SAVED = "key3";
    String saveName = "";
    String saveDesc = "";

    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_SAVED, arrayList);
        outState.putString(KEY2_SAVED, saveName);
        outState.putString(KEY3_SAVED, saveDesc);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.id_main_listview);
        textPort = findViewById(R.id.id_textPort);
        textLand = findViewById(R.id.id_textLand);


        arrayList = new ArrayList <> ();

        arrayList.add(new Icecream("Vanilla", "Vanilla icecream was invented in 1805. The vanilla flavor often comes from vanilla beans or vanilla bean extract. I enjoy vanilla icecream but I always need some sort of topping added to it becauase to me just vanilla is too boring", R.drawable.vanilla));
        arrayList.add(new Icecream("Mint Chip",   "Mint Chip icecream was invented in 1973.  The flavor comes from either peppermint or spearmint extract and chocolate chips. Mint chip is definitely one of my favorite flavors because I enjoy the spearmint/peppermint flavor.", R.drawable.mint));
        arrayList.add(new Icecream("Chocolate",  "Chocolate icecream is another class flavor that was invented in 1692.  The flavor comes from cocoa beans. I do really like chocolate icecream but I would never choose it at an icecream shop because there are so many better flavors", R.drawable.chocolate));
        arrayList.add(new Icecream("Strawberry", "Strawberry icecream was invented in 1813. The icecream is made from fresh strawberries or strawberry extract. I'm not a huge fan of strawberry icecream, sometimes I feel it tastes too artifical. However, I do not mind fresh strawberries in my icecream", R.drawable.strawberry));
        arrayList.add(new Icecream("Coffee", "Coffee icecream was invented in 1869. When it was invented in 1869 it was put in parfaits rather than eaten alone. I never used to like coffee icecream before but now I find myself really enjoying it, probably because I've been drinking more coffee", R.drawable.coffee));
        arrayList.add(new Icecream("Nutella", "There is no exact date of when nutella icecream was invented but it is fairly recent. The flavor comes from the hazelnut spread, nutella. I'm pretty indifferent to nutella icecream but it's still a good flavor, anything with nutella is good to me", R.drawable.nutella));
        arrayList.add(new Icecream("Cheesecake",  "The exact date of when cheesecake icecream was invented is unknown but it became popular after Ben & Jerrys created their Strawberry Cheesecake flavor. I love cheesecake icecream most likely because I love cheesecake. I think its not to boring of a flavor but not too fancy either", R.drawable.strawberrycheesecake));



        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            if (savedInstanceState != null)
            {
                arrayList = savedInstanceState.getParcelableArrayList(KEY_SAVED);
                saveName = savedInstanceState.getString(KEY2_SAVED);
            }
            textPort.setText(saveName);
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            if (savedInstanceState != null)
            {
                arrayList = savedInstanceState.getParcelableArrayList(KEY_SAVED);
                saveName = savedInstanceState.getString(KEY2_SAVED);
                saveDesc = savedInstanceState.getString(KEY3_SAVED);
            }
            textPort.setText(saveName);
            textLand.setText(saveDesc);
        }

        ListViewAdapter adapter = new ListViewAdapter(this, R.layout.adapter_listview, arrayList);
        listView.setAdapter(adapter);

    }

    public class Icecream implements Parcelable
    {
        private String name;
        private String description;
        private int image;

        public Icecream (String n, String desc, int i)
        {
            name = n;
            description = desc;
            image = i;
        }

        protected Icecream(Parcel in) {
            name = in.readString();
            description = in.readString();
            image = in.readInt();
        }

        public final Creator<Icecream> CREATOR = new Creator<Icecream>() {
            @Override
            public Icecream createFromParcel(Parcel in) {
                return new Icecream(in);
            }

            @Override
            public Icecream[] newArray(int size) {
                return new Icecream[size];
            }
        };

        public String getName()
        {
            return name;
        }

        public int getImage()
        {
            return image;
        }


        public String getDescription()
        {
            return description;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(description);
            dest.writeInt(image);
        }
    }

    public class ListViewAdapter extends ArrayAdapter<Icecream>
    {
        Context mainContext;
        int xml;
        List <Icecream> list;

        public ListViewAdapter(@NonNull Context context, int resource, @NonNull List <Icecream> objects) {
            super(context, resource, objects);
            mainContext = context;
            xml = resource;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)mainContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterLayout = inflater.inflate(xml, null);

            ImageView imageView = adapterLayout.findViewById(R.id.id_adapter_imageview);
            TextView textView = adapterLayout.findViewById(R.id.id_adapter_textview);
            Button button = adapterLayout.findViewById(R.id.id_adapter_button2);

            imageView.setImageResource(arrayList.get(position).getImage());
            textView.setText(arrayList.get(position).getName());
            button.setText("Remove");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                     notifyDataSetChanged();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    saveName = list.get(position).getName();
                    saveDesc = list.get(position).getDescription();

                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        textPort.setText(saveName);
                    }

                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        textPort.setText(saveName);
                        textLand.setText(saveDesc);
                    }
                }
            });

            return adapterLayout;
        }
    }
}