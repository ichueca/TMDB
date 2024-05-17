/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.zabalburu.daw1.tmdb.dao;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.movielists.MovieResultsPageWithDates;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.MovieAppendToResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ichueca
 */
public class TMDBImpl implements TMDBDAO{
    private static String API_KEY="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDI1ZWQ1MTMxNzMyMjA4MWQwOWE4MGI5YmE5NzFhOSIsInN1YiI6IjVjNWFhNTYzYzNhMzY4M2NkNTg3NTYwZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.BdRl_09-08E05cmyW_FFaBI24Em91YFr1xVXX4j_-BE";
    private TmdbApi api = new TmdbApi(API_KEY);
    
    @Override
    public List<Movie> getTopRated() {
        try {
            MovieResultsPage resultados = api.getMovieLists().getTopRated("es-ES",0, "ES");
            return resultados.getResults();
        } catch (TmdbException ex) {
            Logger.getLogger(TMDBImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Movie> getNowPlaying() {
        try {
            MovieResultsPageWithDates resultados = api.getMovieLists().getNowPlaying("es-ES",0, "ES");
            return resultados.getResults();
        } catch (TmdbException ex) {
            Logger.getLogger(TMDBImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Movie> getPopular() {
        try {
            MovieResultsPage resultados = api.getMovieLists().getPopular("es-ES",0, "ES");
            return resultados.getResults();
        } catch (TmdbException ex) {
            Logger.getLogger(TMDBImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    public static void main(String[] args) {
        TMDBDAO dao = new TMDBImpl();
        MovieDb peli = dao.getPelicula(823464);
    }

    @Override
    public MovieDb getPelicula(Integer id) {
        try {
            return api.getMovies().getDetails(id, "es-ES",
                    MovieAppendToResponse.CREDITS,
                    MovieAppendToResponse.RELEASE_DATES);
        } catch (TmdbException ex) {
            Logger.getLogger(TMDBImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
