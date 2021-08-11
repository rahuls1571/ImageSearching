# ImageSearching

API Use
{

     "https://api.imgur.com/3/gallery/search/"+page_number+"?q=rose"
   
}

Features 
{

    1. Provide a search field for user input.
    
    2. A get request to Imgur API to search for pictures with the term from the search field.
       Use https://api.imgur.com/3/gallery/search/1?q=vanilla where q stands for the query string. 
    
    3. Display the results by given search terms, including the image in a grid view.
    
    4. When the user clicks on an image open the image in a new activity with the title. Have the image title 
       shown in the action bar with a back button.
    
    5. On this screen you have an option to add a comment to the opened image. Save and retrieve the comment using a local database. The 
       comment section and the image should both be visible in the remaining space. 
    
    6. Subsequent viewings of the image should retain the comment added previously (if any)
    
    7. Appropriate error handling (no crashes/ANRs, addressing API failures)
    
    8. MVVM architecture patterns

}


dependencies {

    // Firebase
    implementation 'com.google.firebase:firebase-database:19.5.1'
    implementation 'com.google.firebase:firebase-analytics'
    implementation platform('com.google.firebase:firebase-bom:26.1.0')
    // Glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    // PhotoView
    implementation 'com.github.chrisbanes:PhotoView:2.3.0'
    // Material
    implementation 'com.google.android.material:material:1.2.1'
    // Card View
    implementation 'androidx.cardview:cardview:1.0.0'
    // Recyclerview
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    // Volley
    implementation 'com.android.volley:volley:1.1.1'

}




