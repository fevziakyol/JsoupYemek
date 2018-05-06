package com.fevzi.jsoupyemek;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class YemekFragment extends Fragment {

    TextView tarihTextView;
    TextView yemek1TextView;
    TextView yemek2TextView;
    TextView yemek3TextView;
    TextView yemek4TextView;

    public YemekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yemek, container, false);

        tarihTextView = (TextView) view.findViewById(R.id.tarih);
        yemek1TextView = (TextView) view.findViewById(R.id.yemek1);
        yemek2TextView = (TextView) view.findViewById(R.id.yemek2);
        yemek3TextView = (TextView) view.findViewById(R.id.yemek3);
        yemek4TextView = (TextView) view.findViewById(R.id.yemek4);


        fetchYemekList();
        return view;
    }

    private void fetchYemekList() {
        final ArrayList<String> yemekList= new ArrayList<>();

        new AsyncTask<Void,Boolean,Boolean>(){

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Document doc = Jsoup.connect("http://ybu.edu.tr/sks").get();
                    Element table = doc.select("table").get(0);
                    Element tbody = table.select("tbody").get(0);
                    Element tr = tbody.select("tr").get(0);
                    Element td = tr.select("td").get(0);
                    Element yemektable = td.select("table").get(0);
                    Element yemektbody = yemektable.select("tbody").get(0);
                    Elements yemekler = yemektbody.select("tr");

                    // 1 den başlamasının sebebi ilk rowda resim olamsı
                    for (int i = 1; i< yemekler.size(); i++) {
                        String yemek;
                        if (i == 1) {
                            yemek = yemekler.get(i).select("td").select("h3").text();
                        } else {
                            yemek = yemekler.get(i).select("td").select("p").select("p").text();
                        }
                        yemekList.add(yemek);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean isSuccess) {
                if (isSuccess) {
                    tarihTextView.setText(yemekList.get(0));
                    yemek1TextView.setText(yemekList.get(1));
                    yemek2TextView.setText(yemekList.get(2));
                    yemek3TextView.setText(yemekList.get(3));
                    yemek4TextView.setText(yemekList.get(4));
                } else {
                    Toast.makeText(getContext(), "Can not connect to internet!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
