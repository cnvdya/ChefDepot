package com.fooddepot.storage.api;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by ravisha on 12/21/16.
 */
public interface ProfilePicService {
    public void loadProfilePic(Activity activity,ImageView profilePicImageView,String profilePicPath) ;
    public void uploadFromUri(final Uri fileUri,String filePath, String fileName);
}
