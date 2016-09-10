package com.antoniohorrillo.devage.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.antoniohorrillo.devage.R;
import com.antoniohorrillo.devage.model.Developer;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Principal_Fragment_Lista.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Principal_Fragment_Lista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Principal_Fragment_Lista extends ListFragment implements AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESPUESTA = "respuesta";
    private static final String DEVELOPERS = "developers";
    private static final String TAG = "Fragment Lista";

    // TODO: Rename and change types of parameters
    private ArrayList<Developer> developers;
    private ArrayList<String> listado;
    private String respuesta;

    private OnFragmentInteractionListener mListener;

    public Principal_Fragment_Lista() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param developers Parameter 1.
     * @param respuesta Parameter 2.
     * @return A new instance of fragment Principal_Fragment_Lista.
     */
    // TODO: Rename and change types and number of parameters
    public static Principal_Fragment_Lista newInstance(ArrayList<Developer> developers, String respuesta) {
        Principal_Fragment_Lista fragment = new Principal_Fragment_Lista();
        Bundle args = new Bundle();
        args.putSerializable(DEVELOPERS, (Serializable) developers);
        args.putString(RESPUESTA, respuesta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_principal_fragment_lista, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (getArguments() != null) {
                respuesta = getArguments().getString(RESPUESTA);
                Log.i("Respuesta Lista",respuesta);

                developers = (ArrayList<Developer>) getArguments().getSerializable(DEVELOPERS);

                listado = new ArrayList<String>();

                for (int i = 0; i < developers.size(); i++) {
                    listado.add(developers.get(i).getNombre()+" "+developers.get(i).getApellidos());
                }

                ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listado);
                setListAdapter(adaptador);
                getListView().setOnItemClickListener(this);
            }
        }
        catch (NullPointerException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        if (mListener != null) {
            mListener.onFragmentInteractionLista(position);
            Log.d(TAG, "Posicion marcada ArrayList: "+position);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int i) {
        if (mListener != null) {
            mListener.onFragmentInteractionLista(i);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionLista(int i);
    }
}
