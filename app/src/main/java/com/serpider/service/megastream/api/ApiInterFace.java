package com.serpider.service.megastream.api;



import com.serpider.service.megastream.adapter.Replay;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.model.CommentPOJO;
import com.serpider.service.megastream.model.Donate;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.model.Country;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Movie;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.Section;
import com.serpider.service.megastream.model.Serial_Play;
import com.serpider.service.megastream.model.Ads;
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

    @GET("getCountry.php")
    Call<List<Country>> getCountry();

    @GET("getGenre.php")
    Call<List<Genre>> getGenre();

    @GET("getNetwork.php")
    Call<List<Network>> getNetwork();

    @GET("getSlider.php")
    Call<List<Ads>> getSlider();

    @GET("getComment.php")
    Call<List<Comment>> getComment();

    @FormUrlEncoded
    @POST("getReplay.php?")
    Call<List<Replay>> getReplay(
            @Field("ID") int id_comment
    );

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
            @Field("ID_SERIAL") String id_serial
    );

    @FormUrlEncoded
    @POST("getSerialPlay.php?")
    Call<List<Serial_Play>> getSerialPlay(
            @Field("ID_SECTION") String id_section
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
    @POST("addComment.php?")
    Call<CommentPOJO> getAddComment(
            @Field("USER_ID") String user_id,
            @Field("ITEM_ID") String item_id,
            @Field("MSG") String message
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
    @FormUrlEncoded
    @POST("getSerialSection.php?")
    Call<List<Section>> getSerialSection(
            @Field("ID_SECTION") String id_seasion
    );


    /*New Api Setup*/
    @GET("getGenres/")
    Call<List<Genre>> getGenres();
    @GET("getCountries/")
    Call<List<Country>> getCountrys();
    @GET("getNetworks/")
    Call<List<Network>> getNetworks();
    @GET("getAds/")
    Call<List<Ads>> getAds();

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

}
