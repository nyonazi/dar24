package com.dar24.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dar24.app.R;
import com.dar24.app.activity.WebActivity;
import com.dar24.app.adapter.NewsRelatedAdapter;
import com.dar24.app.model.News;
import com.dar24.app.utility.Helpers;
import com.dar24.app.utility.SharedPref;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Simon Mawole
 * @version 0.1.0
 * @email simonmawole2011@gmail.com
 * @since 0.1.0
 */
public class NewsDetailFragment extends Fragment {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.tvPublishedAt)
    TextView tvPublishedAt;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ivThumbnail)
    ImageView ivThumbnail;
    @BindView(R.id.llNextNews)
    LinearLayout llNextNews;
    @BindView(R.id.llPreviousNews)
    LinearLayout llPreviousNews;
    @BindView(R.id.tvNextNewsTitle)
    TextView tvNextTitle;
    @BindView(R.id.tvPreviousNewsTitle)
    TextView tvPreviousTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private News news;
    private SharedPref pref;
    private int descriptionSize;
    private String nextTitle = "";
    private String previousTitle = "";
    private NewsDetailFragmentListener mListener;
    private NewsRelatedAdapter newsRelatedAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (NewsDetailFragmentListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SharedPref(getActivity());

        Bundle bundle = getArguments();
        news = Parcels.unwrap(bundle.getParcelable("news"));
        nextTitle = bundle.containsKey("next_title") ? bundle.getString("next_title") : "";
        previousTitle = bundle.containsKey("previous_title") ? bundle.getString("previous_title") : "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (pref.getFontSize() == SharedPref.FONT_SIZE_SMALL) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

            descriptionSize = 15;
        } else if (pref.getFontSize() == SharedPref.FONT_SIZE_MEDIUM) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            descriptionSize = 17;
        } else if (pref.getFontSize() == SharedPref.FONT_SIZE_LARGE) {
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tvCategory.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tvPublishedAt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            descriptionSize = 20;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTitle.setText(Html.fromHtml(news.getTitle(),
                    Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTitle.setText(Html.fromHtml(news.getTitle()));
        }
        tvCategory.setText(news.getCategory());
        tvPublishedAt.setText(Helpers.getTimeAgo(getActivity(),
                Helpers.stringDateToLong(news.getPublishedAt())) + ", " + getString(R.string.by)
                + " " + news.getAuthor());
        try {
            Picasso.get().load(news.getThumbnail())
                    .placeholder(R.color.grey_200)
                    .into(ivThumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().length() == 0) {
                    return false;
                }
                /*This line is commented to stop link click to function in webview*/
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                view.getContext().startActivity(intent);
                Intent aboutIntent = new Intent(getActivity(), WebActivity.class);
                aboutIntent.putExtra("title", url);
                aboutIntent.putExtra("url", url);
                startActivity(aboutIntent);
                return true;
            }
        });

        webView.setVisibility(View.VISIBLE);
        webView.loadDataWithBaseURL("file:///android_asset/",
                buildHtml(news.getDescription(), descriptionSize), "text/html",
                "UTF-8", null);

        if (nextTitle.length() == 0) {
            llNextNews.setVisibility(View.GONE);
        } else {
            tvNextTitle.setText(nextTitle);
            llNextNews.setVisibility(View.VISIBLE);
        }
        if (previousTitle.length() == 0) {
            llPreviousNews.setVisibility(View.GONE);
        } else {
            tvPreviousTitle.setText(previousTitle);
            llPreviousNews.setVisibility(View.VISIBLE);
        }

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        newsRelatedAdapter = new NewsRelatedAdapter(getActivity());
        recyclerView.swapAdapter(newsRelatedAdapter, false);
        newsRelatedAdapter.addItems(news.getNewsRelatedList());

        return rootView;
    }

    @OnClick(R.id.llPreviousNews)
    void onPreviousNews() {
        mListener.onPrevious();
    }

    @OnClick(R.id.llNextNews)
    void onNextNews() {
        mListener.onNext();
    }

    private String buildHtml(String data, int textSize) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        @font-face {\n" +
                "            font-family: 'ubuntu';\n" +
                "            src: url('fonts/Ubuntu-Regular.ttf');\n" +
                "        }\n" +
                "        body {\n" +
                "            font-family: \"ubuntu\";\n" +
                "            background-color: #FAFAFA;\n" +
                "            width: 100%;\n" +
                "            font-size: " + textSize + "px;" +
                "            text-align: justify;\n" +
                "            margin: auto;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        iframe {\n" +
                "            width: 100%;\n" +
                "            height: auto;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            max-width:100% !important;\n" +
                "            width: 100% !important;\n" +
                "            height:auto !important;\n" +
                "        }\n" +
                "\n" +
                "        div {\n" +
                "            max-width:100% !important;\n" +
                "            width: 100% !important;\n" +
                "            height:auto !important;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>";
        html += data;
        html += "<br/><br/>" +
                "</body>\n" +
                "\n" +
                "</html>";
        return html;
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    public interface NewsDetailFragmentListener {
        void onNext();

        void onPrevious();
    }

}
