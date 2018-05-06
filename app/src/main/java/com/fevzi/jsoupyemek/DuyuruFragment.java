package com.fevzi.jsoupyemek;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;



public class DuyuruFragment extends Fragment {

    ListView duyuruListView;

    ArrayList<String> duyuruList = new ArrayList<>();
    ArrayList<String> duyuruLinks = new ArrayList<>();

    public DuyuruFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duyuru, container, false);

        duyuruListView = (ListView) view.findViewById(R.id.duyuruList);
        duyuruListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(duyuruLinks.get(i)));
                startActivity(intent);
            }
        });


        fetchDuyuruList();
        return view;
    }


    private void fetchDuyuruList() {

        new AsyncTask<Void, Boolean, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    Document doc = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar").get();
                    Element divcaContent = doc.getElementsByClass("contentAnnouncements").get(0).getElementsByClass("caContent").get(0);
                    Elements cncItems = divcaContent.getElementsByClass("cncItem");

                    for (int i = 0; i < cncItems.size(); i++) {
                        String duyuru;
                        String link;
                        duyuru = cncItems.get(i).select("a").text();
                        link = "http://www.ybu.edu.tr/muhendislik/bilgisayar/" + cncItems.get(i).select("a").attr("href");
                        duyuruList.add(duyuru);
                        duyuruLinks.add(link);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_list_item_1, duyuruList);
                    duyuruListView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Can not connect to internet!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
