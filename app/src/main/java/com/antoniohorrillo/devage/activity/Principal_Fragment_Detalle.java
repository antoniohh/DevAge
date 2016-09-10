package com.antoniohorrillo.devage.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.antoniohorrillo.devage.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Principal_Fragment_Detalle.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Principal_Fragment_Detalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Principal_Fragment_Detalle extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DNI = "dni";
    private static final String NOMBRE = "nombre";
    private static final String APELLIDOS = "apellidos";
    private static final String EMAIL = "email";
    private static final String ESPECIALIDAD = "especialidad";

    // TODO: Rename and change types of parameters
    private String dni;
    private String nombre;
    private String apellidos;
    private String email;
    private String especialidad;

    private TextView tvdni;
    private TextView tvnombre;
    private TextView tvapellidos;
    private TextView tvemail;
    private TextView tvespecialidad;

    private OnFragmentInteractionListener mListener;

    public Principal_Fragment_Detalle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dni
     * @param nombre
     * @param apellidos
     * @param email
     * @param especialidad
     * @return
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String dni, String nombre, String apellidos,
                                                         String email, String especialidad) {
        Fragment fragment = new Principal_Fragment_Detalle();
        Bundle args = new Bundle();
        args.putString(DNI, dni);
        args.putString(NOMBRE, nombre);
        args.putString(APELLIDOS, apellidos);
        args.putString(EMAIL, email);
        args.putString(ESPECIALIDAD, especialidad);
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
        return inflater.inflate(R.layout.activity_principal_fragment_detalle, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onFragmentInteractionDetalle();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (getArguments() != null) {
                dni = getArguments().getString(DNI);
                nombre = getArguments().getString(NOMBRE);
                apellidos = getArguments().getString(APELLIDOS);
                email = getArguments().getString(EMAIL);
                especialidad = getArguments().getString(ESPECIALIDAD);

                tvdni = (TextView)getActivity().findViewById(R.id.txtdnisql);
                tvnombre = (TextView)getActivity().findViewById(R.id.txtnombresql);
                tvapellidos = (TextView)getActivity().findViewById(R.id.txtapellidossql);
                tvemail = (TextView)getActivity().findViewById(R.id.txtemailsql);
                tvespecialidad = (TextView)getActivity().findViewById(R.id.txtespecialidadsql);

                tvdni.setText(dni);
                tvnombre.setText(nombre);
                tvapellidos.setText(apellidos);
                tvemail.setText(email);
                tvespecialidad.setText(especialidad);
            }
        }
        catch (NullPointerException ex) {
            System.err.println(ex);
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
        void onFragmentInteractionDetalle();
    }
}
