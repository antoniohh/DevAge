package com.antoniohorrillo.devage.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.antoniohorrillo.devage.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Principal_Fragment_Buscar.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Principal_Fragment_Buscar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Principal_Fragment_Buscar extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESPUESTA = "respuesta";
    private TextInputLayout cmpodni;
    private String txtodni;
    private Button btn_buscar;
    private View view;

    // TODO: Rename and change types of parameters
    private String respuesta;

    private OnFragmentInteractionListener mListener;

    public Principal_Fragment_Buscar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param respuesta
     * @return
     */
    // TODO: Rename and change types and number of parameters
    public static Principal_Fragment_Buscar newInstance(String respuesta) {
        Principal_Fragment_Buscar fragment = new Principal_Fragment_Buscar();
        Bundle args = new Bundle();
        args.putString(RESPUESTA, respuesta);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            respuesta = getArguments().getString(RESPUESTA);
            Log.i("Respuesta Buscar",respuesta);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.activity_principal_fragment_buscar, container, false);
        view = inflater.inflate(R.layout.activity_principal_fragment_buscar, container, false);
        btn_buscar = (Button) view.findViewById(R.id.btn_buscar);
        btn_buscar.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onClick(View v) {
        cmpodni = (TextInputLayout)getActivity().findViewById(R.id.cmpo_dni_wrapper);
        txtodni = cmpodni.getEditText().getText().toString();
        if (mListener != null) {
            if (txtodni.isEmpty()) {
                cmpodni.setError(getResources().getString(R.string.errorsignupdni));
            } else {
                cmpodni.setError(null);
                mListener.onFragmentInteractionBuscar(txtodni);
            }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionBuscar(String dni);
    }
}
