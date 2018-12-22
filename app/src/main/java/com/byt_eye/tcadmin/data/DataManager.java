package com.byt_eye.tcadmin.data;

import com.byt_eye.tcadmin.modals.CategoryResponse;
import com.byt_eye.tcadmin.modals.WebsitesResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataManager {
    public Observable<List<WebsitesResponse>> getWebsites(final DatabaseReference database) {

        return Observable.create(new ObservableOnSubscribe<List<WebsitesResponse>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<WebsitesResponse>> e) throws Exception {
                database.orderByKey().limitToLast(600).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            ArrayList<WebsitesResponse> websitesResponses = new ArrayList<>();

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    WebsitesResponse dataModal = noteDataSnapshot.getValue(WebsitesResponse.class);
                                    dataModal.setWebId(noteDataSnapshot.getKey());
                                    websitesResponses.add(dataModal);
                                } else {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            e.onNext(websitesResponses);
                        } catch (
                                Exception ex)

                        {
                            e.onError(ex);
                            ex.printStackTrace();
                        } finally

                        {
                            e.onComplete();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }
        });
    }

    public Observable<List<String>> getModules(final DatabaseReference database) {

        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<String>> e) throws Exception {
                database.orderByKey().limitToLast(600).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {
                            ArrayList<String> modulesList = new ArrayList<>();

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    String dataModal = noteDataSnapshot.getKey();
                                    modulesList.add(dataModal);
                                } else {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            e.onNext(modulesList);
                        } catch (Exception ex) {
                            e.onError(ex);
                            ex.printStackTrace();
                        } finally {
                            e.onComplete();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

            }
        });
    }


    public Observable<Boolean> crawlWebsite(final List<WebsitesResponse> websitesResponses) {

        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                Document doc;

                for (int i = 0; i < websitesResponses.size(); i++) {

                    try {
                        doc = (Document) Jsoup.connect(websitesResponses.get(i).getWeb_page_link());
                        // get title of the page
                        String title = doc.title();
                        System.out.println("Title: " + title);

                        // get all links
                        Elements links = doc.select("a[href]");
                        for (Element link : links) {

                            if (link.attr("href").contains("https://telugu.greatandhra.com/movies/movie-news")) {

                                // get the value from href attribute
                                System.out.println("\nLink : " + link.attr("href"));
                                System.out.println("Text : " + link.text());
                            }

                        }
                        e.onNext(true);
                        e.onComplete();
                    } catch (Exception ex) {
                        e.onError(ex);
                        ex.printStackTrace();
                    }

                }


            }


        });

    }

    ;

    public Observable<List<CategoryResponse>> getCategoriesOfLanguage(final DatabaseReference database) {

        return Observable.create(new ObservableOnSubscribe<List<CategoryResponse>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<CategoryResponse>> e) throws Exception {
                database.orderByKey()
                        .limitToLast(600).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {
                            ArrayList<CategoryResponse> categoriesList = new ArrayList<>();

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    String key = noteDataSnapshot.getKey();
                                    String value = (String) noteDataSnapshot.getValue();
                                    categoriesList.add(new CategoryResponse(value, key));
                                } else {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            e.onNext(categoriesList);
                        } catch (Exception ex) {
                            e.onError(ex);
                            ex.printStackTrace();
                        } finally {
                            e.onComplete();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });
    }
}
