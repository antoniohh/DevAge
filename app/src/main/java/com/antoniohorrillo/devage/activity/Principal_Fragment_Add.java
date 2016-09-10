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
 * {@link Principal_Fragment_Add.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Principal_Fragment_Add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Principal_Fragment_Add extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESPUESTA = "respuesta";
    private static final String TAG = "Fragment Lista";

    // TODO: Rename and change types of parameters
    private String respuesta;
    private Button btn_registro;
    private View view;
    private TextInputLayout cmpoemail;
    private TextInputLayout cmponombre;
    private TextInputLayout cmpoapellidos;
    private TextInputLayout cmpoespecialidad;
    private TextInputLayout cmpodni;
    private String txtonombre;
    private String txtoemail;
    private String txtoapellidos;
    private String txtoespecialidad;
    private String txtodni;

    private OnFragmentInteractionListener mListener;

    public Principal_Fragment_Add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param respuesta Parameter 1.
     * @return A new instance of fragment Principal_Fragment_Add.
     */
    // TODO: Rename and change types and number of parameters
    public static Principal_Fragment_Add newInstance(String respuesta) {
        Principal_Fragment_Add fragment = new Principal_Fragment_Add();
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
            Log.i("Respuesta Add",respuesta);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.activity_principal_fragment_add, container, false);
        view = inflater.inflate(R.layout.activity_principal_fragment_add, container, false);
        btn_registro = (Button) view.findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onClick(View v) {

        cmponombre = (TextInputLayout)getActivity().findViewById(R.id.cmpo_nombre_wrapper);
        cmpoemail = (TextInputLayout)getActivity().findViewById(R.id.cmpo_email_wrapper);
        cmpoapellidos = (TextInputLayout)getActivity().findViewById(R.id.cmpo_apellidos_wrapper);
        cmpoespecialidad = (TextInputLayout)getActivity().findViewById(R.id.cmpo_especialidad_wrapper);
        cmpodni = (TextInputLayout)getActivity().findViewById(R.id.cmpo_dni_wrapper);
        txtonombre = cmponombre.getEditText().getText().toString();
        txtoemail = cmpoemail.getEditText().getText().toString();
        txtoapellidos = cmpoapellidos.getEditText().getText().toString();
        txtoespecialidad = cmpoespecialidad.getEditText().getText().toString();
        txtodni = cmpodni.getEditText().getText().toString();

        if (mListener != null) {
            if (!validate()) {
                return;
            } else {
                mListener.onFragmentInteractionAdd(txtodni, txtonombre, txtoapellidos, txtoemail, txtoespecialidad);
            }
        }
    }

    public boolean validate() {

        boolean valid = true;

        if (txtodni.isEmpty()) {
            cmpodni.setError(getResources().getString(R.string.errorsignupdni));
            valid = false;
        } else {
            cmpodni.setError(null);
        }

        if (txtonombre.isEmpty() || txtonombre.length() < 3) {
            cmponombre.setError(getResources().getString(R.string.errorsignupnombre));
            valid = false;
        } else {
            cmponombre.setError(null);
        }

        if (txtoapellidos.isEmpty() || txtoapellidos.length() < 3) {
            cmpoapellidos.setError(getResources().getString(R.string.errorsignupapellidos));
            valid = false;
        } else {
            cmpoapellidos.setError(null);
        }

        if (txtoemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(txtoemail).matches()) {
            cmpoemail.setError(getResources().getString(R.string.errorloginemail));
            valid = false;
        } else {
            cmpoemail.setError(null);
        }

        if (txtoespecialidad.isEmpty() || txtoespecialidad.length() < 2) {
            cmpoespecialidad.setError(getResources().getString(R.string.errorsignupespecialidad));
            valid = false;
        } else {
            cmpoespecialidad.setError(null);
        }

        return valid;
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
        void onFragmentInteractionAdd(String txtodni, String txtonombre, String txtoapellidos,
                                      String txtoemail, String txtoespecialidad);
    }
}
