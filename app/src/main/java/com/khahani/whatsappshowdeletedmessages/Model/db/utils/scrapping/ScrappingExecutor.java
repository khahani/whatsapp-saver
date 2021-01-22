package com.khahani.whatsappshowdeletedmessages.Model.db.utils.scrapping;

import java.util.concurrent.Executor;

public class ScrappingExecutor implements Executor {
    @Override
    public synchronized void execute(Runnable r) {
        new Thread(r).start();
    }
}
