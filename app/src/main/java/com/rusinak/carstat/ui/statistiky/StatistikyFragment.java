package com.rusinak.carstat.ui.statistiky;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rusinak.carstat.Databaza;
import com.rusinak.carstat.Kalkulacka;
import com.rusinak.carstat.MainActivity;
import com.rusinak.carstat.R;
import com.rusinak.carstat.ui.gallery.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class StatistikyFragment extends Fragment {
    private StatistikyViewModel statistikyViewModel;

    RecyclerView recyclerView;
    StatistikyAdapter statistikyAdapter;
    Kalkulacka kalkulacka = new Kalkulacka();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statistikyViewModel =
                new ViewModelProvider(this).get(StatistikyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_statistiky, container, false);
        //final TextView textView = root.findViewById(R.id.text_statistiky);
        statistikyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            //    textView.setText(s);
            }
        });

        recyclerView = root.findViewById(R.id.statistikyTank);
        setRecyclerView(vratListTank());
        recyclerView = root.findViewById(R.id.statistikyUdrzba);
        setRecyclerView(vratListUdrzba());
        recyclerView = root.findViewById(R.id.statistikySTK);
        setRecyclerView(vratListSTK());
        recyclerView = root.findViewById(R.id.statistikyOpravy);
        setRecyclerView(vratListOpravy());
        recyclerView = root.findViewById(R.id.statistikyVseob);
        setRecyclerView(vratListVseob());
        return root;
    }

    private void setRecyclerView(List<StatistikyModel> model) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statistikyAdapter = new StatistikyAdapter(getContext(), model);
        recyclerView.setAdapter(statistikyAdapter);
    }

    private List<StatistikyModel> vratListTank() {
        List<StatistikyModel> listStat = new ArrayList<>();
        listStat.add(new StatistikyModel("", ""));
        listStat.add(new StatistikyModel("Posl. priemerná spotreba" , kalkulacka.priemernaSpotrebaPosl(((MainActivity)getActivity()).getDatabaza()) + " l/100km"));
        listStat.add(new StatistikyModel("Celk. priemerná spotreba" , kalkulacka.priemernaSpotrebaCelk(((MainActivity)getActivity()).getDatabaza()) + " l/100km"));
        listStat.add(new StatistikyModel("Posl. cena za liter" , kalkulacka.cenaZaLiterPosl(((MainActivity)getActivity()).getDatabaza()) + " €/liter"));
        listStat.add(new StatistikyModel("Celk. priemerná cena za liter" , kalkulacka.cenaZaLiterPriemer(((MainActivity)getActivity()).getDatabaza()) + " €/liter"));
        listStat.add(new StatistikyModel("Celk. utratená suma na PHM" , kalkulacka.celkovaSumaZaTank(((MainActivity)getActivity()).getDatabaza()) + " €"));
        listStat.add(new StatistikyModel("Posl. cena za KM" , kalkulacka.cenaZaKMPoslTank(((MainActivity)getActivity()).getDatabaza()) + " €/km"));
        listStat.add(new StatistikyModel("Celk. cena za KM" , kalkulacka.cenaZaKMCelkTank(((MainActivity)getActivity()).getDatabaza()) + " €/km"));
        listStat.add(new StatistikyModel("", ""));
        return listStat;
    }

    private List<StatistikyModel> vratListUdrzba() {
        List<StatistikyModel> listStat = new ArrayList<>();
        listStat.add(new StatistikyModel("", ""));
        listStat.add(new StatistikyModel("Posl. výmena oleja" , kalkulacka.poslednaVymOleja(((MainActivity)getActivity()).getDatabaza())));
        listStat.add(new StatistikyModel("Posl. záručná prehliadka" , kalkulacka.poslednaZarPrehliadka(((MainActivity)getActivity()).getDatabaza())));
        listStat.add(new StatistikyModel("Celk. utratená suma na údržbu" , kalkulacka.celkovaSumaZaUdrzbu(((MainActivity)getActivity()).getDatabaza()) + " €"));;
        listStat.add(new StatistikyModel("", ""));
        return listStat;
    }

    private List<StatistikyModel> vratListSTK() {
        List<StatistikyModel> listStat = new ArrayList<>();
        listStat.add(new StatistikyModel("", ""));
        listStat.add(new StatistikyModel("Posl. technická kontrola" , kalkulacka.poslednaTK(((MainActivity)getActivity()).getDatabaza())));
        listStat.add(new StatistikyModel("Posl. emisná kontrola" , kalkulacka.poslednaEK(((MainActivity)getActivity()).getDatabaza())));
        listStat.add(new StatistikyModel("Posl. kontrola originality" , kalkulacka.poslednaKO(((MainActivity)getActivity()).getDatabaza())));
        listStat.add(new StatistikyModel("", ""));
        return listStat;
    }

    private List<StatistikyModel> vratListOpravy() {
        List<StatistikyModel> listStat = new ArrayList<>();
        listStat.add(new StatistikyModel("", ""));
        listStat.add(new StatistikyModel("Celková utratená suma na opravy" , kalkulacka.celkovaSumaZaOpravy(((MainActivity)getActivity()).getDatabaza()) + " €"));
        listStat.add(new StatistikyModel("Dátum poslednej opravy" , kalkulacka.poslednaOprava(((MainActivity)getActivity()).getDatabaza())));
        listStat.add(new StatistikyModel("", ""));
        return listStat;
    }

    private List<StatistikyModel> vratListVseob() {
        List<StatistikyModel> listStat = new ArrayList<>();
        listStat.add(new StatistikyModel("", ""));
        listStat.add(new StatistikyModel("Celková suma utratená na auto" , kalkulacka.celkovaSumaZaAuto(((MainActivity)getActivity()).getDatabaza()) + " €"));
        listStat.add(new StatistikyModel("", ""));
        return listStat;
    }
}
