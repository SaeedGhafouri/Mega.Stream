package com.serpider.service.megastream.api;

import static com.serpider.service.megastream.interfaces.Key.END_POINT;

import com.serpider.service.megastream.model.Replay;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.model.Donate;
import com.serpider.service.megastream.model.PlayUrl;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Movie;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.Section;
import com.serpider.service.megastream.model.Ads;
import com.serpider.service.megastream.model.Settings;
import com.serpider.service.megastream.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface ApiInterFace {

    @GET("Test-List.php")
    Call<List<Film>> getAllFilm();


    @GET("getSlider.php")
    Call<List<Ads>> getSlider();

    @GET("getComment.php")
    Call<List<Comment>> getComment();

    @GET("getDonate.php")
    Call<Donate> getDonate();

    @FormUrlEncoded
    @POST("getFilmById.php?")
    Call<Film> getFilmById(
            @Field("UNIQUE_FILM") String unique_film
    );

    @FormUrlEncoded
    @POST("getUrlMovie.php?")
    Call<List<Movie>> getMovieUrl(
            @Field("UNIQUE_MOVIE") String unique_movie
    );

    @FormUrlEncoded
    @POST("getFilmByGroup.php?")
    Call<List<Film>> getItemGroup(
            @Field("GROUP_TYPE") String groupType,
            @Field("GROUP_NAME") String groupName
    );

    @FormUrlEncoded
    @POST("getItemByGroupLimit.php?")
    Call<List<Film>> getItemGroupLimit(
            @Field("GROUP_NAME") String groupName,
            @Field("GROUP_QUERY") String groupQuery
    );

    @FormUrlEncoded
    @POST("getSerialSeason.php?")
    Call<List<Season>> getSeason(
            @Field("ID_SERIAL") int id_serial
    );

    @FormUrlEncoded
    @POST("userSignup.php?")
    Call<User> getUserSignUp(
            @Field("USER_USERNAME") String username,
            @Field("USER_NICKNAME") String nickname,
            @Field("USER_PHONE") String phone,
            @Field("USER_EMAIL") String email,
            @Field("USER_PASSWORD") String password,
            @Field("USER_VECTOR") String vector
    );

    @FormUrlEncoded
    @POST("userLogin.php?")
    Call<User> getUserLogin(
            @Field("USER_USERNAME") String username,
            @Field("USER_PASSWORD") String password
    );
    @FormUrlEncoded
    @POST("getUser.php?")
    Call<User> getUser(
            @Field("USER_UNIQUE") int user_unique
    );
    @FormUrlEncoded
    @POST("getSearch.php?")
    Call<List<Film>> getSearch(
            @Field("NAME_ITEM") String name_item
    );

    /*New Api Setup*/
    @GET(END_POINT + "genre")
    Call<Result> getTest();

    @GET(END_POINT + "genre")
    Call<List<Genre>> getGenre();

    @GET(END_POINT + "country")
    Call<List<Country>> getCountry();

    @GET(END_POINT + "network")
    Call<List<Network>> getNetwork();

    @GET(END_POINT + "ads")
    Call<List<Ads>> getAds();

    @GET(END_POINT + "film_all")
    Call<List<Film>> getFilmAll();

    @FormUrlEncoded
    @POST(END_POINT + "film_by")
    Call<List<Film>> getFilmBy(
            @Field("ITEM_GROUP") String group,
            @Field("ITEM_NAME") String name,
            @Field("ITEM_LENGTH") int length
    );

    @FormUrlEncoded
    @POST(END_POINT + "film_details")
    Call<Film> getFilmDetails(
            @Field("ITEM_ID") int id
    );

    @FormUrlEncoded
    @POST(END_POINT + "film_search")
    Call<List<Film>> getFilmSearch(
            @Field("ITEM_NAME") String item_name
    );

    @FormUrlEncoded
    @POST(END_POINT + "film_comment")
    Call<List<Comment>> getComment(
            @Field("ITEM_ID") int id
    );

    @FormUrlEncoded
    @POST(END_POINT + "film_comment_replay")
    Call<List<Replay>> getReplay(
            @Field("COMMENT_ID") int id_comment
    );

    @FormUrlEncoded
    @POST(END_POINT + "film_comment_add")
    Call<Comment> getCommentAdd(
            @Field("USER_ID") int user_id,
            @Field("ITEM_ID") int item_id,
            @Field("MESSAGE") String message
    );

    @FormUrlEncoded
    @POST(END_POINT + "movie_url")
    Call<List<PlayUrl>> getMovieUrl(
            @Field("ITEM_ID") int id_movie
    );

    @FormUrlEncoded
    @POST(END_POINT + "serial_season")
    Call<List<Season>> getSerialSeason(
            @Field("ITEM_ID") int id
    );

    @FormUrlEncoded
    @POST(END_POINT + "serial_section")
    Call<List<Section>> getSerialSection(
            @Field("ID_SEASON") int id
    );

    @FormUrlEncoded
    @POST(END_POINT + "serial_url")
    Call<List<PlayUrl>> getSerialUrl(
            @Field("ID_SECTION") int id
    );

    @FormUrlEncoded
    @POST(END_POINT + "user_signup")
    Call<User> getUserSignup(
            @Field("USER_USERNAME") String username,
            @Field("USER_NICKNAME") String nickname,
            @Field("USER_PHONE") String phone,
            @Field("USER_EMAIL") String email,
            @Field("USER_PASSWORD") String password,
            @Field("USER_VECTOR") String vector,
            @Field("USER_IP") String mac_address
    );

    @FormUrlEncoded
    @POST(END_POINT + "user_submit")
    Call<Result> getUserSubmit(
            @Field("USER_ID") int id,
            @Field("USER_STATUS") int status
    );

    @FormUrlEncoded
    @POST(END_POINT + "user_detail")
    Call<User> getUserDetail(
            @Field("USER_ID") int id
    );

    @GET(END_POINT + "donate_by")
    Call<Donate> getDonateBy();

    @FormUrlEncoded
    @POST(END_POINT + "user_report")
    Call<Result> getUserReport(
            @Field("USER_ID") int id,
            @Field("REPORT_SUBJECT") String subject,
            @Field("REPORT_TOPIC") String topic,
            @Field("REPORT_MESSAGE") String message
    );

    @GET(END_POINT + "app_settings")
    Call<Settings> getSetting();

    /*@E*/

    @GET("getGenres/")
    Call<List<Genre>> getGenres();


    @GET("getCountries/")
    Call<List<Country>> getCountrys();
    @GET("getNetworks/")
    Call<List<Network>> getNetworks();
    @GET("getDonates/")
    Call<Donate> getDonates();

    @FormUrlEncoded
    @POST("getUser/")
    Call<User> getUserInfo(
            @Field("id") int user_id
    );

    @FormUrlEncoded
    @POST("userSignUp/")
    Call<User> userSignUp(
            @Field("USER_USERNAME") String username,
            @Field("USER_NICKNAME") String nickname,
            @Field("USER_PHONE") String phone,
            @Field("USER_EMAIL") String email,
            @Field("USER_PASSWORD") String password,
            @Field("USER_VECTOR") String vector,
            @Field("USER_MAC_ADDRESS") String mac_address
    );

    @FormUrlEncoded
    @POST("userLogin/")
    Call<User> userLogin(
            @Field("USER_USERNAME") String username,
            @Field("USER_PASSWORD") String password
    );

    @FormUrlEncoded
    @POST("getItem/")
    Call<List<Film>> getItem(
            @Field("ITEM_GROUP") String group,
            @Field("ITEM_NAME") String name,
            @Field("ITEM_LENGTH") int length
    );

    @FormUrlEncoded
    @POST("getItemById/")
    Call<Film> getItemByItem(
            @Field("ITEM_ID") String id
    );

    @FormUrlEncoded
    @POST("getUrlMovie/")
    Call<List<PlayUrl>> getMoviePlay(
            @Field("ID_MOVIE") int id_movie
    );

    @FormUrlEncoded
    @POST("getItemComment/")
    Call<List<Comment>> getComments(
            @Field("ID_ITEM") int id_item
    );

    @FormUrlEncoded
    @POST("getCommentReply/")
    Call<List<Replay>> getReplys(
            @Field("ID_COMMENT") int id_comment
    );

    @FormUrlEncoded
    @POST("getSearch/")
    Call<List<Film>> getSearchItem(
            @Field("ITEM_NAME") String item_name
    );

    @FormUrlEncoded
    @POST("userComment/")
    Call<Comment> userComment(
            @Field("USER_ID") int user_id,
            @Field("ITEM_ID") int item_id,
            @Field("MESSAGE") String message
    );
}
