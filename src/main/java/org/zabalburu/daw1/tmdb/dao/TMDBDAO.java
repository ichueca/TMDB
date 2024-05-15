/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.zabalburu.daw1.tmdb.dao;

import info.movito.themoviedbapi.model.core.Movie;
import java.util.List;

/**
 *
 * @author ichueca
 */
public interface TMDBDAO {
    List<Movie> getTopRated();
    List<Movie> getNowPlaying();
    List<Movie> getPopular();
}
