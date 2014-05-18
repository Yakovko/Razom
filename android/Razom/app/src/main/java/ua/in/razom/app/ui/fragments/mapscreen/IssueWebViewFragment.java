package ua.in.razom.app.ui.fragments.mapscreen;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ua.in.razom.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IssueWebViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IssueWebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssueWebViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ISSUEID = "";


    // TODO: Rename and change types of parameters
    private String mIssueId;

    private OnFragmentInteractionListener mListener;

    public IssueWebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param issueId Parameter 1.
     * @return A new instance of fragment IssueWebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssueWebViewFragment newInstance(String issueId) {
        IssueWebViewFragment fragment = new IssueWebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ISSUEID, issueId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIssueId = getArguments().getString(ARG_ISSUEID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_issue_web_view, container, false);
        WebView issueWebView = (WebView) v.findViewById(R.id.issue_web_view);
        issueWebView.loadUrl("http://razom.batros.in.ua/issue/" + mIssueId);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        public void onFragmentInteraction(Uri uri);
    }

}
