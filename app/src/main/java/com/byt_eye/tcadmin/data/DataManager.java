package com.byt_eye.tcadmin.data;

import com.byt_eye.tcadmin.modals.CrawlWebsite;
import com.byt_eye.tcadmin.modals.Website;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DataManager {
    public Observable<List<Website>> getWebsites(final DatabaseReference database) {

        return Observable.create(new ObservableOnSubscribe<List<Website>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Website>> e) throws Exception {
                database.orderByKey().limitToLast(600).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try {
                            ArrayList<Website> dataModalsList = new ArrayList<>();

                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                if (dataSnapshot.hasChildren()) {
                                    Website dataModal = noteDataSnapshot.getValue(Website.class);
                                    dataModalsList.add(dataModal);
                                } else {
                                    DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                    firstChild.getRef().removeValue();
                                }
                            }
                            e.onNext(dataModalsList);
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


    public Observable<List<CrawlWebsite>> crawlWebsite() {
        return Observable.create(new ObservableOnSubscribe<List<CrawlWebsite>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CrawlWebsite>> e) throws Exception {
                crawlWebsite("https://telugu.greatandhra.com/movies.php");
                List<CrawlWebsite> crawlWebsites = new ArrayList<>();
                e.onNext(crawlWebsites);
            }
        });
    }


    private void crawlWebsite(String website) {
        Document doc;
        try {
            doc = Jsoup.connect(website).get();
            // get title of the page
            String title = doc.title();
            System.out.println("Title: " + title);

            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {

                if(link.attr("href").contains("https://telugu.greatandhra.com/movies/movie-news")){

                    // get the value from href attribute
                    System.out.println("\nLink : " + link.attr("href"));
                    System.out.println("Text : " + link.text());
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
