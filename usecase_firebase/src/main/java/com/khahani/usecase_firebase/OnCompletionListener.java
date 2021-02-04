package com.khahani.usecase_firebase;

import com.google.android.gms.tasks.Task;

public interface OnCompletionListener {

    void onComplete(Task<Boolean> task);
}

