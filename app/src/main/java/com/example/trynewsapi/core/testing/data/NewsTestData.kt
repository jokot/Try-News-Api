package com.example.trynewsapi.core.testing.data

import com.example.trynewsapi.core.model.SavableArticle
import com.example.trynewsapi.core.network.model.NetworkArticle
import com.example.trynewsapi.core.network.model.NetworkSource
import com.example.trynewsapi.core.network.model.asExternalModel

val networkArticlesTestData: List<NetworkArticle> = listOf(
    NetworkArticle (
        source = NetworkSource(
            id = "reuters",
            name = "Reuters"
        ),
        author = "Suleiman Al-Khalidi",
        title = "Russian, Syrian jets intensify bombing of Syria's rebel-held northwest - Reuters",
        description = "Insurgents swept into Aleppo, east of Idlib province, on Friday night, forcing the army to redeploy in the biggest challenge to President Bashar al-Assad in years.",
        url = "https://www.reuters.com/world/middle-east/russian-syrian-jets-intensify-bombing-syrias-rebel-held-northwest-2024-12-01/",
        urlToImage = "https://www.reuters.com/resizer/v2/EP4T6SCYIFOLTJIUYWKM4N7644.jpg?auth=45439936b90e3b68553eb04635c95894348d8363f0bb49624690b0920dba1c49&height=1005&width=1920&quality=80&smart=true",
        publishedAt = "2024-12-01T15:31:10Z",
        content = null
    ),
    NetworkArticle (
        source = NetworkSource(
            name = "NBCSports.com"
        ),
        author = "Michael David Smith",
        title = "NFL Playoff Picture 2024: Updated AFC and NFC Standings, bracket, tiebreakers for Week 13 - NBC Sports",
        description = "The Lions and Packers bolstered their playoff positions, while the Giants became the first NFL team mathematically eliminated, on Thanksgiving.",
        url = "https://www.nbcsports.com/nfl/profootballtalk/rumor-mill/news/nfl-playoff-picture-2024-updated-afc-and-nfc-standings-bracket-tiebreakers-for-week-13",
        urlToImage = "https://nbcsports.brightspotcdn.com/dims4/default/80e2eeb/2147483647/strip/true/crop/8381x4714+0+280/resize/1440x810!/quality/90/?url=https%3A%2F%2Fnbc-sports-production-nbc-sports.s3.us-east-1.amazonaws.com%2Fbrightspot%2Fbb%2F5e%2F055b1f2a480a9ff03b6e580db29d%2Fhttps-api-imagn.com%2Frest%2Fdownload%2FimageID%3D24870823",
        publishedAt = "2024-11-29T10:56:55Z",
        content = "The Lions and Packers bolstered their playoff positions, while the Giants became the first NFL team mathematically eliminated, on Thanksgiving. Heres how the NFL playoff picture looks for Week 13 hea… [+3727 chars]"
    ),
    NetworkArticle (
        source = NetworkSource(
            id = "politico",
            name = "Politico"
        ),
        author = "Sue Allan, Mickey Djuric",
        title = "What Trudeau told Trump at Mar-a-Lago - POLITICO",
        description = "Trade, energy and the Arctic were on the table Friday night. Canada hopes it's just the start of the conversation.",
        url = "https://www.politico.com/news/2024/12/01/trudeau-trump-energy-trade-arctic-00192048",
        urlToImage = "https://static.politico.com/5f/56/e9ca331244cd8bef7d868a7d34e2/trump-trudeau-38731.jpg",
        publishedAt = "2024-12-01T15:21:39Z",
        content = "Between Mondays meltdown and a serving of meatloaf at Mar-a-Lago, the pending tariffs were the focus of emergency debate in Ottawa, a special Cabinet meeting, an evening teleconference between Trudea… [+3762 chars]"
    ),
    NetworkArticle (
        source = NetworkSource(
            name = "Athlon Sports"
        ),
        author = "Matt Wadleigh",
        title = "David Pollack Reveals College Football Playoff Predictions After Week 14 - Athlon Sports",
        description = "David Pollack gave his predictions for the College Football Playoff after Week 14 came to an end",
        url = "https://athlonsports.com/college-football/david-pollack-reveals-college-football-playoff-predictions-week-14",
        urlToImage = "https://athlonsports.com/.image/t_share/MjEwMDU5MjcyNzgyODgyMTcw/usatsi_15384543.jpg",
        publishedAt = "2024-12-01T15:19:43Z",
        content = "Week 14 of the college football season had a lot of things going on. There were fights in the game, postgame brawls, rivals planting their flags on the opponents' field, and even a scuffle between Oh… [+1644 chars]"
    ),
)

val savableArticlesTestData = networkArticlesTestData.map {
    SavableArticle(
        article = it.asExternalModel(),
        isBookmarked = false
    )
}