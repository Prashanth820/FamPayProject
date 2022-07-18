package com.example.fampayproject.networking

import com.example.fampayproject.models.CardGroupObject
import com.example.fampayproject.networking.ApiDelegate
import io.reactivex.rxjava3.core.Observable

/**
 * Layer between android-specific components of codebase, i.e.,
 * ViewModels and the gateway to the communication with external web,
 * Via this it is made sure all the interactions happen through this [Repository]
 */
class Repository {
    private val apiDelegate = ApiDelegate.instance

    /**
     * Calls the fetchCards() function defined in [ApiService]
     * @returns the same DataType as described there, i.e., Observable [CardGroupObject]
     */
    fun fetchCardsData(): Observable<CardGroupObject> {
        return apiDelegate.apiService.fetchCards()
    }

}
